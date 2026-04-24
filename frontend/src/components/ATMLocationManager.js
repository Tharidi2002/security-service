import React, { useState, useEffect, useCallback } from 'react';
import {
  Box,
  Card,
  CardContent,
  Typography,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Chip,
  IconButton,
  Alert,
  useTheme,
  CircularProgress,
  DialogContentText,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  LocationOn as LocationIcon,
  Phone as PhoneIcon,
} from '@mui/icons-material';
import atmLocationService from '../services/atmLocationService'; // Use the centralized service

const ATMLocationManager = () => {
  const theme = useTheme();
  const [atms, setAtms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [formError, setFormError] = useState(''); // For errors inside the dialog
  const [openFormDialog, setOpenFormDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [editingATM, setEditingATM] = useState(null);
  const [atmToDelete, setAtmToDelete] = useState(null);
  const [formData, setFormData] = useState({
    atmId: '',
    bankCode: '',
    location: '',
    address: '',
    phoneNumber: '',
    city: '',
    district: '',
    latitude: '',
    longitude: '',
    status: 'ACTIVE',
    notes: '',
  });

  const fetchATMs = useCallback(async () => {
    try {
      setLoading(true);
      const response = await atmLocationService.getAll();
      setAtms(response.data);
      setError('');
    } catch (err) {
      setError('Failed to fetch ATM locations. Please check API connection.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchATMs();
  }, [fetchATMs]);

  const handleOpenFormDialog = (atm = null) => {
    setFormError('');
    if (atm) {
      setEditingATM(atm);
      setFormData(atm);
    } else {
      setEditingATM(null);
      setFormData({
        atmId: '',
        bankCode: '',
        location: '',
        address: '',
        phoneNumber: '',
        city: '',
        district: '',
        latitude: '',
        longitude: '',
        status: 'ACTIVE',
        notes: '',
      });
    }
    setOpenFormDialog(true);
  };

  const handleCloseFormDialog = () => {
    setOpenFormDialog(false);
    setEditingATM(null);
  };

  const handleOpenDeleteDialog = (atm) => {
    setAtmToDelete(atm);
    setOpenDeleteDialog(true);
  };

  const handleCloseDeleteDialog = () => {
    setOpenDeleteDialog(false);
    setAtmToDelete(null);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setFormError('');
    try {
      if (editingATM) {
        await atmLocationService.update(formData.atmId, formData);
      } else {
        await atmLocationService.create(formData);
      }
      fetchATMs();
      handleCloseFormDialog();
    } catch (err) {
      const errorMsg = err.response?.data?.message || err.response?.data?.error || 'Failed to save ATM location.';
      setFormError(errorMsg);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleDelete = async () => {
    if (!atmToDelete) return;
    setIsSubmitting(true);
    try {
      await atmLocationService.delete(atmToDelete.atmId);
      fetchATMs();
      handleCloseDeleteDialog();
    } catch (err) {
      setError('Failed to delete ATM location.'); // Show error on main page
      handleCloseDeleteDialog();
    } finally {
      setIsSubmitting(false);
    }
  };
  
  const getStatusChip = (status) => {
      let color;
      switch(status) {
          case 'ACTIVE': color = 'success'; break;
          case 'INACTIVE': color = 'error'; break;
          case 'UNDER_MAINTENANCE': color = 'warning'; break;
          case 'DECOMMISSIONED': color = 'default'; break;
          default: color = 'default';
      }
      return <Chip label={status} color={color} size="small" sx={{ color: color !== 'default' ? 'white' : 'black'}} />;
  }

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">ATM Location Management</Typography>
        <Button variant="contained" startIcon={<AddIcon />} onClick={() => handleOpenFormDialog()}>
          Register New ATM
        </Button>
      </Box>

      {error && <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>{error}</Alert>}

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>Registered ATM Locations</Typography>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ATM ID</TableCell>
                  <TableCell>Location</TableCell>
                  <TableCell>Address</TableCell>
                  <TableCell>City</TableCell>
                  <TableCell>Status</TableCell>
                  <TableCell sx={{ textAlign: 'right' }}>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {atms.map((atm) => (
                  <TableRow key={atm.atmId}>
                    <TableCell>{atm.atmId}</TableCell>
                    <TableCell>
                      <Box display="flex" alignItems="center">
                        <LocationIcon sx={{ mr: 1, fontSize: 16, color: 'text.secondary' }} />
                        {atm.location}
                      </Box>
                    </TableCell>
                    <TableCell>{atm.address}</TableCell>
                    <TableCell>{atm.city}</TableCell>
                    <TableCell>{getStatusChip(atm.status)}</TableCell>
                    <TableCell sx={{ textAlign: 'right' }}>
                      <IconButton size="small" onClick={() => handleOpenFormDialog(atm)}>
                        <EditIcon />
                      </IconButton>
                      <IconButton size="small" onClick={() => handleOpenDeleteDialog(atm)} color="error">
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </CardContent>
      </Card>

      {/* Form Dialog for Create/Update */}
      <Dialog open={openFormDialog} onClose={handleCloseFormDialog} maxWidth="md" fullWidth>
        <DialogTitle>{editingATM ? 'Edit ATM Location' : 'Register New ATM Location'}</DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            {formError && <Alert severity="error" sx={{ mb: 2 }}>{formError}</Alert>}
            <Grid container spacing={2} sx={{ mt: 1 }}>
              {/* --- Form Fields --- */}
              <Grid item xs={12} sm={6}><TextField fullWidth label="ATM ID" name="atmId" value={formData.atmId} onChange={handleChange} required disabled={!!editingATM} /></Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="Bank Code" name="bankCode" value={formData.bankCode} onChange={handleChange} required /></Grid>
              <Grid item xs={12}><TextField fullWidth label="Location Description" name="location" value={formData.location} onChange={handleChange} required /></Grid>
              <Grid item xs={12}><TextField fullWidth label="Address" name="address" value={formData.address} onChange={handleChange} required /></Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="Phone Number" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} /></Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="City" name="city" value={formData.city} onChange={handleChange} required /></Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="District" name="district" value={formData.district} onChange={handleChange} required /></Grid>
              <Grid item xs={12} sm={6}>
                <FormControl fullWidth>
                  <InputLabel>Status</InputLabel>
                  <Select name="status" value={formData.status} onChange={handleChange} label="Status">
                    <MenuItem value="ACTIVE">Active</MenuItem>
                    <MenuItem value="INACTIVE">Inactive</MenuItem>
                    <MenuItem value="UNDER_MAINTENANCE">Under Maintenance</MenuItem>
                    <MenuItem value="DECOMMISSIONED">Decommissioned</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="Latitude" name="latitude" value={formData.latitude} onChange={handleChange} /></Grid>
              <Grid item xs={12} sm={6}><TextField fullWidth label="Longitude" name="longitude" value={formData.longitude} onChange={handleChange} /></Grid>
              <Grid item xs={12}><TextField fullWidth label="Notes" name="notes" value={formData.notes} onChange={handleChange} multiline rows={3} /></Grid>
            </Grid>
          </DialogContent>
          <DialogActions sx={{ p: '16px 24px' }}>
            <Button onClick={handleCloseFormDialog} disabled={isSubmitting}>Cancel</Button>
            <Button type="submit" variant="contained" disabled={isSubmitting}>
              {isSubmitting ? <CircularProgress size={24} /> : (editingATM ? 'Update' : 'Register')}
            </Button>
          </DialogActions>
        </form>
      </Dialog>

      {/* Delete Confirmation Dialog */}
      <Dialog open={openDeleteDialog} onClose={handleCloseDeleteDialog}>
        <DialogTitle>Confirm Deletion</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Are you sure you want to delete ATM '{atmToDelete?.atmId}'? This action cannot be undone.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDeleteDialog} disabled={isSubmitting}>Cancel</Button>
          <Button onClick={handleDelete} color="error" disabled={isSubmitting}>
            {isSubmitting ? <CircularProgress size={24} /> : 'Delete'}
          </Button>
        </DialogActions>
      </Dialog>

    </Box>
  );
};

export default ATMLocationManager;

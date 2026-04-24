import React, { useState, useEffect } from 'react';
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
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  LocationOn as LocationIcon,
  Phone as PhoneIcon,
} from '@mui/icons-material';
import axios from 'axios';

const ATMLocationManager = () => {
  const theme = useTheme();
  const [atms, setAtms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [openDialog, setOpenDialog] = useState(false);
  const [editingATM, setEditingATM] = useState(null);
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

  useEffect(() => {
    fetchATMs();
  }, []);

  const fetchATMs = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/atm-locations/all');
      setAtms(response.data);
    } catch (err) {
      setError('Failed to fetch ATM locations');
    } finally {
      setLoading(false);
    }
  };

  const handleOpenDialog = (atm = null) => {
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
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingATM(null);
    setError('');
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingATM) {
        await axios.put(`http://localhost:8081/api/atm-locations/${formData.atmId}`, formData);
      } else {
        await axios.post('http://localhost:8081/api/atm-locations/register', formData);
      }
      fetchATMs();
      handleCloseDialog();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to save ATM location');
    }
  };

  const handleDelete = async (atmId) => {
    if (window.confirm('Are you sure you want to delete this ATM location?')) {
      try {
        await axios.delete(`http://localhost:8081/api/atm-locations/${atmId}`);
        fetchATMs();
      } catch (err) {
        setError('Failed to delete ATM location');
      }
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'ACTIVE': return theme.palette.success.main;
      case 'INACTIVE': return theme.palette.error.main;
      case 'UNDER_MAINTENANCE': return theme.palette.warning.main;
      case 'DECOMMISSIONED': return theme.palette.grey.main;
      default: return theme.palette.grey.main;
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
        <Typography>Loading ATM locations...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">ATM Location Management</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => handleOpenDialog()}
        >
          Register New ATM
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>
          {error}
        </Alert>
      )}

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Registered ATM Locations
          </Typography>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ATM ID</TableCell>
                  <TableCell>Bank Code</TableCell>
                  <TableCell>Location</TableCell>
                  <TableCell>Address</TableCell>
                  <TableCell>Contact</TableCell>
                  <TableCell>City</TableCell>
                  <TableCell>Status</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {atms.map((atm) => (
                  <TableRow key={atm.id}>
                    <TableCell>{atm.atmId}</TableCell>
                    <TableCell>{atm.bankCode}</TableCell>
                    <TableCell>
                      <Box display="flex" alignItems="center">
                        <LocationIcon sx={{ mr: 1, fontSize: 16 }} />
                        {atm.location}
                      </Box>
                    </TableCell>
                    <TableCell>{atm.address}</TableCell>
                    <TableCell>
                      <Box display="flex" alignItems="center">
                        <PhoneIcon sx={{ mr: 1, fontSize: 16 }} />
                        {atm.phoneNumber}
                      </Box>
                    </TableCell>
                    <TableCell>{atm.city}</TableCell>
                    <TableCell>
                      <Chip
                        label={atm.status}
                        size="small"
                        sx={{
                          backgroundColor: getStatusColor(atm.status),
                          color: 'white',
                        }}
                      />
                    </TableCell>
                    <TableCell>
                      <IconButton
                        size="small"
                        onClick={() => handleOpenDialog(atm)}
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        onClick={() => handleDelete(atm.atmId)}
                        color="error"
                      >
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

      <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="md" fullWidth>
        <DialogTitle>
          {editingATM ? 'Edit ATM Location' : 'Register New ATM Location'}
        </DialogTitle>
        <form onSubmit={handleSubmit}>
          <DialogContent>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="ATM ID"
                  name="atmId"
                  value={formData.atmId}
                  onChange={handleChange}
                  required
                  disabled={!!editingATM}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Bank Code"
                  name="bankCode"
                  value={formData.bankCode}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Location Description"
                  name="location"
                  value={formData.location}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Address"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Phone Number"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="City"
                  name="city"
                  value={formData.city}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="District"
                  name="district"
                  value={formData.district}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <FormControl fullWidth>
                  <InputLabel>Status</InputLabel>
                  <Select
                    name="status"
                    value={formData.status}
                    onChange={handleChange}
                    label="Status"
                  >
                    <MenuItem value="ACTIVE">Active</MenuItem>
                    <MenuItem value="INACTIVE">Inactive</MenuItem>
                    <MenuItem value="UNDER_MAINTENANCE">Under Maintenance</MenuItem>
                    <MenuItem value="DECOMMISSIONED">Decommissioned</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Latitude"
                  name="latitude"
                  value={formData.latitude}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Longitude"
                  name="longitude"
                  value={formData.longitude}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Notes"
                  name="notes"
                  value={formData.notes}
                  onChange={handleChange}
                  multiline
                  rows={3}
                />
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseDialog}>Cancel</Button>
            <Button type="submit" variant="contained">
              {editingATM ? 'Update' : 'Register'}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </Box>
  );
};

export default ATMLocationManager;

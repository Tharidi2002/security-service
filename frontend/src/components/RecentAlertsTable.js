import React from 'react';
import { 
  Table, 
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow, 
  Paper, 
  Typography, 
  Chip, 
  Box 
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { format } from 'date-fns';

const SeverityChip = styled(Chip)(({ theme, severity }) => {
  const colors = {
    CRITICAL: theme.palette.error.main,
    HIGH: theme.palette.warning.main,
    MEDIUM: theme.palette.info.main,
    LOW: theme.palette.grey[500],
  };
  return {
    backgroundColor: colors[severity] || theme.palette.grey[700],
    color: 'white',
    fontWeight: 'bold',
  };
});

const StatusChip = styled(Chip)(({ theme, status }) => {
    let color;
    let variant = 'filled';

    switch (status) {
        case 'OPEN':
            color = 'warning';
            break;
        case 'IN_PROGRESS':
            color = 'primary';
            break;
        case 'RESOLVED':
            color = 'success';
            variant = 'outlined';
            break;
        default:
            color = 'default';
    }

    return {
        color: color,
        borderColor: color,
        variant: variant
    };
});


const RecentAlertsTable = ({ alerts }) => {
  return (
    <TableContainer component={Paper} sx={{ mt: 4, background: '#1f2937' }}>
      <Box sx={{ p: 2 }}>
        <Typography variant="h6">Recent Alerts</Typography>
      </Box>
      <Table sx={{ minWidth: 650 }} aria-label="recent alerts table">
        <TableHead>
          <TableRow>
            <TableCell>ATM ID</TableCell>
            <TableCell>Location</TableCell>
            <TableCell>Alert Type</TableCell>
            <TableCell>Severity</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Timestamp</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {alerts && alerts.length > 0 ? (
            alerts.map((alert) => (
              <TableRow key={alert.id}>
                <TableCell>{alert.atmId}</TableCell>
                <TableCell>{alert.location}</TableCell>
                <TableCell>{alert.alertType}</TableCell>
                <TableCell>
                  <SeverityChip label={alert.severity} severity={alert.severity} size="small" />
                </TableCell>
                <TableCell>
                    <StatusChip label={alert.status} status={alert.status} size="small" />
                </TableCell>
                <TableCell>{format(new Date(alert.timestamp), 'Pp')}</TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={6} sx={{ textAlign: 'center' }}>
                No recent alerts found.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default RecentAlertsTable;

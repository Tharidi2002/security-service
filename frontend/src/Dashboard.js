import React, { useState, useEffect } from 'react';
import { Grid, Container, Typography, CircularProgress, Box } from '@mui/material';
import KeyMetricCard from './components/KeyMetricCard';
import RecentAlertsTable from './components/RecentAlertsTable';
import AlertSeverityPieChart from './components/AlertSeverityPieChart';
import AlertTypeBarChart from './components/AlertTypeBarChart';
import alertService from './services/alertService';
import socketService from './services/socketService'; // Import the socket service
import NotificationsIcon from '@mui/icons-material/Notifications';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';
import ReportProblemIcon from '@mui/icons-material/ReportProblem';
import AtmIcon from '@mui/icons-material/Atm';

const Dashboard = () => {
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch initial data
    alertService.getAllAlerts()
      .then(response => {
        const sortedAlerts = response.data.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
        setAlerts(sortedAlerts);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching data: ', error);
        setError('Failed to load alert data. Check API.');
        setLoading(false);
      });

    // Establish WebSocket connection
    socketService.connect();

    // Listen for new alerts
    socketService.on('new-alert', (newAlert) => {
        console.log('New alert received via WebSocket:', newAlert);
        // Add the new alert to the top of the list
        setAlerts(prevAlerts => [newAlert, ...prevAlerts]);
    });

    // Cleanup on component unmount
    return () => {
        console.log('Disconnecting WebSocket.');
        socketService.off('new-alert');
        socketService.disconnect();
    };
  }, []); // Empty dependency array ensures this runs only once

  const totalAlerts = alerts.length;
  const criticalAlerts = alerts.filter(alert => alert.severity === 'CRITICAL').length;
  const unresolvedAlerts = alerts.filter(alert => alert.status !== 'RESOLVED').length;
  const atmsAffected = new Set(alerts.map(alert => alert.atmId)).size;

  if (loading) {
    return <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}><CircularProgress /></Box>;
  }

  if (error) {
    return <Typography color="error.main" sx={{ textAlign: 'center', mt: 4 }}>{error}</Typography>;
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" sx={{ mb: 4, color: 'text.primary' }}>
        Real-Time Security Dashboard
      </Typography>
      
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6} md={3}>
          <KeyMetricCard title="Total Alerts" value={totalAlerts} icon={<NotificationsIcon fontSize="large" />} color="primary.main" />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <KeyMetricCard title="Critical Alerts" value={criticalAlerts} icon={<WarningAmberIcon fontSize="large" />} color="error.main" />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <KeyMetricCard title="Unresolved Alerts" value={unresolvedAlerts} icon={<ReportProblemIcon fontSize="large" />} color="warning.main" />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <KeyMetricCard title="ATMs Affected" value={atmsAffected} icon={<AtmIcon fontSize="large" />} color="info.main" />
        </Grid>
      </Grid>

      <Grid container spacing={3} sx={{ mt: 1 }}>
        <Grid item xs={12} md={6}>
           <AlertSeverityPieChart alerts={alerts} />
        </Grid>
        <Grid item xs={12} md={6}>
           <AlertTypeBarChart alerts={alerts} />
        </Grid>
      </Grid>

      <Grid container spacing={3} sx={{ mt: 1 }}>
        <Grid item xs={12}>
          <RecentAlertsTable alerts={alerts} /> 
        </Grid>
      </Grid>

    </Container>
  );
};

export default Dashboard;

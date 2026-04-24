import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer, CartesianGrid } from 'recharts';
import { Paper, Typography, Box } from '@mui/material';
import { useTheme } from '@mui/material/styles';

const AlertTypeBarChart = ({ alerts }) => {
  const theme = useTheme();

  const typeCounts = alerts.reduce((acc, alert) => {
    acc[alert.alertType] = (acc[alert.alertType] || 0) + 1;
    return acc;
  }, {});

  const data = Object.keys(typeCounts).map(key => ({ 
    name: key,
    count: typeCounts[key] 
  })).sort((a, b) => b.count - a.count); // Sort for better visualization

  return (
    <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: '100%', background: '#1f2937' }}>
      <Typography variant="h6" gutterBottom>
        Alerts by Type
      </Typography>
      <ResponsiveContainer width="100%" height={300}>
        <BarChart
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" stroke={theme.palette.divider} />
          <XAxis dataKey="name" tick={{ fill: theme.palette.text.secondary }} />
          <YAxis tick={{ fill: theme.palette.text.secondary }} />
          <Tooltip 
             contentStyle={{ backgroundColor: theme.palette.background.paper, border: `1px solid ${theme.palette.divider}` }}
          />
          <Legend />
          <Bar dataKey="count" fill={theme.palette.primary.main} />
        </BarChart>
      </ResponsiveContainer>
    </Paper>
  );
};

export default AlertTypeBarChart;

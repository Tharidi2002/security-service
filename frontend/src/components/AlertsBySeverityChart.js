import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip, Legend } from 'recharts';
import { Paper, Typography, Box } from '@mui/material';

const COLORS = {
  CRITICAL: '#f44336', // Red
  HIGH: '#ff9800',     // Orange
  MEDIUM: '#2196f3',   // Blue
  LOW: '#4caf50',      // Green
};

const AlertsBySeverityChart = ({ alerts }) => {
  const data = alerts.reduce((acc, alert) => {
    const severity = alert.severity || 'UNKNOWN';
    const existing = acc.find(item => item.name === severity);
    if (existing) {
      existing.value += 1;
    } else {
      acc.push({ name: severity, value: 1 });
    }
    return acc;
  }, []);

  return (
    <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: '100%', background: '#1f2937' }}>
      <Typography variant="h6" gutterBottom>
        Alerts by Severity
      </Typography>
      <ResponsiveContainer width="100%" height={300}>
        <PieChart>
          <Pie
            data={data}
            cx="50%"
            cy="50%"
            labelLine={false}
            outerRadius={80}
            fill="#8884d8"
            dataKey="value"
            nameKey="name"
            label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
          >
            {data.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={COLORS[entry.name]} />
            ))}
          </Pie>
          <Tooltip />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </Paper>
  );
};

export default AlertsBySeverityChart;

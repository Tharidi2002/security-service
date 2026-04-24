import React from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Paper, Typography, Box } from '@mui/material';
import { useTheme } from '@mui/material/styles';

const AlertSeverityPieChart = ({ alerts }) => {
  const theme = useTheme();

  const severityCounts = alerts.reduce((acc, alert) => {
    acc[alert.severity] = (acc[alert.severity] || 0) + 1;
    return acc;
  }, {});

  const data = Object.keys(severityCounts).map(key => ({ 
    name: key,
    value: severityCounts[key] 
  }));

  const COLORS = {
    CRITICAL: theme.palette.error.main,
    HIGH: theme.palette.warning.main,
    MEDIUM: theme.palette.info.main,
    LOW: theme.palette.grey[500],
  };

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
            label={({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
                const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
                const x = cx + radius * Math.cos(-midAngle * (Math.PI / 180));
                const y = cy + radius * Math.sin(-midAngle * (Math.PI / 180));
                return (
                    <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
                        {`${(percent * 100).toFixed(0)}%`}
                    </text>
                );
            }}
          >
            {data.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={COLORS[entry.name]} />
            ))}
          </Pie>
          <Tooltip 
            contentStyle={{ backgroundColor: theme.palette.background.paper, border: `1px solid ${theme.palette.divider}` }}
          />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </Paper>
  );
};

export default AlertSeverityPieChart;

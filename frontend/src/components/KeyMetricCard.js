import React from 'react';
import { Card, CardContent, Typography, Box, Avatar } from '@mui/material';
import { styled } from '@mui/material/styles';

const StyledCard = styled(Card)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-between',
  height: '100%',
  padding: theme.spacing(2),
}));

const CardHeader = styled(Box)({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'flex-start',
});

const KeyMetricCard = ({ title, value, icon, color }) => {
  return (
    <StyledCard elevation={3}>
      <CardContent sx={{ padding: 0, "&:last-child": { paddingBottom: 0 } }}>
        <CardHeader>
          <Typography variant="h6" component="div" color="text.secondary">
            {title}
          </Typography>
          <Avatar sx={{ bgcolor: color, color: 'white', width: 56, height: 56 }}>
            {icon}
          </Avatar>
        </CardHeader>
        <Box mt={3}>
          <Typography variant="h4" component="p" sx={{ fontWeight: 'bold' }}>
            {value}
          </Typography>
        </Box>
      </CardContent>
    </StyledCard>
  );
};

export default KeyMetricCard;

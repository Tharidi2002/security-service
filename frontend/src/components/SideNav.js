import React from 'react';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import {
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Box,
  Typography
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import AtmIcon from '@mui/icons-material/Atm';
import SecurityIcon from '@mui/icons-material/Security';

const drawerWidth = 240;

const SideNav = () => {
  const location = useLocation();

  const menuItems = [
    { text: 'Dashboard', icon: <DashboardIcon />, path: '/' },
    { text: 'ATM Management', icon: <AtmIcon />, path: '/atms' },
  ];

  return (
    <Drawer
      variant="permanent"
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        [`& .MuiDrawer-paper`]: { 
            width: drawerWidth, 
            boxSizing: 'border-box',
            backgroundColor: '#111827', // Dark background for the drawer
            color: '#9CA3AF', // Light grey text
        },
      }}
    >
      <Toolbar>
        <Box sx={{ display: 'flex', alignItems: 'center', color: '#FFF' }}>
            <SecurityIcon sx={{ mr: 2 }}/>
            <Typography variant="h6" noWrap>
                ATM Security
            </Typography>
        </Box>
      </Toolbar>
      <List>
        {menuItems.map((item) => (
          <ListItem key={item.text} disablePadding>
            <ListItemButton 
              component={RouterLink} 
              to={item.path}
              selected={location.pathname === item.path}
              sx={{
                '&.Mui-selected': {
                  backgroundColor: 'rgba(255, 255, 255, 0.08)',
                  color: '#FFF',
                  '& .MuiListItemIcon-root': {
                    color: '#FFF',
                  },
                },
                '&:hover': {
                    backgroundColor: 'rgba(255, 255, 255, 0.05)',
                }
              }}
            >
              <ListItemIcon sx={{ color: 'inherit' }}>
                {item.icon}
              </ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Drawer>
  );
};

export default SideNav;

import React, { useState, useEffect, useRef } from 'react';
import {
  Badge,
  IconButton,
  Popover,
  List,
  ListItem,
  ListItemText,
  Typography,
  Box,
  Chip,
  Alert,
  Snackbar,
  useTheme,
} from '@mui/material';
import {
  Notifications as NotificationsIcon,
  Error as ErrorIcon,
  Warning as WarningIcon,
  Info as InfoIcon,
  Close as CloseIcon,
} from '@mui/icons-material';
import io from 'socket.io-client';

const NotificationCenter = () => {
  const theme = useTheme();
  const [anchorEl, setAnchorEl] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [criticalAlert, setCriticalAlert] = useState(null);
  const socketRef = useRef(null);

  useEffect(() => {
    // Initialize WebSocket connection
    socketRef.current = io('http://localhost:8082', {
      transports: ['websocket', 'polling'],
    });

    // Listen for new alerts
    socketRef.current.on('connect', () => {
      console.log('Connected to WebSocket server');
    });

    socketRef.current.on('alerts', (alert) => {
      console.log('New alert received:', alert);
      addNotification(alert);
    });

    socketRef.current.on('critical-alerts', (alert) => {
      console.log('Critical alert received:', alert);
      addNotification(alert);
      setCriticalAlert(alert);
    });

    socketRef.current.on('disconnect', () => {
      console.log('Disconnected from WebSocket server');
    });

    return () => {
      if (socketRef.current) {
        socketRef.current.disconnect();
      }
    };
  }, []);

  const addNotification = (alert) => {
    const notification = {
      id: alert.id || Date.now(),
      title: `${alert.atmId} - ${alert.alertType?.replace('_', ' ')}`,
      message: alert.message,
      severity: alert.severity,
      timestamp: alert.timestamp || new Date().toISOString(),
      read: false,
    };

    setNotifications(prev => [notification, ...prev].slice(0, 50)); // Keep only last 50
    setUnreadCount(prev => prev + 1);
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleNotificationClick = (notificationId) => {
    setNotifications(prev =>
      prev.map(n => n.id === notificationId ? { ...n, read: true } : n)
    );
    setUnreadCount(prev => Math.max(0, prev - 1));
  };

  const handleCriticalAlertClose = () => {
    setCriticalAlert(null);
  };

  const getSeverityIcon = (severity) => {
    switch (severity) {
      case 'CRITICAL': return <ErrorIcon color="error" />;
      case 'WARNING': return <WarningIcon color="warning" />;
      case 'INFO': return <InfoIcon color="info" />;
      default: return <InfoIcon />;
    }
  };

  const getSeverityColor = (severity) => {
    switch (severity) {
      case 'CRITICAL': return theme.palette.error.main;
      case 'WARNING': return theme.palette.warning.main;
      case 'INFO': return theme.palette.info.main;
      default: return theme.palette.grey.main;
    }
  };

  const open = Boolean(anchorEl);
  const id = open ? 'notification-popover' : undefined;

  return (
    <>
      <IconButton
        color="inherit"
        onClick={handleClick}
        sx={{ mr: 1 }}
      >
        <Badge badgeContent={unreadCount} color="error">
          <NotificationsIcon />
        </Badge>
      </IconButton>

      <Popover
        id={id}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
      >
        <Box sx={{ width: 360, maxHeight: 400, overflow: 'auto' }}>
          <Typography variant="h6" sx={{ p: 2, borderBottom: 1, borderColor: 'divider' }}>
            Notifications
          </Typography>
          
          {notifications.length === 0 ? (
            <Box sx={{ p: 2, textAlign: 'center' }}>
              <Typography variant="body2" color="text.secondary">
                No notifications
              </Typography>
            </Box>
          ) : (
            <List dense>
              {notifications.map((notification) => (
                <ListItem
                  key={notification.id}
                  button
                  onClick={() => handleNotificationClick(notification.id)}
                  sx={{
                    backgroundColor: notification.read ? 'transparent' : 'action.hover',
                    '&:hover': {
                      backgroundColor: 'action.selected',
                    },
                  }}
                >
                  <Box sx={{ display: 'flex', alignItems: 'center', width: '100%' }}>
                    {getSeverityIcon(notification.severity)}
                    <Box sx={{ ml: 2, flexGrow: 1 }}>
                      <Typography variant="subtitle2" sx={{ fontWeight: notification.read ? 'normal' : 'bold' }}>
                        {notification.title}
                      </Typography>
                      <Typography variant="body2" color="text.secondary" noWrap>
                        {notification.message}
                      </Typography>
                      <Typography variant="caption" color="text.secondary">
                        {new Date(notification.timestamp).toLocaleString()}
                      </Typography>
                    </Box>
                    <Chip
                      label={notification.severity}
                      size="small"
                      sx={{
                        backgroundColor: getSeverityColor(notification.severity),
                        color: 'white',
                        ml: 1,
                      }}
                    />
                  </Box>
                </ListItem>
              ))}
            </List>
          )}
        </Box>
      </Popover>

      {/* Critical Alert Snackbar */}
      <Snackbar
        open={!!criticalAlert}
        autoHideDuration={10000}
        onClose={handleCriticalAlertClose}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        {criticalAlert && (
          <Alert
            onClose={handleCriticalAlertClose}
            severity="error"
            sx={{ width: '100%' }}
          >
            <Typography variant="h6" gutterBottom>
              🚨 CRITICAL ALERT 🚨
            </Typography>
            <Typography variant="body1">
              <strong>{criticalAlert.atmId}</strong> - {criticalAlert.alertType?.replace('_', ' ')}
            </Typography>
            <Typography variant="body2">
              {criticalAlert.message}
            </Typography>
            <Typography variant="caption" display="block" sx={{ mt: 1 }}>
              {new Date(criticalAlert.timestamp).toLocaleString()}
            </Typography>
          </Alert>
        )}
      </Snackbar>
    </>
  );
};

export default NotificationCenter;

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';

// Import components
import SideNav from './components/SideNav';
import Dashboard from './Dashboard';
import ATMLocationManager from './components/ATMLocationManager';

// Define a dark theme for the application
const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#38bdf8', // A vibrant blue
    },
    secondary: {
      main: '#fbbf24', // A warm yellow
    },
    background: {
      default: '#0f172a', // A very dark blue, almost black
      paper: '#1e293b',   // A slightly lighter dark blue for cards/papers
    },
    text: {
      primary: '#f1f5f9',
      secondary: '#94a3b8',
    },
    error: {
      main: '#f43f5e', // A vibrant red
    },
    warning: {
      main: '#f97316', // A bright orange
    },
    info: {
      main: '#4ade80',   // A cool green
    },
    success: {
        main: '#22c55e', // A standard green for success
    }
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h4: {
      fontWeight: 600,
    },
    h6: {
        fontWeight: 600,
    }
  },
});

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <Router>
        <Box sx={{ display: 'flex' }}>
          {/* The SideNav will be a permanent part of the layout */}
          <SideNav />

          {/* Main content area */}
          <Box
            component="main"
            sx={{
              flexGrow: 1,
              bgcolor: 'background.default',
              p: 3,
              width: `calc(100% - 240px)` // Adjust width to account for the drawer
            }}
          >
            {/* The top toolbar spacer is only needed if you have a top AppBar, which we don't */}
            {/* <Toolbar /> */}

            <Routes>
              {/* Route for the main dashboard */}
              <Route path="/" element={<Dashboard />} />
              
              {/* Route for the ATM Location Management page */}
              <Route path="/atms" element={<ATMLocationManager />} />
            </Routes>
          </Box>
        </Box>
      </Router>
    </ThemeProvider>
  );
}

export default App;

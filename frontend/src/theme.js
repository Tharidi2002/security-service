import { createTheme } from '@mui/material/styles';

// Create a theme instance.
const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#5A96E3', // A pleasant, professional blue
    },
    secondary: {
      main: '#FFD166', // A contrasting, warm amber
    },
    background: {
      default: '#0B132B', // Very dark navy blue
      paper: '#1C2541',   // Dark slate blue for cards/surfaces
    },
    text: {
      primary: '#F0F0F0',   // Light grey for primary text
      secondary: '#B0B0B0', // Dimmer grey for secondary text
    },
    error: {
      main: '#f44336', // Red
    },
    warning: {
      main: '#ff9800', // Orange
    },
    info: {
      main: '#4DB6AC', // Teal
    },
    success: {
      main: '#4caf50', // Green
    },
  },
  typography: {
    fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
    h4: {
      fontWeight: 600,
    },
    h5: {
      fontWeight: 600,
    },
    h6: {
      fontWeight: 600,
    },
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundColor: '#1C2541', // Match paper color for a seamless look
          boxShadow: 'none',
          borderBottom: '1px solid #333f5b', // Subtle border
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: '12px', // More rounded corners for a modern feel
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          boxShadow: '0 4px 12px 0 rgba(0,0,0,0.1)',
        }
      }
    }
  },
});

export default theme;

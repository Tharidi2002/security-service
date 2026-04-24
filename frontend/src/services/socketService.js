import io from 'socket.io-client';

// We connect to the same server that serves our REST API
const SOCKET_URL = 'http://localhost:8081'; 

const socket = io(SOCKET_URL, {
    // Optional: add any connection options here
    // e.g., transports: ['websocket']
});

/**
 * The service object for managing WebSocket interactions.
 */
const socketService = {
    /**
     * Connect to the WebSocket server.
     */
    connect: () => {
        if (!socket.connected) {
            socket.connect();
        }
    },

    /**
     * Disconnect from the WebSocket server.
     */
    disconnect: () => {
        if (socket.connected) {
            socket.disconnect();
        }
    },

    /**
     * Subscribe to a specific event.
     * @param {string} eventName The name of the event (e.g., 'new-alert').
     * @param {function} callback The function to execute when the event is received.
     */
    on: (eventName, callback) => {
        socket.on(eventName, callback);
    },

    /**
     * Unsubscribe from a specific event to prevent memory leaks.
     * @param {string} eventName The name of the event.
     */
    off: (eventName) => {
        socket.off(eventName);
    }
};

// Log connection status for debugging
socket.on('connect', () => {
    console.log('Socket.IO connected successfully.');
});

socket.on('connect_error', (error) => {
    console.error('Socket.IO connection error:', error);
});

export default socketService;

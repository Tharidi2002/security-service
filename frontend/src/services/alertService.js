import axios from 'axios';

const API_URL = 'http://localhost:8081/api/alerts';

/**
 * Fetches all alerts from the backend API.
 */
const getAllAlerts = () => {
  return axios.get(API_URL);
};

/**
 * The service object that contains all API functions.
 */
const alertService = {
  getAllAlerts,
};

export default alertService;

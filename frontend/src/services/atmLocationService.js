import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api/atm-locations';

/**
 * A centralized service for handling all API requests related to ATM locations.
 */
const atmLocationService = {
    /**
     * Fetches all ATM locations.
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    getAll: () => {
        return axios.get(`${API_BASE_URL}/all`);
    },

    /**
     * Registers a new ATM location.
     * @param {object} atmData The data for the new ATM.
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    create: (atmData) => {
        return axios.post(`${API_BASE_URL}/register`, atmData);
    },

    /**
     * Updates an existing ATM location.
     * @param {string} atmId The ID of the ATM to update.
     * @param {object} atmData The updated data.
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    update: (atmId, atmData) => {
        return axios.put(`${API_BASE_URL}/${atmId}`, atmData);
    },

    /**
     * Deletes an ATM location.
     * @param {string} atmId The ID of the ATM to delete.
     * @returns {Promise<axios.AxiosResponse<any>>}
     */
    delete: (atmId) => {
        return axios.delete(`${API_BASE_URL}/${atmId}`);
    }
};

export default atmLocationService;

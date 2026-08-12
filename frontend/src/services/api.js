import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const authAPI = {
  login: (username, password) =>
    api.post('/auth/login', { username, password }),
  register: (username, password, roles = ['USER']) =>
    api.post('/auth/register', { username, password, roles }),
};

export const appointmentAPI = {
  bookAppointment: (userId, scheduleProviderId, appointeeName, appointeeEmailId, datetime, location) =>
    api.post('/appointment/book', null, {
      params: {
        userId,
        scheduleProviderId,
        appointeeName,
        appointeeEmailId,
        datetime,
        location,
      },
    }),
  getAllAppointments: () =>
    api.get('/appointment/all'),
};

export const providerAPI = {
  getAllProviders: () =>
    api.get('/auth/scheduleprovider/all'),
};

export const timeSlotAPI = {
  getAvailableSlots: (providerId) =>
    api.get(`/timeslot/available/${providerId}`),
};

export default api;

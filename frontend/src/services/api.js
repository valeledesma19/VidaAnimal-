import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

api.interceptors.request.use((config) => {
  const esRutaPublica = config.url.startsWith('/auth');
  const token = localStorage.getItem('token');

  if (token && !esRutaPublica) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
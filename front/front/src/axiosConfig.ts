import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL;

// Creo una instancia de Axios
const apiClient = axios.create({
  baseURL: API_URL,
  //timeout: 10000000, // Tiempo de espera 
});

/*
// Interceptor para agregar el token a las peticiones
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt_token');
    if (token) {
      config.headers['Authorization'] = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
*/

// Interceptor para manejar errores
/*
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.error('Unauthorized: Redirecting to login');
      window.location.href = '/login'; 
    }
    return Promise.reject(error);
  }
);
*/
export default apiClient;
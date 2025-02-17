import { useEffect, useState } from "react";
import { PostResponse, CommentResponse, NotificationData } from "./types";
import apiClient from "./axiosConfig";

const API_URL = import.meta.env.VITE_API_URL;



const useSSE = () => {
  const [notifications, setNotifications] = useState<NotificationData[]>([]);

  useEffect(() => {
    apiClient.get(`${API_URL}/notifications`)
    .then(response => {
      console.log("Prueba inicial exitosa:", response.data);
      // Si la petición con axios funciona, abrir SSE
      const eventSource = new EventSource(`${API_URL}/notifications`, { withCredentials: true });

      eventSource.onmessage = (event) => {
        console.log("Mensaje recibido:", event.data);
      };

      eventSource.onerror = (error) => {
        console.error("Error en SSE:", error);
        eventSource.close();
      };

      return () => {
        eventSource.close();
      };
    })
    .catch(error => {
      console.error("Error en la prueba inicial con axios:", error);
    });
}, []);

  return notifications;
};

export default useSSE;



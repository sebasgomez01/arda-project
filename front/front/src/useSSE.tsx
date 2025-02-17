import { useEffect, useState } from "react";
import { NotificationData } from "./types";

const API_URL = import.meta.env.VITE_API_URL;

const useSSE = () => {
  const [notifications, setNotifications] = useState<NotificationData[]>([]);

  useEffect(() => {
    const eventSource = new EventSource(`${API_URL}/notifications`, { withCredentials: true });

    eventSource.onmessage = (event) => {
      console.log("event", event);
      console.log("Mensaje recibido:", event.data);
      setNotifications((prev) => [...prev, event.data]);
    };

    eventSource.onerror = (error) => {
      console.error("Error en SSE:", error);
      eventSource.close();
      //setTimeout(connectSSE, 5000); // Intenta reconectar en 5 segundos
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return notifications;
};

export default useSSE;

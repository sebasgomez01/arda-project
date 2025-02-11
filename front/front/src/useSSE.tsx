import { useEffect, useState } from "react";
import { PostResponse, CommentResponse, NotificationData } from "./types";

const API_URL = import.meta.env.VITE_API_URL;



const useSSE = () => {
  const [notifications, setNotifications] = useState<NotificationData[]>([]);

  useEffect(() => {
    const eventSource = new EventSource(`${API_URL}/notifications`, { withCredentials: true });

    eventSource.onmessage = (event) => {
      const newNotification: NotificationData = JSON.parse(event.data);
      setNotifications((prev) => [...prev, newNotification]);
    };

    eventSource.onerror = () => {
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return notifications;
};

export default useSSE;

import Notification from './Notification';
import '../assets/NotificationList.css'
import useSSE from '../useSSE';
import { NotificationData } from '../types';

const NotificationList = () => {
    const notifications = useSSE();
    return (
    <div id='notificationListContainer'>
        {notifications.map((n) => {

            return (
                <Notification
                    key={n.id}
                    id={"1234"}
                    textContent={n.textContent}
                    causedBy={n.causedBy}
                    receivedBy={n.receivedBy}
                    post={n.post}
                    comment={n.comment}
                />
            );
        })}
    </div>
    );
};

    
export default NotificationList;
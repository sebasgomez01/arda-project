import '../assets/Notification.css'
import { NotificationData } from '../types';

const Notification = (props: NotificationData) => {
    return (
        <>
            <div id='notificationContainer'>
                <img id='notificationImage' src='#'></img>
                <p id='notificationText'> {props.textContent} </p>
            </div>
        </>
    );    
};

    
export default Notification;
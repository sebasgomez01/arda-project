import '../assets/LeftBarItem.css'
import { Link } from 'react-router-dom';
import apiClient from '../axiosConfig';

type LeftBarItemProps = {
    text: string;
    link: string;
}



const LeftBarItem = (props: LeftBarItemProps) => {
    
    const handleClick = async () => {
        if(props.text == "Log Out") {
            sessionStorage.setItem('jwt_token', 'null');
            sessionStorage.setItem('username', 'null');
            await apiClient.post("/logout");
        }
        
    }

    return (
        <div className="leftBarItemContainer" onClick={handleClick}>
            <a className="leftBarItemIcon"></a>
            <Link to={props.link}>
                <h3 className="leftBarItemText">  {props.text} </h3>
            </Link>
        </div>
    );
};

export default LeftBarItem;
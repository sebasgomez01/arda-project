import '../assets/LeftBarItem.css'
import { Link } from 'react-router-dom';

type LeftBarItemProps = {
    text: string;
    link: string;
}

const LeftBarItem = (props: LeftBarItemProps) => {
    return (
        <div className="leftBarItemContainer">
            <a className="leftBarItemIcon"></a>
            <Link to={props.link}>
                <h3 className="leftBarItemText">  {props.text} </h3>
            </Link>
        </div>
    );
};

export default LeftBarItem;
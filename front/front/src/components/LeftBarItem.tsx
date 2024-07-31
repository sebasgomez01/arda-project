import '../assets/LeftBarItem.css'

type LeftBarItemProps = {
    text: string;
}

const LeftBarItem = (props: LeftBarItemProps) => {
    return (
        <div className="leftBarItemContainer">
            <a className="leftBarItemIcon"></a>
            <h3 className="leftBarItemText">  {props.text} </h3>
        </div>
    );
};

export default LeftBarItem;
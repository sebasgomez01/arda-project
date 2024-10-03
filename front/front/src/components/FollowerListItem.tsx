import '../assets/FollowerListItem.css';


const FollowerListItem = () => {

    return (
        <>
            <div id='listItemContainer'>
                
                <img className='listItemProfilePicture' src='#'></img>
                <p className='name'> Lalo Landa </p>
                <p className='username'> @lalolanda </p>
                < button className='followButton'>Follow</button>
                                  
            </div>
        </>
    );
};

export default FollowerListItem;

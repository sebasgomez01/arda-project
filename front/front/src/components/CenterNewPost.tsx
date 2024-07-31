import '../assets/CenterNewPost.css'

const CenterNewPost = () => {
    return (
        <div className='centerNewPostContainer'>
            <div className='centerNewPostProfileImageContainer'>
                <img className='centerNewPostProfileImage' src="https://via.placeholder.com/150" alt="Imagen de marcador de posición">
                </img>
            </div>
            <div className='centerNewPostContent'> 
                <form className="centerNewPostContentForm">
                    <textarea className="centerNewPostContentText" placeholder="¿Qué está pasando?"></textarea>
                </form>
                <div className="centerNewPostContentButtons">
                    <div className="centerNewPostContentButtonsMultimedia">
                        <button className='centerNewPostContentMultimediaButton'>a</button>
                        <button className='centerNewPostContentMultimediaButton'>a</button>
                        <button className='centerNewPostContentMultimediaButton'>b</button>
                    </div>
                    <button className="centerNewPostButton">Postear</button>
                </div>
            </div>

        </div>
    );
};

export default CenterNewPost;
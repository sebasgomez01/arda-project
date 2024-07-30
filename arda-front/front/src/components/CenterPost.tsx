import '../assets/CenterPost.css'

type CenterPostProps = {
    title: string,
    textContent: string,
    user: string,
    imageBool: boolean
    
}

const CenterPost = (props: CenterPostProps) => {
  const itHasImage = props.imageBool;
  
  return (
    <div className="centerPostContainer">
        <div className="centerPostProfileImgContainer">
            <img className="centerPostProfileImg" src="https://via.placeholder.com/150" alt="Imagen de marcador de posición"></img>
        </div>
        <div className="centerPostContentContainer">
            <h5 className="centerPostContentAuthorInfo"> {props.user} </h5>
            <div className="centerPostContent">
                <p className="centerPostContentText"> {props.textContent} </p>
                { itHasImage && <img className='centerPostImage' src="https://via.placeholder.com/150" alt="Imagen de marcador de posición"></img> }
                { itHasImage && <p className="centerPostImgInfo"></p> }
                <div className="centerPostButtons">
                  <button className='postButton'>a</button>
                  <button className='postButton'>b</button>
                  <button className='postButton'>c</button>
                  <button className='postButton'>d</button>
                </div>
            </div>
        </div>
    </div>
  );
};

export default CenterPost;

import axios from 'axios';
import '../assets/CenterPost.css'

type CenterPostProps = {
    title: string,
    textContent: string,
    user: string,
    imageBool: boolean
    srcImage: string;   
}

const CenterPost = (props: CenterPostProps) => {

  return (
    <div className="centerPostContainer">
        <div className="centerPostProfileImgContainer">
            <img className="centerPostProfileImg" src="https://via.placeholder.com/150" alt="Imagen de marcador de posición"></img>
        </div>
        <div className="centerPostContentContainer">
            <h3 className="centerPostContentAuthorInfo">{props.title} </h3>
            <h5 className="centerPostContentAuthorInfo">from: {props.user} </h5>
            <div className="centerPostContent">
                <p className="centerPostContentText"> {props.textContent} </p>
                { props.srcImage && <img className='centerPostImage' src={props.srcImage} alt="Imagen de marcador de posición"></img> }
                { props.srcImage && <p className="centerPostImgInfo"></p> }
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

import '../assets/CenterPost.css'
import apiClient from '../axiosConfig';
import { PostData, PostCommentData } from '../types.ts'

type CenterPostProps = {
    title: string,
    textContent: string,
    user: string,
    imageBool: boolean
    srcImage: string; 
    id: string;  
    setReloadPosts: React.Dispatch<React.SetStateAction<boolean>>
    reloadPosts: boolean;
    setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>
    setShowPostWithComments: React.Dispatch<React.SetStateAction<boolean>>
    setPostCommentData: React.Dispatch<React.SetStateAction<PostCommentData>>
    setPostData: React.Dispatch<React.SetStateAction<PostData>>
}

const CenterPost = (props: CenterPostProps) => {
  
  const handleLike = async () => {
    await apiClient.patch(`/posts/like/${props.id}`);
  } 

  const handleDelete = async () => {
    await apiClient.delete(`/posts/${props.id}`); 
    props.setReloadPosts(true);
  }

  const handleComment = () => {
    props.setPostCommentData( { textContent: "", post: {id: props.id}, user: "" } );
    props.setShowCommentModal(true);
  }

  const handleRepost = async () => {
    await apiClient.patch(`/posts/repost/${props.id}`); 
    
  }

  const handleClick = () => {
    props.setPostData({
      title: props.title,
      textContent: props.textContent,
      user: props.user,
      imageBool: props.imageBool,
      srcImage: props.srcImage,
      id: props.id

    })
    props.setShowPostWithComments(true)
  }

  return (

    <div className="centerPostContainer" >
         
      
        <div className="centerPostContentContainer">
            <h3 className="centerPostContentAuthorInfo">{props.title} </h3>
            <h5 className="centerPostContentAuthorInfo">from: {props.user} </h5>
            <div className="centerPostContent" onClick={handleClick}>
                <p className="centerPostContentText"> {props.textContent} </p>
                { props.srcImage && <img className='centerPostImage' src={ `${import.meta.env.VITE_API_URL}/posts/image/${props.srcImage}`} alt="Imagen del post"></img> }
                { props.srcImage && <p className="centerPostImgInfo"></p> }
                
            </div>
            <div className="centerPostButtons">
                  <button className='postButton' onClick={handleLike} >Me gusta</button>
                  <button className='postButton' onClick={handleComment} >Comentar</button>
                  <button className='postButton'onClick={handleRepost}>Repost</button>
                  <button className='postButton' onClick={handleDelete}>Eliminar</button>
            </div>
        </div>
    </div>
  );
};

export default CenterPost;

/**
 
        <div className="centerPostProfileImgContainer">
            <img className="centerPostProfileImg" src="#" alt="Imagen de marcador de posición"></img>
        </div>
 */

        
import '../assets/Comment.css'
import apiClient from '../axiosConfig'

type CommentProps = {
    name: string,
    username: string,
    textContent: string,
    id: string
}

const Comment = (props: CommentProps) => {
    const handleLike = async () => {   
        await apiClient.patch(`comments/like/${props.id}`)
    }

    const handleDislike = async () => {   
        await apiClient.patch(`comments/dislike/${props.id}`)
    }
    
    return (
        <div className="commentContainer">
            <img className="commentImage" src="#"></img>
            <div className="commentContent">
                <p className="commentAuthor pElement" > {`${props.name} @${props.username}`} </p>
                <p className="commentTextContent pElement"> { props.textContent } </p>
                <button className="likeButton" onClick={handleLike}>Like</button>
                <button className="dislikeButton"onClick={handleDislike}>Dislike</button>
            </div>
        </div>
    )
}

export default Comment;
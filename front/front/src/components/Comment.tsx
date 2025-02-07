import '../assets/Comment.css'

type CommentProps = {
    name: string,
    username: string,
    textContent: string
}

const Comment = (props: CommentProps) => {
    
    return (
        <div className="commentContainer">
            <img className="commentImage" src="#"></img>
            <div className="commentContent">
                <p className="commentAuthor pElement" > {`${props.name} @${props.username}`} </p>
                <p className="commentTextContent pElement"> { props.textContent } </p>
                <button className="likeButton">Like</button>
                <button className="dislikeButton">Dislike</button>
            </div>
        </div>
    )
}

export default Comment;
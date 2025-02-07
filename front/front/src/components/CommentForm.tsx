import "../assets/CommentForm.css"
import { useState } from 'react';
import { PostCommentData } from "../types";
import apiClient from "../axiosConfig";

type CommentFormProps =  {
    postCommentData: PostCommentData;
    setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>
}

const CommentForm = (props: CommentFormProps) => {
    const [textContent, setTextContent] = useState<string>('');


    const handleSubmit = async () => {
        props.postCommentData.textContent = textContent;
        try {
            const response = await apiClient.post("/comments", props.postCommentData);
            console.log(response);
            setTextContent("");
            props.setShowCommentModal(false);
        } catch(error) {
            console.error('Error sending comment', error);
        }
    }

    return (
        <div className='commentFormContainer'>

            
            <div className='commentFormContent'> 
                <form className="commentFormContentForm" onSubmit={handleSubmit}>
                    <textarea 
                        className="commentFormContentText" 
                        placeholder="Escribe tu comentario"
                        value={textContent}
                        onChange={(e) => setTextContent(e.target.value)}>
                    </textarea>
                </form>
                <div className="commentFormContentButtons">
                    
                    <button type="submit" className="centerNewPostButton" onClick={handleSubmit} >Postear</button>
                </div>
            </div>

        </div>
        
    )
}

export default CommentForm;
import "../assets/CommentForm.css"
import { useState } from 'react';

const CommentForm = () => {
    const [textContent, setTextContent] = useState<string>('');

    const handleSubmit = () => {

    }

    return (
        <div className='commentFormContainer'>
            <p>Comentario al post NombreDelPost de Autor</p>
            
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
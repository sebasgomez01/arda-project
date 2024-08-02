import '../assets/CenterNewPost.css'
import { useState } from 'react';
import axios from 'axios'; 

const CenterNewPost = () => {
    const [title, setTitle] = useState('');
    const [textContent, setTextContent] = useState('');

     const handleSubmit = async (event) => {
        event.preventDefault();

        const postData = {
            title: title,
            textContent: textContent,
        };

        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/posts`, postData);
            console.log('Post created successfully:', response.data);
            setTitle('');
            setTextContent('');
        } catch (error) {
            console.error('Error creating post:', error);
        }
    }


    return (
        <div className='centerNewPostContainer'>
            <div className='centerNewPostProfileImageContainer'>
                <img className='centerNewPostProfileImage' src="https://via.placeholder.com/150" alt="Imagen de marcador de posición">
                </img>
            </div>
            <div className='centerNewPostContent'> 
                <form className="centerNewPostContentForm" onSubmit={handleSubmit}>
                    <textarea 
                        className="centerNewPostContentTitle" 
                        placeholder="Título del post" 
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}>
                    </textarea>
                    <textarea 
                        className="centerNewPostContentText" 
                        placeholder="¡¿Qué quieres contarnos?!"
                        value={textContent}
                        onChange={(e) => setTextContent(e.target.value)}>
                    </textarea>
                </form>
                <div className="centerNewPostContentButtons">
                    <div className="centerNewPostContentButtonsMultimedia">
                        <button type="button" className='centerNewPostContentMultimediaButton'>a</button>
                        <button type="button" className='centerNewPostContentMultimediaButton'>a</button>
                        <button type="button" className='centerNewPostContentMultimediaButton'>b</button>
                    </div>
                    <button type="submit" className="centerNewPostButton" onClick={handleSubmit} >Postear</button>
                </div>
            </div>

        </div>
    );
};

export default CenterNewPost;
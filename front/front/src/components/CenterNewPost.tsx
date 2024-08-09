import '../assets/CenterNewPost.css'
import { useState } from 'react';
import axios from 'axios'; 

const CenterNewPost = () => {
    const [title, setTitle] = useState('');
    const [textContent, setTextContent] = useState('');
    const [image, setImage] = useState('');  // Nuevo estado para la imagen
    const [imageBool, setImageBool] = useState(false);

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

    const handleImageUpload = (e) => {
        setImage(URL.createObjectURL(e.target.files[0]))
        setImageBool(true);
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
                    { imageBool && <img className='centerNewPostImage' src={image}></img> }
                </form>
                <div className="centerNewPostContentButtons">
                    <div className="centerNewPostContentButtonsMultimedia">
                        <input 
                            type="file" 
                            accept="image/*" 
                            style={{ display: 'none' }} 
                            id="imageUpload" 
                            onChange={handleImageUpload}
                             
                        />
                        <label htmlFor="imageUpload" className='centerNewPostContentMultimediaButton'>Subir Imagen</label>
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
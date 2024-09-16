import '../assets/CenterNewPost.css'
import { useState } from 'react';
import axios, { AxiosRequestConfig } from 'axios'; 

const getAxiosConfig = (): AxiosRequestConfig => {
    const token = sessionStorage.getItem("jwt");
    return {
        headers: {
            'Authorization': token,
            //'Content-Type': 'application/json',
        },
    }; 
  };

interface ComponentAProps {
    setNewPostMessage: React.Dispatch<React.SetStateAction<string>>;
}

const CenterNewPost: React.FC<ComponentAProps> = ( {setNewPostMessage} ) => {
    const [title, setTitle] = useState('');
    const [textContent, setTextContent] = useState('');
    const [selectedFile, setSelectedFile] = useState<File | null>(null);  // Nuevo estado para la imagen
    const [previewImage, setPreviewImage] = useState<string | null>(null); // estado para el link temporal a la imagen
    const [imageBool, setImageBool] = useState(false);

     const handleSubmit = async (event) => {
        event.preventDefault();

        const postData = {
            title: title,
            textContent: textContent,
            imagePath: "",
            username: sessionStorage.getItem("username")
        };

        let postIdentifier: string;
        
        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/posts`, postData, getAxiosConfig());
            console.log("response: ")
            console.log(response)
            postIdentifier = response.data.id;
            console.log(postIdentifier);
            console.log('Post created successfully:', response.data);
            setTitle('');
            setTextContent('');
            // mando la imagen 
            await handleUpload(postIdentifier);
            setNewPostMessage('New post!!!')
        } catch (error) {
            console.error('Error creating post:', error);
        }

        
    }

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            const file = event.target.files[0];
            setSelectedFile(file);
            setImageBool(true);
            setPreviewImage(URL.createObjectURL(file)); // Genera una URL temporal para la previsualización
        }
    };

    const handleUpload = async (postIdentifier: string) => {
        if (!selectedFile) return;

        const formData = new FormData();
        formData.append('file', selectedFile);
        formData.append('postIdentifier', postIdentifier);

        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/posts/image`, formData, 
                getAxiosConfig()

                /*{
                    headers: {
                    'Content-Type': 'multipart/form-data',
                },
                }
                */
            );
            console.log('Image uploaded successfully:', response.data);
            setPreviewImage(null);
            setSelectedFile(null);
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    };

    return (
        <div className='centerNewPostContainer'>
            <div className='centerNewPostProfileImageContainer'>
                <img className='centerNewPostProfileImage' src="#" alt="Imagen">
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
                { previewImage && <img className='centerNewPostImage' src={previewImage} alt="Preview"></img> }
                <div className="centerNewPostContentButtons">
                    
                        <input 
                            type="file" 
                            accept="image/*" 
                            style={{ display: 'none' }} 
                            id="imageUpload" 
                            onChange={handleFileChange}
                             
                        />
                        <label htmlFor="imageUpload" className='labelUploadImage'>Subir Imagen</label>
            
                    
                    <button type="submit" className="centerNewPostButton" onClick={handleSubmit} >Postear</button>
                </div>
            </div>

        </div>
    );
};

export default CenterNewPost;
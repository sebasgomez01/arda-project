import '../assets/CenterPost.css'
import apiClient from '../axiosConfig';

type CenterPostProps = {
    title: string,
    textContent: string,
    user: string,
    imageBool: boolean
    srcImage: string; 
    id: string;  
}
/*
const deleteElem = async () => {
  // Realizo la petición DELETE
  apiClient.delete(`/posts/${bookID}`)
    .then(response => {
      //console.log('Libro eliminado correctamente', response.data);
    deleteEvent();
    })
    .catch(error => {
      //console.error('Error al eliminar el libro', error);
    emit('sessionExpired');
    });

    
      // Ejecuto la función deleteEvent que emite el evento deleteItem para que lo escuche el componente App.vue
    ;
}
  */

const CenterPost = (props: CenterPostProps) => {

  return (
    <div className="centerPostContainer">
      
      
        <div className="centerPostContentContainer">
            <h3 className="centerPostContentAuthorInfo">{props.title} </h3>
            <h5 className="centerPostContentAuthorInfo">from: {props.user} </h5>
            <div className="centerPostContent">
                <p className="centerPostContentText"> {props.textContent} </p>
                { props.srcImage && <img className='centerPostImage' src={ `${import.meta.env.VITE_API_URL}/posts/image`} alt="Imagen del post"></img> }
                { props.srcImage && <p className="centerPostImgInfo"></p> }
                <div className="centerPostButtons">
                  <button className='postButton'>Me gusta</button>
                  <button className='postButton'>Comentar</button>
                  <button className='postButton'>Editar</button>
                  <button className='postButton'>Eliminar</button>
                </div>
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
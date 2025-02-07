import '../assets/CommentList.css'
import { useState, useEffect } from 'react';
import apiClient from '../axiosConfig';
import Comment from './Comment';
import {CommentResponse} from '../types.ts'

const CommentList = (  ) => {
    const [comments, setComments] = useState<CommentResponse[]>([]); 


    useEffect(() => {
      const getComments = async () => {
        const response = await apiClient.get(`/comments`);
        console.log("response:", response);
        console.log("data:", response.data);
        setComments(response.data._embedded.comments);
        return response.data;
      }

      getComments();
      //props.setReloadPosts(false);
    }, [/*props.reloadPosts*/]);
    
    return (
         <>
            {comments.map((comment: CommentResponse) => {
                return <Comment
                  key={comment._links.self.href}
                  name={comment.user.name}
                  username={comment.user.username}
                  textContent={comment.textContent}
                />
            })}
         </> 
           
    )
}

export default CommentList;
import '../assets/CommentList.css'
import { useState, useEffect } from 'react';
import apiClient from '../axiosConfig';
import Comment from './Comment';
import {CommentResponse, PostCommentData} from '../types.ts'

type CommentListProps = {
      postId: string;
      reloadPosts: boolean;
      setReloadPosts: React.Dispatch<React.SetStateAction<boolean>>;
      setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>;
      setPostCommentData: React.Dispatch<React.SetStateAction<PostCommentData>>;
}

const CommentList = (props: CommentListProps) => {
    const [comments, setComments] = useState<CommentResponse[]>([]); 

    useEffect(() => {
      const getComments = async () => {
        const response = await apiClient.get(`/posts/${props.postId}/comments`);
        console.log("response:", response);
        console.log("COMENTARIOS DEL POST 1:", response.data);
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
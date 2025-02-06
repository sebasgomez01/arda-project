import axios, { AxiosRequestConfig } from "axios";
import apiClient from "../axiosConfig";
import { useQuery } from '@tanstack/react-query';
import { PostResponse, UserResponse } from "../types";
import CenterPost from "./CenterPost";
import { useState, useEffect } from 'react';

/*
  const replaceLinkByUsername = async (post: PostResponse) => {
      const hrefUser = post._links.user.href;
      const updatedUrl = hrefUser.replace("http://localhost:8080", import.meta.env.VITE_API_URL);
      const response = await axios.get(updatedUrl); // obtengo
      const user: UserResponse = response.data;
      console.log("response: ", response);
      console.log("user: ", user);
      return user.username;
  }
*/


interface ComponentBProps {
    newPostMessage: string;
    setReloadPosts: React.Dispatch<React.SetStateAction<boolean>>
    setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>
    reloadPosts: boolean;
}



const Postlist: React.FC<ComponentBProps> = ( {newPostMessage, reloadPosts, setReloadPosts, setShowCommentModal} ) => {
    const [posts, setPosts] = useState<PostResponse[]>([]); 

    console.log( newPostMessage )

    useEffect(() => {
      const getPosts = async (): Promise<PostResponse[]> => {
        //const token = sessionStorage.getItem("jwt");
        //console.log("El token obtenido de sessionStorage es:", token);
        const response = await apiClient.get("/posts");
        console.log("response:", response);
        console.log("data:", response.data);
        setPosts(response.data._embedded.posts);
        return response.data;
      }

      getPosts();
      setReloadPosts(false);
    }, [reloadPosts]);
    
    function getPostId(url: string): string {
      return url.substring(url.lastIndexOf("/") + 1);
    }

    return (
      <>
          {
              posts.map((post: PostResponse) => {
                  let postId:string = getPostId(post._links.self.href);

                  return (
                    <CenterPost
                      key={post._links.self.href}
                      title={post.title}
                      textContent={post.textContent}
                      user={post.user.username || "Loading..."} // Muestra "Loading..." mientras se resuelve la promesa
                      imageBool={true}
                      srcImage={post.imagePath}
                      id={postId}
                      setReloadPosts={setReloadPosts}
                      reloadPosts={reloadPosts}
                      setShowCommentModal={setShowCommentModal}
                    />
                  );
              })
          }
      </>
  );
};

export default Postlist;
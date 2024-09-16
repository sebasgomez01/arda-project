import axios, { AxiosRequestConfig } from "axios";
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
}

const getAxiosConfig = (): AxiosRequestConfig => {
  const token = sessionStorage.getItem("jwt");
  return {
      headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
      },
  }; 
};


const Postlist: React.FC<ComponentBProps> = ( {newPostMessage} ) => {


    console.log( newPostMessage )

    const getPosts = async (): Promise<PostResponse[]> => {
        //const token = sessionStorage.getItem("jwt");
        //console.log("El token obtenido de sessionStorage es:", token);
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/posts`, getAxiosConfig());
        console.log("data:", response);
        return response.data._embedded.posts;
    }

    const { data, error, isSuccess } = useQuery({
        queryKey: ["posts"],
        queryFn: getPosts
    });

    if (!isSuccess) {
        return <span>Loading...</span>
    }
    else if (error) {
        return <span>Error when fetching cars...</span>
    }
    else {
        return (
            <>
                {
                    data.map((post: PostResponse) => {
                        //const [username, setUsername] = useState<string | null>(null);
                      /*
                        useEffect(() => {
                          replaceLinkByUsername(post).then(result => {
                            setUsername(result);
                          });
                        }, [post]);
                
                        let filename: string | undefined;
                        let imageURL: string;
                
                        if (post.imagePath) {
                          filename = post.imagePath.split('/').pop(); // obtengo el nombre 
                          imageURL = `${import.meta.env.VITE_API_URL}/api/posts/image/${filename}`;
                        } else {
                          imageURL = post.imagePath;
                        }
                        */
                        return (
                          <CenterPost
                            key={post._links.self.href}
                            title={post.title}
                            textContent={post.textContent}
                            user={post.user.username || "Loading..."} // Muestra "Loading..." mientras se resuelve la promesa
                            imageBool={false}
                            srcImage={post.imagePath}
                          />
                        );
                    })
                }
            </>
        );
    }
};

export default Postlist;
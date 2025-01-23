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
        const response = await apiClient.get("/posts");
        console.log("response:", response);
        console.log("post 0:", response.data[0]);
        console.log("post 1:", response.data[1]);
        console.log("data:", response.data);
        return response.data;
    }
    getPosts();

    const data = [];

    return (
      <>
          {
              data.map((post: PostResponse) => {
                  //console.log(post._links.self.href)
                  return (
                    <CenterPost
                      key={post._links.self.href}
                      title={post.title}
                      textContent={post.textContent}
                      user={post.user.username || "Loading..."} // Muestra "Loading..." mientras se resuelve la promesa
                      imageBool={false}
                      srcImage={post.imagePath}
                      id={post._links.self.href}
                    />
                  );
              })
          }
      </>
  );
};

export default Postlist;
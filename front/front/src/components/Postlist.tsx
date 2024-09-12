import axios from "axios";
import { useQuery } from '@tanstack/react-query';
import { PostResponse, UserResponse } from "../types";
import CenterPost from "./CenterPost";

const Postlist = () => {

    const getPosts = async (): Promise<PostResponse[]> => {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/posts`);
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
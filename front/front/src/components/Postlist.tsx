import axios from "axios";
import { useQuery } from '@tanstack/react-query';
import { PostResponse } from "../types";
import CenterPost from "./CenterPost";

const Postlist = () => {
    const getPosts = async (): Promise<PostResponse[]> => {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/posts`);
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

                        let filename: string | undefined;
                        let imageURL: string;

                        if(post.imagePath) {
                            filename = post.imagePath.split('/').pop(); // obtengo el nombre 
                            imageURL = `${import.meta.env.VITE_API_URL}/api/posts/image/${filename}`;
                        } else {
                            imageURL = post.imagePath;
                        }
                        
                        return (
                            <CenterPost 
                            key={post._links.self.href}
                            title={post.title} textContent={post.textContent} user={post._links.user.href} 
                            imageBool={false}
                            srcImage={imageURL}
                        />
                        )
                        
                    })
                }
            </>
        );
    }
};

export default Postlist;
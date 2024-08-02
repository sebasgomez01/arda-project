import axios from "axios";
import { useQuery } from '@tanstack/react-query';
import { PostResponse } from "../types";
import CenterPost from "./CenterPost";

const apiURL: string = "https://sturdy-space-giggle-679gg695r6phrggw-8080.app.github.dev/";



const Postlist = () => {
    const getPosts = async (): Promise<PostResponse[]> => {
        const response = await axios.get(apiURL + "api/posts");
        return response.data._embedded.posts;
    }

    const { data, error, isSuccess } = useQuery({
        queryKey: ["posts"],
        queryFn: getPosts
    });

    console.log("El contenido de data es:")
    console.log(data);

    
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
                            title={post.title} textContent={post.textContent} user={post._links.user.href} 
                            imageBool={false}
                        />
                        )
                        
                    })
                }
            </>
        );
    }
};

export default Postlist;
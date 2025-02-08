import CenterPost from "./CenterPost";
import CommentList from "./CommentList";
import { PostCommentData } from "../types";

type CenterPostWIthCommentsProps = {
    newPostMessage: string;
    reloadPosts: boolean;
    setReloadPosts: React.Dispatch<React.SetStateAction<boolean>>;
    setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>;
    setPostCommentData: React.Dispatch<React.SetStateAction<PostCommentData>>;
}


const CenterPostWithComments = (props: CenterPostWIthCommentsProps) => {

    return (
        <>
            <CenterPost/>
            <CommentList  reloadPosts={props.reloadPosts} setReloadPosts={props.setReloadPosts} 
                setShowCommentModal={props.setShowCommentModal} setPostCommentData={props.setPostCommentData}/>
        </>

    )
}   

export default CenterPostWithComments;
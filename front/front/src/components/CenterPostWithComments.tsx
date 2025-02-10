import { useState } from "react";
import CenterPost from "./CenterPost";
import CommentList from "./CommentList";
import { PostCommentData, PostData } from "../types";

type CenterPostWIthCommentsProps = {
    newPostMessage: string;
    reloadPosts: boolean;
    postData: PostData
    reloadComments: boolean
    setReloadPosts: React.Dispatch<React.SetStateAction<boolean>>;
    setShowCommentModal: React.Dispatch<React.SetStateAction<boolean>>;
    setShowPostWithComments: React.Dispatch<React.SetStateAction<boolean>>;
    setReloadComments: React.Dispatch<React.SetStateAction<boolean>>;
    setPostCommentData: React.Dispatch<React.SetStateAction<PostCommentData>>;
    setPostData: React.Dispatch<React.SetStateAction<PostData>>;
}

const CenterPostWithComments = (props: CenterPostWIthCommentsProps) => {
    

    return (
        <>
            <div onClick={() => { props.setShowPostWithComments(false) }}> 

                <h2>Get back to posts</h2></div>
            <CenterPost 
                title={props.postData.title}
                textContent={props.postData.textContent}
                user={props.postData.user} 
                imageBool={props.postData.imageBool}
                srcImage={props.postData.srcImage}
                id={props.postData.id}
                setReloadPosts={props.setReloadPosts}
                reloadPosts={props.reloadPosts}
                setShowCommentModal={props.setShowCommentModal}
                setPostCommentData= {props.setPostCommentData}
                setShowPostWithComments={props.setShowPostWithComments}
                setPostData={props.setPostData}

            />
            <CommentList postId={props.postData.id}  
                reloadPosts={props.reloadPosts} setReloadPosts={props.setReloadPosts} 
                reloadComments={props.reloadComments} setReloadComments={props.setReloadComments}
                setShowCommentModal={props.setShowCommentModal} setPostCommentData={props.setPostCommentData}/>
        </>

    )
}   

export default CenterPostWithComments;
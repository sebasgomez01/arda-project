import LeftBarItem from "./LeftBarItem";
import LeftBarPostButton from "./LeftBarPostButton";
import '../assets/Home.css'
import CenterNewPost from "./CenterNewPost";
import Postlist from "./Postlist"
import NotificationList from "./NotificationList"
import CommentModal from "./CommentModal";
import { useState } from "react";
import CommentForm from "./CommentForm";
import CenterPostWIthComments from './CenterPostWithComments';
import { PostCommentData, PostData } from "../types";


const Home = () => {
    
    const [newPostMessage, setNewPostMessage] = useState<string>('');
    const [reloadPosts, setReloadPosts] = useState<boolean>(false);
    const [showCommentModal, setShowCommentModal] = useState<boolean>(false);
    const [postCommentData, setPostCommentData] = useState<PostCommentData>({textContent: "", post: {id: ""}, user: "" });
    const [showPostWithComments, setShowPostWithComments] = useState<boolean>(false);
    const [reloadComments, setREloadComments] = useState<boolean>(false);
    const [postData, setPostData] = useState<PostData>({
        title: "",
        textContent: "",
        user: "",
        imageBool: false,
        srcImage: "",
        id: ""
    });

    return (
        <>
            
            <div className="left">
                <h1 className='logo'>arda</h1>
                <LeftBarItem text='Home' link='/home' />
                <LeftBarItem text='Profile' link='/profile' />
                <LeftBarItem text='Log Out' link='/login'/>
                <LeftBarPostButton />
            </div>
            <div className="center">
                { !showPostWithComments && <CenterNewPost setNewPostMessage={setNewPostMessage} setReloadPosts={setReloadPosts} /> }
                { !showPostWithComments && <Postlist newPostMessage={newPostMessage} reloadPosts={reloadPosts} setReloadPosts={setReloadPosts} 
                setShowCommentModal={setShowCommentModal} setPostCommentData={setPostCommentData} 
                setShowPostWithComments={setShowPostWithComments} 
                setPostData={setPostData} /> }
                
                { showPostWithComments && <CenterPostWIthComments newPostMessage={newPostMessage} reloadPosts={reloadPosts} setReloadPosts={setReloadPosts} 
                setShowCommentModal={setShowCommentModal} setPostCommentData={setPostCommentData}
                postData={postData} setShowPostWithComments={setShowPostWithComments} setPostData={setPostData}
                reloadComments={reloadComments} setReloadComments={setREloadComments}
                />}
            </div>
            <div className="right">
                <h2 className='logo'>Notifications</h2>
                <NotificationList />
            </div>
            <CommentModal isOpen={showCommentModal} onClose={() => setShowCommentModal(false)} >
                <CommentForm postCommentData={postCommentData} setShowCommentModal={setShowCommentModal} setReloadComments={setREloadComments} />
            </CommentModal>
        </>
    );
};

export default Home;


import LeftBarItem from "./LeftBarItem";
import LeftBarPostButton from "./LeftBarPostButton";
import '../assets/Home.css'
import CenterNewPost from "./CenterNewPost";
import Postlist from "./Postlist"
import NotificationList from "./NotificationList"
import CommentList from "./CommentList";
import CommentModal from "./CommentModal";
import { useState } from "react";
import CommentForm from "./CommentForm";
import CenterPostWIthComments from './CenterPostWithComments';
import { PostCommentData } from "../types";


const Home = () => {
    
    const [newPostMessage, setNewPostMessage] = useState<string>('');
    const [reloadPosts, setReloadPosts] = useState<boolean>(false);
    const [showCommentModal, setShowCommentModal] = useState<boolean>(false);
    const [postCommentData, setPostCommentData] = useState<PostCommentData>({textContent: "", post: {id: ""}, user: "" });

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
                <CenterNewPost setNewPostMessage={setNewPostMessage} setReloadPosts={setReloadPosts} />
                <Postlist newPostMessage={newPostMessage} reloadPosts={reloadPosts} setReloadPosts={setReloadPosts} 
                setShowCommentModal={setShowCommentModal} setPostCommentData={setPostCommentData} />
                
                <CenterPostWIthComments newPostMessage={newPostMessage} reloadPosts={reloadPosts} setReloadPosts={setReloadPosts} 
                setShowCommentModal={setShowCommentModal} setPostCommentData={setPostCommentData}/>
            </div>
            <div className="right">
                <h2 className='logo'>Notifications</h2>
                <NotificationList />
            </div>
            <CommentModal isOpen={showCommentModal} onClose={() => setShowCommentModal(false)} >
                <CommentForm postCommentData={postCommentData} setShowCommentModal={setShowCommentModal} />
            </CommentModal>
        </>
    );
};

export default Home;


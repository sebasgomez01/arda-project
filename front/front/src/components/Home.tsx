import LeftBarItem from "./LeftBarItem";
import LeftBarPostButton from "./LeftBarPostButton";
import '../assets/Home.css'
import CenterNewPost from "./CenterNewPost";
import Postlist from "./Postlist"
import NotificationList from "./NotificationList"
import { useState } from "react";

const Home = () => {
    
    const [newPostMessage, setNewPostMessage] = useState<string>('');

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
                <CenterNewPost setNewPostMessage={setNewPostMessage} />
                <Postlist newPostMessage={newPostMessage} />
            </div>
            <div className="right">
                <h2 className='logo'>Notifications</h2>
                <NotificationList />
            </div>
        </>
    );
};

export default Home;


/*

    <CenterPost title={ propsCenterPost1.title}  textContent={ propsCenterPost1.textContent}
                            user={ propsCenterPost1.user} imageBool={ propsCenterPost1.imageBool}  />
                <CenterPost title={ propsCenterPost2.title}  textContent={ propsCenterPost2.textContent}
                            user={ propsCenterPost2.user} imageBool={ propsCenterPost2.imageBool}/>
                <CenterPost title={ propsCenterPost2.title}  textContent={ propsCenterPost2.textContent}
                            user={ propsCenterPost2.user} imageBool={ propsCenterPost2.imageBool}/>
*/

/* 
    <LeftBarItem text='EXplorar' /> 
                <LeftBarItem text='Mensajes' />
                <LeftBarItem text='Grook' />
                <LeftBarItem text='Comunidades' />
                <LeftBarItem text='Más opciones' />
*/
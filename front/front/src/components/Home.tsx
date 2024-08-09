import LeftBarItem from "./LeftBarItem";
import LeftBarPostButton from "./LeftBarPostButton";

import CenterNewPost from "./CenterNewPost";
import CenterTopBar from "./CenterTopBar";
import Postlist from "./Postlist"
import axios from "axios";
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();



let propsCenterPost1 = {
    title: "hola qué tal",
    textContent: "Buenas gente cómo están? Yo todo bien por acá, muchas gracias por preguntar.",
    user: "kamikaze",
    imageBool: false
}

let propsCenterPost2 = {
    title: "hola qué tal",
    textContent: "Buenas gente cómo están? Yo todo bien por acá, muchas gracias por preguntar.",
    user: "kamikaze",
    imageBool: true
}

const apiURL: string = "https://sturdy-space-giggle-679gg695r6phrggw-8080.app.github.dev";
axios.get(apiURL + "/api/posts")
.then(response => console.log(response))
.catch(error => console.log(error))

const Home = () => {
    return (
        <>
            <div className="left">
                <h1 className='logo'>arda</h1>
                <LeftBarItem text='Inicio' />
                <LeftBarItem text='Noficaciones' />
                <LeftBarItem text='Perfil' />
                <LeftBarPostButton />
            </div>
            <div className="center">
                <CenterNewPost />
                <Postlist />
            </div>
            <div className="right">
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
import LeftBarItem from "./LeftBarItem";
import LeftBarPostButton from "./LeftBarPostButton";
import CenterPost from "./CenterPost";
import CenterNewPost from "./CenterNewPost";
import CenterTopBar from "./CenterTopBar";


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

const Home = () => {
    return (
        <>
            <div className="left">
                <h1 className='logo'>arda</h1>
                <LeftBarItem text='Inicio' />
                <LeftBarItem text='EXplorar' />
                <LeftBarItem text='Noficaciones' />
                <LeftBarItem text='Mensajes' />
                <LeftBarItem text='Grook' />
                <LeftBarItem text='Comunidades' />
                <LeftBarItem text='Perfil' />
                <LeftBarItem text='Más opciones' />
                <LeftBarPostButton />
            </div>
            <div className="center">
                <CenterTopBar/>
                <CenterNewPost />
                <CenterPost title={ propsCenterPost1.title}  textContent={ propsCenterPost1.textContent}
                            user={ propsCenterPost1.user} imageBool={ propsCenterPost1.imageBool}  />
                <CenterPost title={ propsCenterPost2.title}  textContent={ propsCenterPost2.textContent}
                            user={ propsCenterPost2.user} imageBool={ propsCenterPost2.imageBool}/>
                <CenterPost title={ propsCenterPost2.title}  textContent={ propsCenterPost2.textContent}
                            user={ propsCenterPost2.user} imageBool={ propsCenterPost2.imageBool}/>
            </div>
            <div className="right">
            </div>
        </>
    );
};

export default Home;
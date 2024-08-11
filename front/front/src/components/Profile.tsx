import '../assets/Profile.css';
import LeftBarItem from './LeftBarItem';
import LeftBarPostButton from './LeftBarPostButton';
import ProfileInfo from './ProfileInfo';

const Profile = () => {
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
                <ProfileInfo />
            </div>
            <div className="right">
            </div>
        </>
    );
};

export default Profile;
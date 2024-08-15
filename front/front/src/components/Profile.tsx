import { useState } from 'react';
import '../assets/Profile.css';
import LeftBarItem from './LeftBarItem';
import LeftBarPostButton from './LeftBarPostButton';
import ProfileInfo from './ProfileInfo';
import Postlist from './Postlist';

const Profile = () => {
    const [stateArray, setStateArray] = useState<Array<number>>([1, 0, 0, 0]);

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
                { stateArray[0] && <Postlist /> } 
            </div>
            <div className="right">
            </div>
        </>
    );
};

export default Profile;
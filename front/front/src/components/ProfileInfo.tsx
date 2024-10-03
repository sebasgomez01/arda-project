import { useState } from 'react';
import '../assets/ProfileInfo.css'


const ProfileInfo = () => {
    const [profileOwner, setProfileOwner] = useState(false);

    return (
        <>
        
            <div className='profileInfo'>
                <div className='profileInfoContainer'>
                    <img className='profilePicture' src="https://via.placeholder.com/150"></img>
                    <div className='profileDetails'>
                        <h4 className='withoutMargin'>Firstname Secondname</h4>
                        <p className='withoutMargin'> usernamelalala</p>
                    </div>
                    <div className='profileActions'>
                        { profileOwner && <button className='configurationButton'>Configurar perfil</button>}
                        { !profileOwner && <button className='notificationButton'>Activar notifiaciones</button> }
                        <button className='followButton'>Seguir</button>
                    </div>
                </div>
                <div className='profileInfoButtonsContainer'>
                            <button className='profileInfoButton'>Posts</button>
                            <button className='profileInfoButton'>Seguigores</button>
                            <button className='profileInfoButton'>Seguidos</button>
                            <button className='profileInfoButton'>Me gusta</button>
                    </div>
                </div>
            <div className='profileListContainer'>
            </div>
            
        </>
    )
}

export default ProfileInfo;
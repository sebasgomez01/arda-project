import { useState, useEffect } from 'react';
import '../assets/Login.css'
import { useLocation, Link } from 'react-router-dom';

const Login = () => {
    const location = useLocation();
    const [newAccountMessage, setNewAccountMessage] = useState(false);
    

    useEffect(() => {
            if (location.state?.fromCreateAccount) {
                setNewAccountMessage(true);
            }
        },
        [location.state]
    );

    return (
        <>
            <div className='loginModalContainer'>
                { newAccountMessage && 
                    <h3 className='succesfullyCreatedAccountMessage' >
                    Cuenta creada exitósamente. <br></br> 
                    ¡Inicia sesión para empezar a compartir posts!
                    </h3>
                }
                <div className='loginModal'>
                    <h1 className='loginModalTitle'>Sign in to arda</h1>
                    <form action="/login" method="post" className='loginForm'>
                        <label for="username"></label>
                        <input className='loginFormField' type="text" id="username" name="username" placeholder='Username' required></input>
                        <label for="password"></label>
                        <input className='loginFormField' type="password" id="password" name="password" placeholder='Password' required></input>
                        <button className='loginModalButtons loginModalSignInButton' type="submit">Sign in</button>
                        <button className='loginModalButtons loginModalForgotPasswordButton' >Forgot password?</button>
                        { !newAccountMessage && <p className='loginFormPElem'>Don't you hace an account? <Link to="/create-account">Create one</Link> </p> }
                    </form>
                    
                </div>
            </div>
        </>
    );
};

export default Login;
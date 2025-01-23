import { useState, useEffect } from 'react';
import '../assets/Login.css';
import { useLocation, Link, useNavigate } from 'react-router-dom';
import apiClient from '../axiosConfig.ts';

const Login = () => {
    const location = useLocation();
    const navigate = useNavigate(); // Hook para navegar a otra ruta
    const [newAccountMessage, setNewAccountMessage] = useState<boolean>(false);//useState(false);
    const [username, setUsername] = useState<string>(''); // Estado para username
    const [password, setPassword] = useState<string>(''); // Estado para password

    useEffect(() => {
        if (location.state?.fromCreateAccount) {
            setNewAccountMessage(true);
        }
    }, [location.state]);

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevenir el comportamiento por defecto del formulario

        const loginData = {
            username: username,
            password: password
        };
        
        // guardo el username en sessionStorage
        sessionStorage.setItem("username", loginData.username); // guardo el token en sessionStorage

        try {
            const response = await apiClient.post("/users/login", loginData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log('Login successful:', response.data);
            const jwtToken = response.headers.authorization;
            if (jwtToken !== null) {
                console.log("El token es:", jwtToken)
                sessionStorage.setItem("jwt_token", jwtToken); // guardo el token en sessionStorage
                navigate('/home'); // Redirigir a la página de inicio
                //setAuth(true);
            }
        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    return (
        <>
            <div className='loginModalContainer'>
                {newAccountMessage && 
                    <h3 className='succesfullyCreatedAccountMessage'>
                        Cuenta creada exitósamente. <br />
                        ¡Inicia sesión para empezar a compartir posts!
                    </h3>
                }
                <div className='loginModal'>
                    <h1 className='loginModalTitle'>Sign in to arda</h1>
                    <form className='loginForm' onSubmit={handleSubmit}>
                        <label htmlFor="username"></label>
                        <input
                            className='loginFormField'
                            type="text"
                            id="username"
                            name="username"
                            placeholder='Username'
                            value={username}
                            onChange={(e) => setUsername(e.target.value)} // Actualizar estado de username
                            required
                        />
                        <label htmlFor="password"></label>
                        <input
                            className='loginFormField'
                            type="password"
                            id="password"
                            name="password"
                            placeholder='Password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)} // Actualizar estado de password
                            required
                        />
                        <button className='loginModalButtons loginModalSignInButton' type="submit">Sign in</button>
                        <button className='loginModalButtons loginModalForgotPasswordButton' type="button">Forgot password?</button>
                        {!newAccountMessage && <p className='loginFormPElem'>Don't you have an account? <Link to="/create-account">Create one</Link></p>}
                    </form>
                </div>
            </div>
        </>
    );
};

export default Login;

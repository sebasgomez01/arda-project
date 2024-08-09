import { useState } from 'react';
import axios from 'axios';
import '../assets/CreateAccount.css'
import { useNavigate, Link } from 'react-router-dom'; // para redigirigir a otra página

const CreateAccount = () => {
    const [name, setName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const userData = {
            name: name,
            username: username,
        };

        const userDataCredentials = {
            name: name,
            username: username,
            password: password, // falta encriptar la contraseña antes de mandarla
            role: "user"
        };

        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/users`, userData);
            axios.post(`${import.meta.env.VITE_API_URL}/api/users/credentials`, userDataCredentials);
            console.log('User created successfully:', response.data);
            setName('');
            setUsername('');
            setPassword('');
        } catch (error) {
            console.error('Error creating post:', error);
        }

        
        navigate( "/login", { state: { fromCreateAccount: true } });
    }

    return (
        <>
             <div className='createAccountModalContainer'>
                <div className='createAccountModal'>
                    <h2 className='loginModalTitle'>Create your account</h2>
                    <form action="/login" method="post" className='loginForm' onSubmit={handleSubmit}>
                        <label for="name"></label>
                        <input 
                            className='loginFormField'
                            type="text" 
                            id="name" 
                            name="name" 
                            placeholder='Name' 
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required>
                        </input>
                        <label htmlFor="username"></label>
                        <input 
                            className='loginFormField' 
                            type="text" 
                            id="username" 
                            name="username" 
                            placeholder='Username' 
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required>
                        </input>
                        <label for="password"></label>
                        <input 
                            className='loginFormField' 
                            type="password" 
                            id="password" 
                            name="password" 
                            placeholder='Password' 
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required>
                        </input>
                        <button className='loginModalButtons loginModalSignInButton' type="submit">Create account</button>
                        <p className='loginFormPElem'>Already have an account? <Link to="/login" >Login</Link> </p>
                    </form>
                    
                </div>
            </div>
        </>
    );
};

export default CreateAccount;
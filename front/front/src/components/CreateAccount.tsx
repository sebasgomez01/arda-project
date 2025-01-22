import { useState } from 'react';
import apiClient from '../axiosConfig.ts';
import '../assets/CreateAccount.css'
import { useNavigate, Link } from 'react-router-dom'; // para redigirigir a otra página

const CreateAccount = () => {
    const [name, setName] = useState<string>('');
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        //event.preventDefault();
        event.preventDefault();

        const userDataCredentials = {
            name: name,
            username: username,
            password: password, // falta encriptar la contraseña antes de mandarla
            role: "user"
        };

        try {
            
            const response = await apiClient.post("/users/credentials", userDataCredentials);
            console.log('User created successfully:', response);
            //setName('');
            //setUsername('');
            //setPassword('');
            //navigate( "/login", { state: { fromCreateAccount: true } });
        } catch (error) {
            console.error('Error creating post:', error);
        }       
    }

    return (
        <>
             <div className='createAccountModalContainer'>
                <div className='createAccountModal'>
                    <h2 className='loginModalTitle'>holacaradebola</h2>
                    <form className='loginForm' onSubmit={handleSubmit}>
                        <label htmlFor="name"></label>
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
                        <label htmlFor="password"></label>
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
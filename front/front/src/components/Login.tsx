import '../assets/Login.css'

const Login = () => {
    return (
        <>
            <div className='loginModalContainer'>
                <div className='loginModal'>
                    <h1 className='loginModalTitle'>Sign in to arda</h1>
                    <form action="/login" method="post" className='loginForm'>
                        <label for="username"></label>
                        <input className='loginFormField' type="text" id="username" name="username" placeholder='Username' required></input>
                        <label for="password"></label>
                        <input className='loginFormField' type="password" id="password" name="password" placeholder='Password' required></input>
                        <button className='loginModalButtons loginModalSignInButton' type="submit">Sign in</button>
                        <button className='loginModalButtons loginModalForgotPasswordButton' >Forgot password?</button>
                        <p className='loginFormPElem'>Don't you hace an account? <a>Create one</a> </p>
                    </form>
                    
                </div>
            </div>
        </>
    );
};

export default Login;
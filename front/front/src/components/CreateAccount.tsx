import '../assets/CreateAccount.css'

const CreateAccount = () => {
    return (
        <>
             <div className='createAccountModalContainer'>
                <div className='createAccountModal'>
                    <h2 className='loginModalTitle'>Create your account</h2>
                    <form action="/login" method="post" className='loginForm'>
                        <label for="name"></label>
                        <input className='loginFormField' type="text" id="name" name="name" placeholder='Name' required></input>
                        <label for="username"></label>
                        <input className='loginFormField' type="text" id="username" name="username" placeholder='Username' required></input>
                        <label for="password"></label>
                        <input className='loginFormField' type="password" id="password" name="password" placeholder='Password' required></input>
                        <button className='loginModalButtons loginModalSignInButton' type="submit">Create account</button>
                        <p className='loginFormPElem'>Already have an account? <a>Login</a> </p>
                    </form>
                    
                </div>
            </div>
        </>
    );
};

export default CreateAccount;
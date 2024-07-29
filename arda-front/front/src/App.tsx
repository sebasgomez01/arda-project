import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import './App.css'
import Home from './components/Home';
import Login from './components/Login';
import CreateAccount from './components/CreateAccount';

function App() {
  

  return (
    <>      
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/create-account" element={<CreateAccount />} />
        </Routes>
      </BrowserRouter> 
    </>
  )
}

export default App;
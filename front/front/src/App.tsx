import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import './App.css'
import Home from './components/Home';
import Login from './components/Login';
import CreateAccount from './components/CreateAccount';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

function App() {
  

  return (
    <>      
      <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/create-account" element={<CreateAccount />} />
        </Routes>
      </BrowserRouter> 
      
      </QueryClientProvider>
    </>
  )
}

export default App;
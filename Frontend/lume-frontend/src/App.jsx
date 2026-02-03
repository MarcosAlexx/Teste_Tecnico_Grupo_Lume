import { Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom'
import { isAuthenticated, logout } from './api'
import Login from './pages/Login'
import Clientes from './pages/Clientes'
import './App.css'

function PrivateRoute({ children }) {
  return isAuthenticated() ? children : <Navigate to="/login" />
}

function Layout({ children }) {
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/login')
  }

  return (
    <>
      <header className="app-header">
        <h2>Lume</h2>
        <nav>
          <Link to="/clientes">Clientes</Link>
          <a href="http://localhost:8080/swagger-ui/index.html" target="_blank" rel="noreferrer">
            Swagger
          </a>
          <a href="http://localhost:8080/h2-console" target="_blank" rel="noreferrer">
            H2 Console
          </a>
          <button className="logout-btn" onClick={handleLogout}>Sair</button>
        </nav>
      </header>
      {children}
    </>
  )
}

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/clientes"
        element={
          <PrivateRoute>
            <Layout><Clientes /></Layout>
          </PrivateRoute>
        }
      />
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  )
}

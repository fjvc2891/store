import { BrowserRouter, Routes, Route, Link, Navigate } from "react-router-dom";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";
import Orders from "./pages/Orders";
import type { JSX } from "react/jsx-runtime";
import "./App.css"; // 👈 importa los estilos

function Protected({ children }: { children: JSX.Element }) {
  return (
    <AuthContext.Consumer>
      {({ token }) => (token ? children : <Navigate to="/login" replace />)}
    </AuthContext.Consumer>
  );
}

function Navbar() {
  return (
    <nav className="navbar">
      <Link to="/">Productos</Link>
      <Link to="/orders">Órdenes</Link>
      <Link to="/login" style={{ marginLeft: "auto" }}>Login</Link>
      <Link to="/register">Registro</Link>
    </nav>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <div className="container">
          <Routes>
            <Route path="/" element={<Protected><Products /></Protected>} />
            <Route path="/orders" element={<Protected><Orders /></Protected>} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

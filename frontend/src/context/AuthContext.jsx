import { createContext, useContext, useState } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [rol, setRol] = useState(localStorage.getItem('rol'));

  function login(nuevoToken, nuevoRol) {
    localStorage.setItem('token', nuevoToken);
    localStorage.setItem('rol', nuevoRol);
    setToken(nuevoToken);
    setRol(nuevoRol);
  }

  function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('rol');
    setToken(null);
    setRol(null);
  }

  const value = {
    token,
    rol,
    estaAutenticado: !!token,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
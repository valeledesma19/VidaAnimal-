import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function RutaProtegida({ rolRequerido, children }) {
  const { estaAutenticado, rol } = useAuth();

  if (!estaAutenticado) {
    return <Navigate to="/login" replace />;
  }

  if (rolRequerido && rol !== rolRequerido) {
    return <Navigate to="/login" replace />;
  }

  return children;
}
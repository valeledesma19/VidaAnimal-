import { useAuth } from '../context/AuthContext';

export default function PanelEmpleado() {
  const { logout } = useAuth();

  return (
    <div>
      <h1>Panel interno - Empleado</h1>
      <button onClick={logout}>Cerrar sesión</button>
    </div>
  );
}
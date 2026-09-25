import { useAuth } from '../context/AuthContext';

export default function PanelCliente() {
  const { logout } = useAuth();

  return (
    <div>
      <h1>Portal del cliente</h1>
      <button onClick={logout}>Cerrar sesión</button>
    </div>
  );
}
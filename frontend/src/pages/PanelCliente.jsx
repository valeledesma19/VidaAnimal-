import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import MisMascotas from './MisMascotas';
import NuevaMascota from './NuevaMascota';
import FichaMascota from './FichaMascota';
import MisTurnos from './MisTurnos';
import ReservarTurno from './ReservarTurno';

export default function PanelCliente() {
  const { logout } = useAuth();

  return (
    <div className="panel-page">
      <svg className="panel-paw" viewBox="0 0 200 200" aria-hidden="true">
        <circle cx="100" cy="130" r="38" />
        <circle cx="55" cy="75" r="20" />
        <circle cx="100" cy="55" r="22" />
        <circle cx="145" cy="75" r="20" />
      </svg>

      <header>
        <h1>Portal del cliente</h1>
        <nav>
          <Link to="/cliente/mascotas">Mis mascotas</Link>
          <Link to="/cliente/turnos">Mis turnos</Link>
        </nav>
        <button onClick={logout}>Cerrar sesión</button>
      </header>

      <div className="page-content">
        <Routes>
          <Route index element={<Navigate to="mascotas" replace />} />
          <Route path="mascotas" element={<MisMascotas />} />
          <Route path="mascotas/nueva" element={<NuevaMascota />} />
          <Route path="mascotas/:id" element={<FichaMascota />} />
          <Route path="turnos" element={<MisTurnos />} />
          <Route path="turnos/nuevo" element={<ReservarTurno />} />
        </Routes>
      </div>
    </div>
  );
}
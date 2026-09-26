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
    <div>
      <header>
        <h1>Portal del cliente</h1>
                <nav>
                  <Link to="/cliente/mascotas">Mis mascotas</Link> | <Link to="/cliente/turnos">Mis turnos</Link>
                </nav>
        <button onClick={logout}>Cerrar sesión</button>
      </header>

      <Routes>
        <Route index element={<Navigate to="mascotas" replace />} />
        <Route path="mascotas" element={<MisMascotas />} />
        <Route path="mascotas/nueva" element={<NuevaMascota />} />
        <Route path="mascotas/:id" element={<FichaMascota />} />
        <Route path="turnos" element={<MisTurnos />} />
        <Route path="turnos/nuevo" element={<ReservarTurno />} />
      </Routes>
    </div>
  );
}
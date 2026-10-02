import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Agenda from './Agenda';
import CargarTurno from './CargarTurno';
import EditarTurno from './EditarTurno';
import RegistrarVacuna from './RegistrarVacuna';
import RegistrarPeso from './RegistrarPeso';
import AlertasVacunas from './AlertasVacunas';

export default function PanelEmpleado() {
  const { logout } = useAuth();

  return (
    <div>
      <header>
        <h1>Panel interno - Empleado</h1>
        <nav>
          <Link to="/empleado/agenda">Agenda</Link> |{' '}
          <Link to="/empleado/vacunas">Registrar vacuna</Link> |{' '}
          <Link to="/empleado/peso">Registrar peso</Link> |{' '}
          <Link to="/empleado/alertas">Alertas de vacunas</Link>
        </nav>
        <button onClick={logout}>Cerrar sesión</button>
      </header>

      <Routes>
        <Route index element={<Navigate to="agenda" replace />} />
        <Route path="agenda" element={<Agenda />} />
        <Route path="agenda/nuevo" element={<CargarTurno />} />
        <Route path="agenda/editar/:id" element={<EditarTurno />} />
        <Route path="vacunas" element={<RegistrarVacuna />} />
        <Route path="peso" element={<RegistrarPeso />} />
        <Route path="alertas" element={<AlertasVacunas />} />
      </Routes>
    </div>
  );
}
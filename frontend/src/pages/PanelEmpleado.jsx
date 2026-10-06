import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Agenda from './Agenda';
import CargarTurno from './CargarTurno';
import EditarTurno from './EditarTurno';
import RegistrarVacuna from './RegistrarVacuna';
import RegistrarPeso from './RegistrarPeso';
import AlertasVacunas from './AlertasVacunas';
import Reportes from './Reportes';

export default function PanelEmpleado() {
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
        <h1>Panel interno - Empleado</h1>
        <nav>
          <Link to="/empleado/agenda">Agenda</Link>
          <Link to="/empleado/vacunas">Registrar vacuna</Link>
          <Link to="/empleado/peso">Registrar peso</Link>
          <Link to="/empleado/alertas">Alertas de vacunas</Link>
          <Link to="/empleado/reportes">Reportes</Link>
        </nav>
        <button onClick={logout}>Cerrar sesión</button>
      </header>

      <div className="page-content">
        <Routes>
          <Route index element={<Navigate to="agenda" replace />} />
          <Route path="agenda" element={<Agenda />} />
          <Route path="agenda/nuevo" element={<CargarTurno />} />
          <Route path="agenda/editar/:id" element={<EditarTurno />} />
          <Route path="vacunas" element={<RegistrarVacuna />} />
          <Route path="peso" element={<RegistrarPeso />} />
          <Route path="alertas" element={<AlertasVacunas />} />
          <Route path="reportes" element={<Reportes />} />
        </Routes>
      </div>
    </div>
  );
}
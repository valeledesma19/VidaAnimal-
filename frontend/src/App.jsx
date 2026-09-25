import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Registro from './pages/Registro';
import PanelCliente from './pages/PanelCliente';
import PanelEmpleado from './pages/PanelEmpleado';
import RutaProtegida from './components/RutaProtegida';

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/registro" element={<Registro />} />

      <Route
        path="/cliente/*"
        element={
          <RutaProtegida rolRequerido="CLIENTE">
            <PanelCliente />
          </RutaProtegida>
        }
      />

      <Route
        path="/empleado/*"
        element={
          <RutaProtegida rolRequerido="EMPLEADO">
            <PanelEmpleado />
          </RutaProtegida>
        }
      />

      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}
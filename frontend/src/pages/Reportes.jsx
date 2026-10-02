import { useEffect, useState } from 'react';
import {
  obtenerTurnosPorMes,
  obtenerVacunasMasAplicadas,
  obtenerMascotasNuevasPorMes,
} from '../services/reporteService';

function TablaReporte({ titulo, columnaEtiqueta, filas }) {
  return (
    <div>
      <h3>{titulo}</h3>
      {filas.length === 0 ? (
        <p>Sin datos todavía.</p>
      ) : (
        <table border="1" cellPadding="6">
          <thead>
            <tr>
              <th>{columnaEtiqueta}</th>
              <th>Cantidad</th>
            </tr>
          </thead>
          <tbody>
            {filas.map((f) => (
              <tr key={f.etiqueta}>
                <td>{f.etiqueta}</td>
                <td>{f.cantidad}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default function Reportes() {
  const [turnosPorMes, setTurnosPorMes] = useState([]);
  const [vacunasMasAplicadas, setVacunasMasAplicadas] = useState([]);
  const [mascotasNuevasPorMes, setMascotasNuevasPorMes] = useState([]);
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    cargarTodo();
  }, []);

  async function cargarTodo() {
    setCargando(true);
    setError('');
    try {
      const [turnos, vacunas, mascotas] = await Promise.all([
        obtenerTurnosPorMes(),
        obtenerVacunasMasAplicadas(),
        obtenerMascotasNuevasPorMes(),
      ]);
      setTurnosPorMes(turnos);
      setVacunasMasAplicadas(vacunas);
      setMascotasNuevasPorMes(mascotas);
    } catch (err) {
      setError('No se pudieron cargar los reportes');
    } finally {
      setCargando(false);
    }
  }

  if (cargando) return <p>Cargando reportes...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Reportes</h2>
      <TablaReporte titulo="Turnos por mes" columnaEtiqueta="Mes" filas={turnosPorMes} />
      <TablaReporte titulo="Vacunas más aplicadas" columnaEtiqueta="Vacuna" filas={vacunasMasAplicadas} />
      <TablaReporte titulo="Mascotas nuevas por mes" columnaEtiqueta="Mes" filas={mascotasNuevasPorMes} />
    </div>
  );
}
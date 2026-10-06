import { useEffect, useState } from 'react';
import { listarAlertas } from '../services/vacunaService';

export default function AlertasVacunas() {
  const [alertas, setAlertas] = useState([]);
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    listarAlertas()
      .then(setAlertas)
      .catch(() => setError('No se pudieron cargar las alertas'))
      .finally(() => setCargando(false));
  }, []);

  if (cargando) return <p>Cargando...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Vacunas próximas a vencer</h2>
      {alertas.length === 0 && <p>No hay vacunas por vencer en los próximos 30 días.</p>}
      <ul>
        {alertas.map((v) => (
          <li key={v.id}>
            {v.mascotaNombre} — {v.nombre} — vence {v.fechaVencimiento}
          </li>
        ))}
      </ul>
    </div>
  );
}
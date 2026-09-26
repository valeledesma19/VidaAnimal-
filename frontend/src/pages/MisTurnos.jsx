import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { listarMisTurnos, cancelarTurno } from '../services/turnoService';

export default function MisTurnos() {
  const [turnos, setTurnos] = useState([]);
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    cargar();
  }, []);

  async function cargar() {
    setCargando(true);
    try {
      const data = await listarMisTurnos();
      setTurnos(data);
    } catch (err) {
      setError('No se pudieron cargar tus turnos');
    } finally {
      setCargando(false);
    }
  }

  async function handleCancelar(id) {
    setError('');
    try {
      await cancelarTurno(id);
      cargar();
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo cancelar el turno';
      setError(mensaje);
    }
  }

  if (cargando) return <p>Cargando...</p>;

  return (
    <div>
      <h2>Mis turnos</h2>
      <Link to="nuevo">+ Reservar turno</Link>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {turnos.length === 0 && <p>Todavía no reservaste ningún turno.</p>}

      <ul>
        {turnos.map((t) => (
          <li key={t.id}>
            {t.fecha} {t.hora} — {t.mascotaNombre} — {t.estado}
            {t.estado === 'CONFIRMADO' && (
              <button onClick={() => handleCancelar(t.id)}>Cancelar</button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
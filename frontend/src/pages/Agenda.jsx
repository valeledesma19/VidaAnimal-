import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { listarAgenda, cancelarTurno } from '../services/turnoService';

function hoy() {
  return new Date().toISOString().split('T')[0];
}

export default function Agenda() {
  const [fecha, setFecha] = useState(hoy());
  const [turnos, setTurnos] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    cargar();
  }, [fecha]);

  async function cargar() {
    setError('');
    try {
      const data = await listarAgenda(fecha);
      setTurnos(data);
    } catch (err) {
      setError('No se pudo cargar la agenda');
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

  return (
    <div>
      <h2>Agenda</h2>
      <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} />
      <Link to="nuevo">+ Cargar turno</Link>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {turnos.length === 0 && <p>Sin turnos para este día.</p>}

      <ul>
        {turnos.map((t) => (
          <li key={t.id}>
            {t.hora} — {t.mascotaNombre} — {t.motivo || 'sin motivo'}
            <Link to={`editar/${t.id}`}> Editar</Link>
            <button onClick={() => handleCancelar(t.id)}>Cancelar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
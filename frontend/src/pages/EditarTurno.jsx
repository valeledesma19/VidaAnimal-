import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { obtenerTurno, listarDisponibilidad, editarTurno } from '../services/turnoService';

export default function EditarTurno() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [turnoOriginal, setTurnoOriginal] = useState(null);
  const [fecha, setFecha] = useState('');
  const [hora, setHora] = useState('');
  const [motivo, setMotivo] = useState('');
  const [horasLibres, setHorasLibres] = useState([]);
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  useEffect(() => {
    obtenerTurno(id)
      .then((t) => {
        setTurnoOriginal(t);
        setFecha(t.fecha);
        setHora(t.hora);
        setMotivo(t.motivo || '');
      })
      .catch(() => setError('No se pudo cargar el turno'));
  }, [id]);

  async function handleFechaChange(nuevaFecha) {
    setFecha(nuevaFecha);
    try {
      const libres = await listarDisponibilidad(nuevaFecha);
      setHorasLibres(nuevaFecha === turnoOriginal.fecha ? [...libres, turnoOriginal.hora] : libres);
    } catch {
      setHorasLibres([]);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setCargando(true);

    try {
      await editarTurno(id, { mascotaId: turnoOriginal.mascotaId, fecha, hora, motivo });
      navigate('..');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo editar el turno';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  if (error && !turnoOriginal) return <p style={{ color: 'red' }}>{error}</p>;
  if (!turnoOriginal) return <p>Cargando...</p>;

  return (
    <div>
      <h2>Editar turno — {turnoOriginal.mascotaNombre}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Fecha</label>
          <input type="date" value={fecha} onChange={(e) => handleFechaChange(e.target.value)} required />
        </div>

        <div>
          <label>Horario</label>
          <select value={hora} onChange={(e) => setHora(e.target.value)} required>
            <option value={turnoOriginal.hora}>{turnoOriginal.hora} (actual)</option>
            {horasLibres.filter((h) => h !== turnoOriginal.hora).map((h) => (
              <option key={h} value={h}>
                {h}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Motivo</label>
          <input type="text" value={motivo} onChange={(e) => setMotivo(e.target.value)} />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <button type="submit" disabled={cargando}>
          {cargando ? 'Guardando...' : 'Guardar cambios'}
        </button>
      </form>
    </div>
  );
}
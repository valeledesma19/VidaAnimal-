import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { listarMisMascotas } from '../services/mascotaService';
import { listarDisponibilidad, crearTurno } from '../services/turnoService';

export default function ReservarTurno() {
  const [mascotas, setMascotas] = useState([]);
  const [mascotaId, setMascotaId] = useState('');
  const [fecha, setFecha] = useState('');
  const [horasLibres, setHorasLibres] = useState([]);
  const [hora, setHora] = useState('');
  const [motivo, setMotivo] = useState('');
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    listarMisMascotas().then(setMascotas).catch(() => setError('No se pudieron cargar tus mascotas'));
  }, []);

  useEffect(() => {
    if (!fecha) {
      setHorasLibres([]);
      return;
    }
    listarDisponibilidad(fecha)
      .then(setHorasLibres)
      .catch(() => setError('No se pudo consultar la disponibilidad'));
    setHora('');
  }, [fecha]);

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setCargando(true);

    try {
      await crearTurno({ mascotaId: Number(mascotaId), fecha, hora, motivo });
      navigate('..');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo reservar el turno';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h2>Reservar turno</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Mascota</label>
          <select value={mascotaId} onChange={(e) => setMascotaId(e.target.value)} required>
            <option value="">Elegí una mascota</option>
            {mascotas.map((m) => (
              <option key={m.id} value={m.id}>
                {m.nombre}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Fecha</label>
          <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} required />
        </div>

        {fecha && (
          <div>
            <label>Horario</label>
            {horasLibres.length === 0 && <p>No hay horarios libres ese día.</p>}
            <select value={hora} onChange={(e) => setHora(e.target.value)} required>
              <option value="">Elegí un horario</option>
              {horasLibres.map((h) => (
                <option key={h} value={h}>
                  {h}
                </option>
              ))}
            </select>
          </div>
        )}

        <div>
          <label>Motivo</label>
          <input type="text" value={motivo} onChange={(e) => setMotivo(e.target.value)} />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <button type="submit" disabled={cargando || !hora}>
          {cargando ? 'Reservando...' : 'Confirmar turno'}
        </button>
      </form>
    </div>
  );
}
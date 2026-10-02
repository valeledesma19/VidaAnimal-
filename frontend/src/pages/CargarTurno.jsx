import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { buscarMascotas } from '../services/mascotaService';
import { listarDisponibilidad, crearTurno } from '../services/turnoService';

export default function CargarTurno() {
  const [textoBusqueda, setTextoBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [mascotaElegida, setMascotaElegida] = useState(null);

  const [fecha, setFecha] = useState('');
  const [horasLibres, setHorasLibres] = useState([]);
  const [hora, setHora] = useState('');
  const [motivo, setMotivo] = useState('');
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    if (textoBusqueda.trim().length < 2) {
      setResultados([]);
      return;
    }
    const timeout = setTimeout(() => {
      buscarMascotas(textoBusqueda).then(setResultados).catch(() => {});
    }, 300);
    return () => clearTimeout(timeout);
  }, [textoBusqueda]);

  useEffect(() => {
    if (!fecha) {
      setHorasLibres([]);
      return;
    }
    listarDisponibilidad(fecha).then(setHorasLibres).catch(() => {});
    setHora('');
  }, [fecha]);

  function elegirMascota(m) {
    setMascotaElegida(m);
    setResultados([]);
    setTextoBusqueda(`${m.nombre} (dueño: ${m.clienteNombreCompleto})`);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setCargando(true);

    try {
      await crearTurno({ mascotaId: mascotaElegida.id, fecha, hora, motivo });
      navigate('..');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo cargar el turno';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h2>Cargar turno</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Buscar mascota por nombre</label>
          <input
            type="text"
            value={textoBusqueda}
            onChange={(e) => {
              setTextoBusqueda(e.target.value);
              setMascotaElegida(null);
            }}
            placeholder="Ej: Firulais"
          />
          {resultados.length > 0 && (
            <ul>
              {resultados.map((m) => (
                <li key={m.id} onClick={() => elegirMascota(m)} style={{ cursor: 'pointer' }}>
                  {m.nombre} ({m.raza}) — dueño: {m.clienteNombreCompleto}
                </li>
              ))}
            </ul>
          )}
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

        <button type="submit" disabled={cargando || !mascotaElegida || !hora}>
          {cargando ? 'Cargando...' : 'Confirmar turno'}
        </button>
      </form>
    </div>
  );
}
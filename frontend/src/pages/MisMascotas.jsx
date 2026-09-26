import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { listarMisMascotas } from '../services/mascotaService';

export default function MisMascotas() {
  const [mascotas, setMascotas] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    cargar();
  }, []);

  async function cargar() {
    setCargando(true);
    setError('');
    try {
      const data = await listarMisMascotas();
      setMascotas(data);
    } catch (err) {
      setError('No se pudieron cargar tus mascotas');
    } finally {
      setCargando(false);
    }
  }

  if (cargando) return <p>Cargando...</p>;

  return (
    <div>
      <h2>Mis mascotas</h2>
      <Link to="nueva">+ Agregar mascota</Link>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {mascotas.length === 0 && !error && <p>Todavía no tenés mascotas cargadas.</p>}

      <ul>
        {mascotas.map((m) => (
          <li key={m.id}>
            <Link to={`${m.id}`}>
              {m.nombre} — {m.raza} ({m.tamaño})
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
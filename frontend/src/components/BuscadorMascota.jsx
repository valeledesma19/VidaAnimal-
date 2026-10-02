import { useEffect, useState } from 'react';
import { buscarMascotas } from '../services/mascotaService';

export default function BuscadorMascota({ onSeleccionar }) {
  const [texto, setTexto] = useState('');
  const [resultados, setResultados] = useState([]);
  const [elegida, setElegida] = useState(null);

  useEffect(() => {
    if (texto.trim().length < 2) {
      setResultados([]);
      return;
    }
    const timeout = setTimeout(() => {
      buscarMascotas(texto).then(setResultados).catch(() => {});
    }, 300);
    return () => clearTimeout(timeout);
  }, [texto]);

  function elegir(m) {
    setElegida(m);
    setResultados([]);
    setTexto(`${m.nombre} (dueño: ${m.clienteNombreCompleto})`);
    onSeleccionar(m);
  }

  function limpiar(e) {
    setTexto(e.target.value);
    setElegida(null);
    onSeleccionar(null);
  }

  return (
    <div>
      <label>Buscar mascota por nombre</label>
      <input type="text" value={texto} onChange={limpiar} placeholder="Ej: Firulais" />
      {resultados.length > 0 && (
        <ul>
          {resultados.map((m) => (
            <li key={m.id} onClick={() => elegir(m)} style={{ cursor: 'pointer' }}>
              {m.nombre} ({m.raza}) — dueño: {m.clienteNombreCompleto}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
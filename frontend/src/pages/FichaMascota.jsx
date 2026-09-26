import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { obtenerFicha } from '../services/mascotaService';

export default function FichaMascota() {
  const { id } = useParams();
  const [ficha, setFicha] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    cargar();
  }, [id]);

  async function cargar() {
    setError('');
    try {
      const data = await obtenerFicha(id);
      setFicha(data);
    } catch (err) {
      setError('No se pudo cargar la ficha de la mascota');
    }
  }

  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!ficha) return <p>Cargando...</p>;

  return (
    <div>
      <h2>{ficha.mascota.nombre}</h2>
      <p>Raza: {ficha.mascota.raza}</p>
      <p>Tamaño: {ficha.mascota.tamaño}</p>
      <p>Fecha de nacimiento: {ficha.mascota.fechaNacimiento}</p>

      <h3>Vacunas</h3>
      {ficha.vacunas.length === 0 && <p>Sin vacunas registradas.</p>}
      <ul>
        {ficha.vacunas.map((v) => (
          <li key={v.id}>
            {v.nombre} — aplicada {v.fechaAplicacion} — vence {v.fechaVencimiento}
          </li>
        ))}
      </ul>

      <h3>Peso</h3>
      {ficha.pesos.length === 0 && <p>Sin registros de peso.</p>}
      <ul>
        {ficha.pesos.map((p) => (
          <li key={p.id}>
            {p.fecha}: {p.valorKg} kg
          </li>
        ))}
      </ul>
    </div>
  );
}
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { crearMascota } from '../services/mascotaService';

export default function NuevaMascota() {
  const [form, setForm] = useState({
    nombre: '',
    raza: '',
    tamaño: 'MEDIANO',
    fechaNacimiento: '',
  });
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  const navigate = useNavigate();

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setCargando(true);

    try {
      await crearMascota(form);
      navigate('..');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo registrar la mascota';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h2>Agregar mascota</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nombre</label>
          <input type="text" name="nombre" value={form.nombre} onChange={handleChange} required />
        </div>
        <div>
          <label>Raza</label>
          <input type="text" name="raza" value={form.raza} onChange={handleChange} required />
        </div>
        <div>
          <label>Tamaño</label>
          <select name="tamaño" value={form.tamaño} onChange={handleChange}>
            <option value="PEQUEÑO">Pequeño</option>
            <option value="MEDIANO">Mediano</option>
            <option value="GRANDE">Grande</option>
          </select>
        </div>
        <div>
          <label>Fecha de nacimiento</label>
          <input
            type="date"
            name="fechaNacimiento"
            value={form.fechaNacimiento}
            onChange={handleChange}
            required
          />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <button type="submit" disabled={cargando}>
          {cargando ? 'Guardando...' : 'Guardar'}
        </button>
      </form>
    </div>
  );
}
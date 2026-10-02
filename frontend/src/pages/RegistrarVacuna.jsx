import { useState } from 'react';
import BuscadorMascota from '../components/BuscadorMascota';
import { registrarVacuna } from '../services/vacunaService';

export default function RegistrarVacuna() {
  const [mascota, setMascota] = useState(null);
  const [nombre, setNombre] = useState('');
  const [fechaAplicacion, setFechaAplicacion] = useState('');
  const [duracionMeses, setDuracionMeses] = useState('');
  const [error, setError] = useState('');
  const [exito, setExito] = useState('');
  const [cargando, setCargando] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setExito('');
    setCargando(true);

    try {
      await registrarVacuna(mascota.id, {
        nombre,
        fechaAplicacion,
        duracionMeses: Number(duracionMeses),
      });
      setExito(`Vacuna registrada para ${mascota.nombre}`);
      setNombre('');
      setFechaAplicacion('');
      setDuracionMeses('');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo registrar la vacuna';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h2>Registrar vacuna</h2>
      <form onSubmit={handleSubmit}>
        <BuscadorMascota onSeleccionar={setMascota} />

        <div>
          <label>Nombre de la vacuna</label>
          <input type="text" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
        </div>

        <div>
          <label>Fecha de aplicación</label>
          <input
            type="date"
            value={fechaAplicacion}
            onChange={(e) => setFechaAplicacion(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Duración (meses)</label>
          <input
            type="number"
            min="1"
            value={duracionMeses}
            onChange={(e) => setDuracionMeses(e.target.value)}
            required
          />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}
        {exito && <p style={{ color: 'green' }}>{exito}</p>}

        <button type="submit" disabled={cargando || !mascota}>
          {cargando ? 'Guardando...' : 'Registrar vacuna'}
        </button>
      </form>
    </div>
  );
}
import { useState } from 'react';
import BuscadorMascota from '../components/BuscadorMascota';
import { registrarPeso } from '../services/pesoService';

function hoy() {
  return new Date().toISOString().split('T')[0];
}

export default function RegistrarPeso() {
  const [mascota, setMascota] = useState(null);
  const [valorKg, setValorKg] = useState('');
  const [fecha, setFecha] = useState(hoy());
  const [error, setError] = useState('');
  const [exito, setExito] = useState('');
  const [cargando, setCargando] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setExito('');
    setCargando(true);

    try {
      await registrarPeso(mascota.id, { valorKg: Number(valorKg), fecha });
      setExito(`Peso registrado para ${mascota.nombre}`);
      setValorKg('');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo registrar el peso';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h2>Registrar peso</h2>
      <form onSubmit={handleSubmit}>
        <BuscadorMascota onSeleccionar={setMascota} />

        <div>
          <label>Peso (kg)</label>
          <input
            type="number"
            step="0.01"
            min="0.01"
            value={valorKg}
            onChange={(e) => setValorKg(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Fecha</label>
          <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} required />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}
        {exito && <p style={{ color: 'green' }}>{exito}</p>}

        <button type="submit" disabled={cargando || !mascota}>
          {cargando ? 'Guardando...' : 'Registrar peso'}
        </button>
      </form>
    </div>
  );
}
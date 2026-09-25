import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { registro as registroService } from '../services/authService';

export default function Registro() {
  const [form, setForm] = useState({
    email: '',
    password: '',
    nombre: '',
    apellido: '',
    telefono: '',
  });
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setCargando(true);

    try {
      const data = await registroService(form);
      login(data.token, data.rol);
      navigate('/cliente');
    } catch (err) {
      const mensaje = err.response?.data?.error || 'No se pudo completar el registro';
      setError(mensaje);
    } finally {
      setCargando(false);
    }
  }

  return (
    <div>
      <h1>VidaAnimal - Crear cuenta</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email</label>
          <input type="email" name="email" value={form.email} onChange={handleChange} required />
        </div>
        <div>
          <label>Contraseña</label>
          <input type="password" name="password" value={form.password} onChange={handleChange} required minLength={8} />
        </div>
        <div>
          <label>Nombre</label>
          <input type="text" name="nombre" value={form.nombre} onChange={handleChange} required />
        </div>
        <div>
          <label>Apellido</label>
          <input type="text" name="apellido" value={form.apellido} onChange={handleChange} required />
        </div>
        <div>
          <label>Teléfono</label>
          <input type="text" name="telefono" value={form.telefono} onChange={handleChange} required />
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <button type="submit" disabled={cargando}>
          {cargando ? 'Creando cuenta...' : 'Crear cuenta'}
        </button>
      </form>

      <p>
        ¿Ya tenés cuenta? <Link to="/login">Iniciar sesión</Link>
      </p>
    </div>
  );
}
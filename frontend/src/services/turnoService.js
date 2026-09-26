import api from './api';

export async function listarDisponibilidad(fecha) {
  const { data } = await api.get('/turnos/disponibilidad', { params: { fecha } });
  return data;
}

export async function listarMisTurnos() {
  const { data } = await api.get('/turnos/mios');
  return data;
}

export async function crearTurno(datos) {
  const { data } = await api.post('/turnos', datos);
  return data;
}

export async function cancelarTurno(id) {
  await api.delete(`/turnos/${id}`);
}
import api from './api';

export async function listarMisMascotas() {
  const { data } = await api.get('/mascotas');
  return data;
}

export async function obtenerFicha(id) {
  const { data } = await api.get(`/mascotas/${id}`);
  return data;
}

export async function crearMascota(datos) {
  const { data } = await api.post('/mascotas', datos);
  return data;
}
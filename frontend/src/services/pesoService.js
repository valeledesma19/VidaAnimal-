import api from './api';

export async function registrarPeso(mascotaId, datos) {
  const { data } = await api.post(`/mascotas/${mascotaId}/peso`, datos);
  return data;
}
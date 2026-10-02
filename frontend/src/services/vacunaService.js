import api from './api';

export async function registrarVacuna(mascotaId, datos) {
  const { data } = await api.post(`/mascotas/${mascotaId}/vacunas`, datos);
  return data;
}

export async function listarAlertas() {
  const { data } = await api.get('/vacunas/alertas');
  return data;
}
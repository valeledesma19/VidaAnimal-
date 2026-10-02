import api from './api';

export async function obtenerTurnosPorMes() {
  const { data } = await api.get('/reportes/turnos-por-mes');
  return data;
}

export async function obtenerVacunasMasAplicadas() {
  const { data } = await api.get('/reportes/vacunas-mas-aplicadas');
  return data;
}

export async function obtenerMascotasNuevasPorMes() {
  const { data } = await api.get('/reportes/mascotas-nuevas-por-mes');
  return data;
}
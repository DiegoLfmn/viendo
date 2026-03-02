import api from './api.js'

export default {
  getContenidos(filtros = {}) {
    return api.get('/contenidos', { params: filtros })
  },
  crearContenido(data) {
    return api.post('/contenidos', data)
  },
  editarContenido(id, data) {
    return api.put(`/contenidos/${id}`, data)
  },
  eliminarContenido(id) {
    return api.delete(`/contenidos/${id}`)
  },
  enviarAValidacion(id) {
    return api.put(`/contenidos/${id}/validar`)
  },
  aprobarContenido(id) {
    return api.put(`/contenidos/${id}/aprobar`)
  },
  rechazarContenido(id, motivo) {
    return api.put(`/contenidos/${id}/rechazar`, { motivo })
  },
  getProcesosAdministrativos(filtros = {}) {
    return api.get('/contenidos/procesos', { params: filtros })
  },
  getInformacionGeneral() {
    return api.get('/contenidos/info-general')
  }
}

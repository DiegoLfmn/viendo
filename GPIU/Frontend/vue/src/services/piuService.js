import api from './api.js'

export default {
  getPIUs() {
    return api.get('/pius')
  },
  crearPIU(data) {
    return api.post('/pius', data)
  },
  actualizarPIU(id, data) {
    return api.put(`/pius/${id}`, data)
  },
  eliminarPIU(id) {
    return api.delete(`/pius/${id}`)
  },
  getEstadoPIUs() {
    return api.get('/pius/estado')
  },
  getIncidencias() {
    return api.get('/pius/incidencias')
  },
  crearIncidencia(data) {
    return api.post('/pius/incidencias', data)
  },
  getEstadisticas() {
    return api.get('/pius/estadisticas')
  },
  solicitarDesconexion(piuId, motivo) {
    return api.post(`/pius/${piuId}/desconexion`, { motivo })
  }
}

import api from './api.js'

export default {
  getEventosVigentes() {
    return api.get('/eventos/vigentes')
  },
  getEventos(filtros = {}) {
    return api.get('/eventos', { params: filtros })
  },
  getEventoById(id) {
    return api.get(`/eventos/${id}`)
  }
}

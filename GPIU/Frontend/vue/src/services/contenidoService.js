import api from './api.js'

export default {
  getContenidos(filtros = {}) {
    return api.get('/contenidos', { params: filtros })
  },
<<<<<<< HEAD
  crearContenido(data) {
    return api.post('/contenidos', data)
  },
  editarContenido(id, data) {
    return api.put(`/contenidos/${id}`, data)
=======
  getContenidosPublicados() {
    return api.get('/contenidos/publicados')
  },
  crearContenido(data) {
    // El backend espera: titulo, tipo, texto(cuerpo), categoria, fechaVigenciaInicio, fechaVigenciaFin, autorNombre
    // Mapeamos "cuerpo" -> "texto" y "autor" -> "autorNombre" para el backend
    const payload = {
      titulo: data.titulo,
      tipo: data.tipo,
      texto: data.cuerpo || data.texto || '',
      cuerpo: data.cuerpo || data.texto || '',
      categoria: data.categoria || '',
      fechaVigenciaInicio: data.fechaVigenciaInicio || null,
      fechaVigenciaFin: data.fechaVigenciaFin || null,
      autorNombre: data.autor || data.autorNombre || '',
      autor: data.autor || data.autorNombre || '',
    }
    return api.post('/contenidos', payload)
  },
  editarContenido(id, data) {
    const payload = {
      titulo: data.titulo,
      tipo: data.tipo,
      texto: data.cuerpo || data.texto || '',
      cuerpo: data.cuerpo || data.texto || '',
      categoria: data.categoria || '',
      fechaVigenciaInicio: data.fechaVigenciaInicio || null,
      fechaVigenciaFin: data.fechaVigenciaFin || null,
      autorNombre: data.autor || data.autorNombre || '',
      autor: data.autor || data.autorNombre || '',
      estado: data.estado || 'borrador',
    }
    return api.put(`/contenidos/${id}`, payload)
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD
    return api.get('/contenidos/procesos', { params: filtros })
  },
  getInformacionGeneral() {
    return api.get('/contenidos/info-general')
  }
}
=======
    return api.get('/contenidos/publicados', { params: filtros })
  },
  getInformacionGeneral() {
    return api.get('/contenidos/publicados')
  }
}
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

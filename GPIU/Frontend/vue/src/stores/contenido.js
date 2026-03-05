import { defineStore } from 'pinia'
import contenidoService from '../services/contenidoService.js'

<<<<<<< HEAD
=======
// Normaliza un objeto de contenido del backend al formato que usa el frontend
function normalizar(c) {
  return {
    ...c,
    id: c.id_contenido || c.id,
    estado: c.estado ? c.estado.toLowerCase() : 'borrador',
    cuerpo: c.cuerpo || c.texto || '',
    autor: c.autorNombre || c.autor || '',
    tipo: c.tipo || '',
    categoria: c.categoria || '',
    fechaCreacion: c.fechaCreacion || null,
    fechaVigenciaInicio: c.fechaVigenciaInicio || null,
    fechaVigenciaFin: c.fechaVigenciaFin || null,
  }
}

>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
const mockContenidos = [
  {
    id: 1,
    titulo: 'Historia de la Universidad',
    tipo: 'info-general',
    cuerpo: 'Fundada en 1947, nuestra universidad ha sido pionera en la educación superior de Chile...',
    estado: 'publicado',
    fechaCreacion: '2024-01-15',
    autor: 'Dirección de Comunicaciones',
    categoria: 'institucional'
  },
  {
    id: 2,
    titulo: 'Proceso de Matrícula 2024',
    tipo: 'proceso',
    cuerpo: 'El proceso de matrícula para el año académico 2024 se realizará entre el 15 y 30 de enero...',
    estado: 'publicado',
    fechaCreacion: '2024-01-05',
    autor: 'Dirección de Registro Curricular',
    categoria: 'administrativo',
    fechaVigenciaInicio: '2024-01-15',
    fechaVigenciaFin: '2024-01-30'
  },
  {
    id: 3,
    titulo: 'Normativa de Uso de Laboratorios',
    tipo: 'proceso',
    cuerpo: 'Se informa a la comunidad universitaria las normas actualizadas para el uso de laboratorios...',
    estado: 'borrador',
    fechaCreacion: '2024-02-01',
    autor: 'Dirección de Infraestructura',
    categoria: 'normativa'
  }
]

export const useContenidoStore = defineStore('contenido', {
  state: () => ({
    contenidos: [],
    contenidoActual: null,
    loading: false,
    error: null
  }),

  actions: {
    async cargarContenidos(filtros = {}) {
      this.loading = true
      this.error = null
      try {
        const response = await contenidoService.getContenidos(filtros)
<<<<<<< HEAD
        this.contenidos = response.data
      } catch (_) {
        this.contenidos = mockContenidos
=======
        // Normalizar cada item del backend al formato del frontend
        this.contenidos = response.data.map(normalizar)
      } catch (_) {
        // Solo usar mocks si todavía no hay datos cargados
        if (this.contenidos.length === 0) {
          this.contenidos = mockContenidos
        }
        this.error = 'No se pudo conectar al servidor.'
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } finally {
        this.loading = false
      }
    },

    async crearContenido(data) {
      try {
        const response = await contenidoService.crearContenido(data)
<<<<<<< HEAD
        this.contenidos.push(response.data)
        return response.data
=======
        const nuevo = normalizar(response.data)
        this.contenidos.push(nuevo)
        return nuevo
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } catch (_) {
        const mock = { ...data, id: Date.now(), estado: 'borrador', fechaCreacion: new Date().toISOString().split('T')[0] }
        this.contenidos.push(mock)
        return mock
      }
    },

    async editarContenido(id, data) {
      try {
        const response = await contenidoService.editarContenido(id, data)
<<<<<<< HEAD
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
        return response.data
=======
        const actualizado = normalizar(response.data)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = actualizado
        return actualizado
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = { ...this.contenidos[idx], ...data }
        return this.contenidos[idx]
      }
    },

    async eliminarContenido(id) {
      try {
        await contenidoService.eliminarContenido(id)
      } catch (_) {
<<<<<<< HEAD
        // mock
=======
        // ignorar error de red, igual quitar del estado local
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      }
      this.contenidos = this.contenidos.filter(c => c.id !== id)
    },

    async enviarAValidacion(id) {
      try {
        const response = await contenidoService.enviarAValidacion(id)
<<<<<<< HEAD
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
=======
        const actualizado = normalizar(response.data)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = actualizado
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx].estado = 'en_validacion'
      }
    },

    async aprobarContenido(id) {
      try {
        const response = await contenidoService.aprobarContenido(id)
<<<<<<< HEAD
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
=======
        const actualizado = normalizar(response.data)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = actualizado
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx].estado = 'publicado'
      }
    },

    async rechazarContenido(id, motivo) {
      try {
        const response = await contenidoService.rechazarContenido(id, motivo)
<<<<<<< HEAD
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
=======
        const actualizado = normalizar(response.data)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = actualizado
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) {
          this.contenidos[idx].estado = 'rechazado'
          this.contenidos[idx].motivoRechazo = motivo
        }
      }
    }
  }
<<<<<<< HEAD
})
=======
})
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

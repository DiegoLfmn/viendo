import { defineStore } from 'pinia'
import contenidoService from '../services/contenidoService.js'

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
        this.contenidos = response.data
      } catch (_) {
        this.contenidos = mockContenidos
      } finally {
        this.loading = false
      }
    },

    async crearContenido(data) {
      try {
        const response = await contenidoService.crearContenido(data)
        this.contenidos.push(response.data)
        return response.data
      } catch (_) {
        const mock = { ...data, id: Date.now(), estado: 'borrador', fechaCreacion: new Date().toISOString().split('T')[0] }
        this.contenidos.push(mock)
        return mock
      }
    },

    async editarContenido(id, data) {
      try {
        const response = await contenidoService.editarContenido(id, data)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
        return response.data
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
        // mock
      }
      this.contenidos = this.contenidos.filter(c => c.id !== id)
    },

    async enviarAValidacion(id) {
      try {
        const response = await contenidoService.enviarAValidacion(id)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx].estado = 'en_validacion'
      }
    },

    async aprobarContenido(id) {
      try {
        const response = await contenidoService.aprobarContenido(id)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx].estado = 'publicado'
      }
    },

    async rechazarContenido(id, motivo) {
      try {
        const response = await contenidoService.rechazarContenido(id, motivo)
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) this.contenidos[idx] = response.data
      } catch (_) {
        const idx = this.contenidos.findIndex(c => c.id === id)
        if (idx !== -1) {
          this.contenidos[idx].estado = 'rechazado'
          this.contenidos[idx].motivoRechazo = motivo
        }
      }
    }
  }
})

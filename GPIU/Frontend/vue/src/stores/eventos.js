import { defineStore } from 'pinia'
import eventosService from '../services/eventosService.js'

const mockEventos = [
  {
    idEvento: 1,
    titulo: 'Charla de Bienvenida Primer Año 2024',
    descripcion: 'Presentación general de la universidad para estudiantes nuevos, incluyendo recorrido por el campus y presentación de facultades.',
    fecha: '2024-03-15',
    horaInicio: '09:00',
    horaFin: '12:00',
    sala: 'Auditorio Central',
    edificio: 'Edificio A',
    campus: 'Campus Principal',
    organizador: 'Dirección de Docencia',
    estaVigente: true
  },
  {
    idEvento: 2,
    titulo: 'Feria de Actividades Estudiantiles',
    descripcion: 'Muestra de clubes, deportes y actividades extracurriculares disponibles para estudiantes.',
    fecha: '2024-03-20',
    horaInicio: '10:00',
    horaFin: '17:00',
    sala: 'Plaza Central',
    edificio: null,
    campus: 'Campus Principal',
    organizador: 'Centro de Estudiantes',
    estaVigente: true
  },
  {
    idEvento: 3,
    titulo: 'Seminario de Investigación en Ingeniería',
    descripcion: 'Presentación de proyectos de investigación desarrollados por académicos del departamento.',
    fecha: '2024-02-10',
    horaInicio: '14:00',
    horaFin: '17:00',
    sala: 'Sala de Conferencias 201',
    edificio: 'Edificio de Ingeniería',
    campus: 'Campus Norte',
    organizador: 'Facultad de Ingeniería',
    estaVigente: false
  },
  {
    idEvento: 4,
    titulo: 'Congreso de Ciencias de la Computación',
    descripcion: 'Evento académico anual con presentaciones de investigación en inteligencia artificial, sistemas distribuidos y más.',
    fecha: '2024-04-05',
    horaInicio: '08:30',
    horaFin: '18:00',
    sala: 'Auditorio Norte',
    edificio: 'Edificio de Ciencias',
    campus: 'Campus Norte',
    organizador: 'Departamento de Informática',
    estaVigente: true
  }
]

export const useEventosStore = defineStore('eventos', {
  state: () => ({
    eventos: [],
    eventoActual: null,
    loading: false,
    error: null,
    filtros: {
      texto: '',
      fecha: '',
      campus: '',
      organizador: '',
      orden: 'fecha_asc'
    }
  }),

  actions: {
    async cargarEventos() {
      this.loading = true
      this.error = null
      try {
        const response = await eventosService.getEventosVigentes()
        this.eventos = response.data
      } catch (error) {
        if (error.code === 'ECONNREFUSED' || error.code === 'ERR_NETWORK') {
          this.eventos = mockEventos
        } else {
          this.eventos = mockEventos
          this.error = null
        }
      } finally {
        this.loading = false
      }
    },

    async cargarEventoDetalle(id) {
      this.loading = true
      this.error = null
      try {
        const response = await eventosService.getEventoById(id)
        this.eventoActual = response.data
      } catch (error) {
        const mock = mockEventos.find(e => e.idEvento === parseInt(id))
        if (mock) {
          this.eventoActual = mock
        } else {
          this.error = 'No se pudo cargar el detalle del evento.'
        }
      } finally {
        this.loading = false
      }
    },

    async buscarEventos(filtros) {
      this.loading = true
      this.error = null
      try {
        const response = await eventosService.getEventos(filtros)
        this.eventos = response.data
      } catch (error) {
        this.eventos = mockEventos
      } finally {
        this.loading = false
      }
    },

    aplicarFiltros(filtros) {
      this.filtros = { ...this.filtros, ...filtros }
    },

    limpiarFiltros() {
      this.filtros = {
        texto: '',
        fecha: '',
        campus: '',
        organizador: '',
        orden: 'fecha_asc'
      }
    }
  }
})

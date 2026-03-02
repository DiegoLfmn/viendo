import { defineStore } from 'pinia'
import piuService from '../services/piuService.js'

const mockPIUs = [
  { id: 1, codigo: 'PIU-001', ubicacion: 'Biblioteca Central - Acceso Principal', sala: 'Hall Entrada', estado: 'activo', fechaInstalacion: '2023-03-15', ip: '192.168.1.101', tipoPantalla: '55"', conectividad: 'WiFi + LAN' },
  { id: 2, codigo: 'PIU-002', ubicacion: 'Edificio A - Piso 1', sala: 'Hall Administrativo', estado: 'activo', fechaInstalacion: '2023-03-20', ip: '192.168.1.102', tipoPantalla: '43"', conectividad: 'LAN' },
  { id: 3, codigo: 'PIU-003', ubicacion: 'Facultad de Ingeniería', sala: 'Entrada Principal', estado: 'mantenimiento', fechaInstalacion: '2023-04-10', ip: '192.168.1.103', tipoPantalla: '55"', conectividad: 'WiFi' },
  { id: 4, codigo: 'PIU-004', ubicacion: 'Casino Central', sala: 'Acceso', estado: 'inactivo', fechaInstalacion: '2023-05-01', ip: '192.168.1.104', tipoPantalla: '32"', conectividad: 'LAN' }
]

const mockIncidencias = [
  { id: 1, piuId: 3, piuCodigo: 'PIU-003', descripcion: 'Pantalla con líneas horizontales, posible falla de hardware', estado: 'abierta', prioridad: 'alta', fechaReporte: '2024-02-28' },
  { id: 2, piuId: 4, piuCodigo: 'PIU-004', descripcion: 'No responde a pings, posible desconexión de red', estado: 'en_proceso', prioridad: 'media', fechaReporte: '2024-02-25' }
]

const mockEstadisticas = {
  totalPIUs: 4,
  activos: 2,
  conFallas: 2,
  disponibilidad: 75,
  incidenciasMes: 2
}

export const usePiuStore = defineStore('piu', {
  state: () => ({
    pius: [],
    estadoPIUs: [],
    incidencias: [],
    estadisticas: null,
    loading: false,
    error: null
  }),

  actions: {
    async cargarPIUs() {
      this.loading = true
      this.error = null
      try {
        const response = await piuService.getPIUs()
        this.pius = response.data
      } catch (_) {
        this.pius = mockPIUs
      } finally {
        this.loading = false
      }
    },

    async cargarEstado() {
      try {
        const response = await piuService.getEstadoPIUs()
        this.estadoPIUs = response.data
      } catch (_) {
        this.estadoPIUs = mockPIUs
      }
    },

    async cargarIncidencias() {
      try {
        const response = await piuService.getIncidencias()
        this.incidencias = response.data
      } catch (_) {
        this.incidencias = mockIncidencias
      }
    },

    async crearPIU(data) {
      try {
        const response = await piuService.crearPIU(data)
        this.pius.push(response.data)
        return response.data
      } catch (_) {
        const mock = { ...data, id: Date.now(), estado: 'activo', fechaInstalacion: new Date().toISOString().split('T')[0] }
        this.pius.push(mock)
        return mock
      }
    },

    async actualizarPIU(id, data) {
      try {
        const response = await piuService.actualizarPIU(id, data)
        const idx = this.pius.findIndex(p => p.id === id)
        if (idx !== -1) this.pius[idx] = response.data
        return response.data
      } catch (_) {
        const idx = this.pius.findIndex(p => p.id === id)
        if (idx !== -1) this.pius[idx] = { ...this.pius[idx], ...data }
        return this.pius[idx]
      }
    },

    async solicitarDesconexion(piuId, motivo) {
      try {
        await piuService.solicitarDesconexion(piuId, motivo)
        const idx = this.pius.findIndex(p => p.id === piuId)
        if (idx !== -1) this.pius[idx].estado = 'inactivo'
      } catch (_) {
        const idx = this.pius.findIndex(p => p.id === piuId)
        if (idx !== -1) this.pius[idx].estado = 'inactivo'
      }
    },

    async cargarEstadisticas() {
      try {
        const response = await piuService.getEstadisticas()
        this.estadisticas = response.data
      } catch (_) {
        this.estadisticas = mockEstadisticas
      }
    }
  }
})

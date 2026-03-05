import { defineStore } from 'pinia'
import piuService from '../services/piuService.js'

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
      } catch (error) {
        console.error("Error cargando PIUs desde el backend:", error)
        this.error = "No se pudieron cargar los PIUs desde el servidor."
        this.pius = [] 
      } finally {
        this.loading = false
      }
    },

    async cargarEstado() {
      try {
        const response = await piuService.getEstadoPIUs()
        this.estadoPIUs = response.data
      } catch (error) {
        console.error("Error cargando estado:", error)
        this.estadoPIUs = []
      }
    },

    async cargarIncidencias() {
      try {
        const response = await piuService.getIncidencias()
        this.incidencias = response.data
      } catch (error) {
        console.error("Error cargando incidencias:", error)
        this.incidencias = []
      }
    },

    async crearPIU(data) {
      try {
        const response = await piuService.crearPIU(data)
        this.pius.push(response.data)
        return response.data
      } catch (error) {
        console.error("Error al crear PIU:", error)
        throw error 
      }
    },

    async actualizarPIU(id, data) {
      try {
        const response = await piuService.actualizarPIU(id, data)
       
        const idx = this.pius.findIndex(p => p.id_piu === id)
        if (idx !== -1) {
          this.pius[idx] = response.data
        }
        return response.data
      } catch (error) {
        console.error("Error al actualizar PIU:", error)
        throw error
      }
    },

    async solicitarDesconexion(piuId, motivo) {
      try {
        await piuService.solicitarDesconexion(piuId, motivo)
        
        const idx = this.pius.findIndex(p => p.id_piu === piuId)
        if (idx !== -1) {
          this.pius[idx].estadoPiu = 'inactivo'
        }
      } catch (error) {
        console.error("Error solicitando desconexión:", error)
        throw error
      }
    },

    async cargarEstadisticas() {
      try {
        const response = await piuService.getEstadisticas()
        this.estadisticas = response.data
      } catch (error) {
        console.error("Error cargando estadísticas:", error)
        this.estadisticas = null
      }
    }
  }
})
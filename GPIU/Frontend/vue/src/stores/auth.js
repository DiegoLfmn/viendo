import { defineStore } from 'pinia'
import authService from '../services/authService.js'

const ROLES_PRIORITY = ['ADMINISTRADOR', 'GESTOR', 'OPERADOR', 'PROFESOR', 'FUNCIONARIO', 'ESTUDIANTE', 'VISITANTE']
const SESSION_DURATION = 15 * 60 * 1000 // 15 minutos en ms
const BLOQUEO_DURATION = 15 * 60 * 1000 // 15 minutos en ms
const MAX_INTENTOS = 5

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: null,
    sessionExpiry: null,
    intentosFallidos: 0,
    bloqueado: false,
    bloqueoExpiry: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    userRole: (state) => {
      if (!state.user || !state.user.roles) return 'VISITANTE'
      for (const role of ROLES_PRIORITY) {
        if (state.user.roles.includes(role)) return role
      }
      return 'VISITANTE'
    },
    userName: (state) => {
      if (!state.user) return 'Visitante'
      return state.user.nombre || state.user.username || 'Visitante'
    },
    isAdmin: (state) => {
      if (!state.user || !state.user.roles) return false
      return ['ADMINISTRADOR', 'GESTOR', 'OPERADOR'].some(r => state.user.roles.includes(r))
    }
  },

  actions: {
    async login(credenciales) {
      if (this.bloqueado) {
        const remaining = Math.ceil((this.bloqueoExpiry - Date.now()) / 60000)
        throw new Error(`Cuenta bloqueada. Intente en ${remaining} minutos.`)
      }
      try {
        const response = await authService.login(credenciales)
        const { token, user } = response.data
        this.token = token
        this.user = user
        this.sessionExpiry = Date.now() + SESSION_DURATION
        this.intentosFallidos = 0
        this.bloqueado = false
        localStorage.setItem('token', token)
        localStorage.setItem('user', JSON.stringify(user))
        localStorage.setItem('sessionExpiry', this.sessionExpiry.toString())
        return response.data
      } catch (error) {
        this.intentosFallidos++
        if (this.intentosFallidos >= MAX_INTENTOS) {
          this.bloqueado = true
          this.bloqueoExpiry = Date.now() + BLOQUEO_DURATION
          localStorage.setItem('bloqueoExpiry', this.bloqueoExpiry.toString())
          localStorage.setItem('intentosFallidos', this.intentosFallidos.toString())
        }
        throw error
      }
    },

    async logout() {
      try {
        await authService.logout()
      } catch (_) {
        // ignorar error de logout
      }
      this.token = null
      this.user = null
      this.sessionExpiry = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      localStorage.removeItem('sessionExpiry')
    },

    checkSession() {
      if (!this.token) return false
      if (this.sessionExpiry && Date.now() > this.sessionExpiry) {
        this.logout()
        return false
      }
      // Renovar expiración con actividad
      this.sessionExpiry = Date.now() + SESSION_DURATION
      localStorage.setItem('sessionExpiry', this.sessionExpiry.toString())
      return true
    },

    restoreSession() {
      const token = localStorage.getItem('token')
      const userStr = localStorage.getItem('user')
      const sessionExpiry = localStorage.getItem('sessionExpiry')
      const bloqueoExpiry = localStorage.getItem('bloqueoExpiry')
      const intentosFallidos = localStorage.getItem('intentosFallidos')

      if (bloqueoExpiry) {
        const expiry = parseInt(bloqueoExpiry)
        if (Date.now() < expiry) {
          this.bloqueado = true
          this.bloqueoExpiry = expiry
          this.intentosFallidos = parseInt(intentosFallidos || '0')
        } else {
          localStorage.removeItem('bloqueoExpiry')
          localStorage.removeItem('intentosFallidos')
        }
      }

      if (token && userStr && sessionExpiry) {
        const expiry = parseInt(sessionExpiry)
        if (Date.now() < expiry) {
          this.token = token
          this.user = JSON.parse(userStr)
          this.sessionExpiry = expiry
        } else {
          localStorage.removeItem('token')
          localStorage.removeItem('user')
          localStorage.removeItem('sessionExpiry')
        }
      }
    },

    inicializar() {
      this.restoreSession()
    }
  }
})

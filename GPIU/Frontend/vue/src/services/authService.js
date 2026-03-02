import api from './api.js'

export default {
  login(credenciales) {
    return api.post('/auth/login', credenciales)
  },
  logout() {
    return api.post('/auth/logout')
  },
  getMe() {
    return api.get('/auth/me')
  }
}

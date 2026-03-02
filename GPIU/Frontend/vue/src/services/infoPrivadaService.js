import api from './api.js'

export default {
  getInfoEstudiante() {
    return api.get('/info-privada/estudiante')
  },
  getInfoProfesor() {
    return api.get('/info-privada/profesor')
  },
  getInfoFuncionario() {
    return api.get('/info-privada/funcionario')
  },
  getRuta(destinoId) {
    return api.get(`/rutas/calcular?destino=${destinoId}`)
  }
}

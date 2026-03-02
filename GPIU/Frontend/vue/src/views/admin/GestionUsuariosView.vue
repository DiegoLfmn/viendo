<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Gestión de Usuarios</h1>
      <button class="btn-primary" @click="abrirModalCrear" aria-label="Crear usuario">+ Nuevo Usuario</button>
    </div>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" />

    <div v-else>
      <!-- Usuarios de prueba rápidos -->
      <div class="card demo-section">
        <h2 class="section-title">👤 Usuarios de Prueba Rápidos</h2>
        <p style="color: var(--color-text-light); margin-bottom: 1rem;">Crea usuarios de prueba para testear los diferentes roles del sistema.</p>
        <div class="demo-buttons">
          <button class="btn-secondary" @click="crearUsuarioPrueba('ADMINISTRADOR', 'admin@universidad.cl', 'admin123')">
            Crear Admin (admin@universidad.cl / admin123)
          </button>
          <button class="btn-secondary" @click="crearUsuarioPrueba('ESTUDIANTE', 'estudiante@universidad.cl', 'est123')">
            Crear Estudiante (estudiante@universidad.cl / est123)
          </button>
          <button class="btn-secondary" @click="crearUsuarioPrueba('PROFESOR', 'profesor@universidad.cl', 'prof123')">
            Crear Profesor (profesor@universidad.cl / prof123)
          </button>
          <button class="btn-secondary" @click="crearUsuarioPrueba('FUNCIONARIO', 'funcionario@universidad.cl', 'func123')">
            Crear Funcionario (funcionario@universidad.cl / func123)
          </button>
        </div>
        <div v-if="mensajeDemo" class="alert alert-success" style="margin-top:1rem;">{{ mensajeDemo }}</div>
      </div>

      <!-- Tabla de usuarios -->
      <div class="table-container card">
        <table class="table" aria-label="Lista de usuarios">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Correo</th>
              <th>Estado</th>
              <th>Roles</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="usuarios.length === 0">
              <td colspan="6" style="text-align:center;">No hay usuarios registrados.</td>
            </tr>
            <tr v-for="u in usuarios" :key="u.id_usuario">
              <td>{{ u.id_usuario }}</td>
              <td>{{ u.nombre_usuario }}</td>
              <td>{{ u.correo_usuario }}</td>
              <td>
                <span class="badge" :class="estadoBadge(u.estado_cuenta)">{{ u.estado_cuenta || 'activo' }}</span>
              </td>
              <td>
                <span v-for="r in u.roles" :key="r.id_rol" class="badge badge-en_validacion" style="margin-right:4px;">
                  {{ r.nombre_rol }}
                </span>
              </td>
              <td>
                <button class="btn-sm btn-secondary" @click="abrirModalEditar(u)">Editar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal crear/editar -->
    <div v-if="modalAbierto" class="modal-overlay" role="dialog" aria-modal="true" aria-label="Formulario de usuario">
      <div class="modal-box card">
        <h2>{{ modoEditar ? 'Editar Usuario' : 'Nuevo Usuario' }}</h2>
        <form @submit.prevent="guardarUsuario">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input id="nombre" v-model="form.nombre_usuario" type="text" class="form-control" required />
          </div>
          <div class="form-group">
            <label for="correo">Correo</label>
            <input id="correo" v-model="form.correo_usuario" type="email" class="form-control" required />
          </div>
          <div class="form-group" v-if="!modoEditar">
            <label for="contrasena">Contraseña</label>
            <input id="contrasena" v-model="form.contrasena_usuario" type="password" class="form-control" required />
          </div>
          <div class="form-group">
            <label for="estado">Estado</label>
            <select id="estado" v-model="form.estado_cuenta" class="form-control">
              <option value="activo">Activo</option>
              <option value="inactivo">Inactivo</option>
              <option value="bloqueado">Bloqueado</option>
            </select>
          </div>
          <div v-if="errorForm" class="alert alert-error">{{ errorForm }}</div>
          <div class="modal-actions">
            <button type="button" class="btn-outline" @click="cerrarModal">Cancelar</button>
            <button type="submit" class="btn-primary" :disabled="guardando">
              {{ guardando ? 'Guardando...' : 'Guardar' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import LoadingSpinner from '../../components/LoadingSpinner.vue'
import ErrorMessage from '../../components/ErrorMessage.vue'
import api from '../../services/api.js'

const usuarios = ref([])
const loading = ref(false)
const error = ref(null)
const modalAbierto = ref(false)
const modoEditar = ref(false)
const form = ref({ nombre_usuario: '', correo_usuario: '', contrasena_usuario: '', estado_cuenta: 'activo' })
const editandoId = ref(null)
const guardando = ref(false)
const errorForm = ref('')
const mensajeDemo = ref('')

async function cargar() {
  loading.value = true
  error.value = null
  try {
    const res = await api.get('/usuarios')
    usuarios.value = res.data
  } catch (e) {
    error.value = 'No se pudo cargar la lista de usuarios'
  } finally {
    loading.value = false
  }
}

function abrirModalCrear() {
  modoEditar.value = false
  form.value = { nombre_usuario: '', correo_usuario: '', contrasena_usuario: '', estado_cuenta: 'activo' }
  errorForm.value = ''
  modalAbierto.value = true
}

function abrirModalEditar(u) {
  modoEditar.value = true
  editandoId.value = u.id_usuario
  form.value = { nombre_usuario: u.nombre_usuario, correo_usuario: u.correo_usuario, estado_cuenta: u.estado_cuenta || 'activo' }
  errorForm.value = ''
  modalAbierto.value = true
}

function cerrarModal() {
  modalAbierto.value = false
}

async function guardarUsuario() {
  guardando.value = true
  errorForm.value = ''
  try {
    if (modoEditar.value) {
      await api.put(`/usuarios/${editandoId.value}`, form.value)
    } else {
      await api.post('/usuarios', form.value)
    }
    await cargar()
    cerrarModal()
  } catch (e) {
    errorForm.value = e.response?.data?.error || 'Error al guardar usuario'
  } finally {
    guardando.value = false
  }
}

async function crearUsuarioPrueba(rolNombre, correo, contrasena) {
  mensajeDemo.value = ''
  try {
    const rolesRes = await api.get('/roles')
    const rol = rolesRes.data.find(r => r.nombre_rol?.toUpperCase() === rolNombre)

    const payload = {
      nombre_usuario: rolNombre.charAt(0) + rolNombre.slice(1).toLowerCase() + ' de Prueba',
      correo_usuario: correo,
      contrasena_usuario: contrasena,
      estado_cuenta: 'activo',
      roles: rol ? [rol] : []
    }
    await api.post('/usuarios', payload)
    mensajeDemo.value = `✅ Usuario ${rolNombre} creado: ${correo}`
    await cargar()
  } catch (e) {
    mensajeDemo.value = `❌ Error: ${e.response?.data?.error || e.message}. Intente crear el usuario manualmente.`
  }
}

function estadoBadge(estado) {
  if (estado === 'activo' || !estado) return 'badge-vigente'
  if (estado === 'bloqueado') return 'badge-rechazado'
  return 'badge-en_validacion'
}

onMounted(cargar)
</script>

<style scoped>
.demo-section { margin-bottom: 1.5rem; }
.demo-buttons { display: flex; flex-wrap: wrap; gap: 0.75rem; }
.modal-overlay {
  position: fixed; inset: 0; background: rgba(0,0,0,0.5);
  display: flex; align-items: center; justify-content: center; z-index: 1000;
}
.modal-box { width: 100%; max-width: 480px; padding: 2rem; }
.modal-actions { display: flex; justify-content: flex-end; gap: 1rem; margin-top: 1.5rem; }
</style>

<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Gestión de Usuarios</h1>
<<<<<<< HEAD
      <button class="btn-primary" @click="abrirModalCrear" aria-label="Crear usuario">+ Nuevo Usuario</button>
=======
      <button class="btn-primary" @click="abrirModalCrear" aria-label="Crear usuario">Crear Usuario Nuevo</button>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
    </div>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" />

    <div v-else>
<<<<<<< HEAD
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
=======
      

>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD
              <td>{{ u.nombre_usuario }}</td>
              <td>{{ u.correo_usuario }}</td>
=======
              <td>{{ u.nombreUsuario }}</td>
              <td>{{ u.correoUsuario }}</td>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
              <td>
                <span class="badge" :class="estadoBadge(u.estado_cuenta)">{{ u.estado_cuenta || 'activo' }}</span>
              </td>
              <td>
<<<<<<< HEAD
                <span v-for="r in u.roles" :key="r.id_rol" class="badge badge-en_validacion" style="margin-right:4px;">
                  {{ r.nombre_rol }}
=======
                <span v-for="r in u.roles" :key="r.id_rol" class="badge badge-rol" style="margin-right:4px;">
                  {{ r.nombreRol }}
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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

<<<<<<< HEAD
    <!-- Modal crear/editar -->
=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
    <div v-if="modalAbierto" class="modal-overlay" role="dialog" aria-modal="true" aria-label="Formulario de usuario">
      <div class="modal-box card">
        <h2>{{ modoEditar ? 'Editar Usuario' : 'Nuevo Usuario' }}</h2>
        <form @submit.prevent="guardarUsuario">
          <div class="form-group">
            <label for="nombre">Nombre</label>
<<<<<<< HEAD
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
=======
            <input id="nombre" v-model="form.nombreUsuario" type="text" class="form-control" required />
          </div>
          <div class="form-group">
            <label for="correo">Correo</label>
            <input id="correo" v-model="form.correoUsuario" type="email" class="form-control" required />
          </div>
          
          <div class="form-group">
            <label for="contrasena">{{ modoEditar ? 'Cambiar Contraseña' : 'Contraseña' }}</label>
            <input id="contrasena" v-model="form.contrasena_usuario" type="password" class="form-control" :required="!modoEditar" />
          </div>

>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          <div class="form-group">
            <label for="estado">Estado</label>
            <select id="estado" v-model="form.estado_cuenta" class="form-control">
              <option value="activo">Activo</option>
              <option value="inactivo">Inactivo</option>
              <option value="bloqueado">Bloqueado</option>
            </select>
          </div>
<<<<<<< HEAD
=======
          
          <div class="form-group">
            <label for="roles">Roles</label>
            <select id="roles" v-model="form.rolesIds" class="form-control" multiple style="height: 100px;">
              <option v-for="rol in rolesDisponibles" :key="rol.id_rol" :value="rol.id_rol">
                {{ rol.nombreRol }}
              </option>
            </select>
            <small style="color:gray; font-size: 0.8rem;">Manteniendo CTRL, seleccionas varios roles.</small>
          </div>
          
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD
=======
const rolesDisponibles = ref([]) 
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
const loading = ref(false)
const error = ref(null)
const modalAbierto = ref(false)
const modoEditar = ref(false)
<<<<<<< HEAD
const form = ref({ nombre_usuario: '', correo_usuario: '', contrasena_usuario: '', estado_cuenta: 'activo' })
=======

const form = ref({ nombreUsuario: '', correoUsuario: '', contrasena_usuario: '', estado_cuenta: 'activo', rolesIds: [] })
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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

<<<<<<< HEAD
function abrirModalCrear() {
  modoEditar.value = false
  form.value = { nombre_usuario: '', correo_usuario: '', contrasena_usuario: '', estado_cuenta: 'activo' }
=======
async function cargarRoles() {
  try {
    const res = await api.get('/roles')
    rolesDisponibles.value = res.data
  } catch (e) {
    console.error("Error al cargar roles:", e)
  }
}

function abrirModalCrear() {
  modoEditar.value = false
  form.value = { nombreUsuario: '', correoUsuario: '', contrasena_usuario: '', estado_cuenta: 'activo', rolesIds: [] }
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
  errorForm.value = ''
  modalAbierto.value = true
}

function abrirModalEditar(u) {
  modoEditar.value = true
  editandoId.value = u.id_usuario
<<<<<<< HEAD
  form.value = { nombre_usuario: u.nombre_usuario, correo_usuario: u.correo_usuario, estado_cuenta: u.estado_cuenta || 'activo' }
=======
  form.value = { 
    nombreUsuario: u.nombreUsuario, 
    correoUsuario: u.correoUsuario, 
    contrasena_usuario: '',
    estado_cuenta: u.estado_cuenta || 'activo',
    rolesIds: u.roles ? u.roles.map(r => r.id_rol) : []
  }
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD
    if (modoEditar.value) {
      await api.put(`/usuarios/${editandoId.value}`, form.value)
    } else {
      await api.post('/usuarios', form.value)
=======
    const payload = { ...form.value }
    payload.roles = payload.rolesIds.map(id => ({ id_rol: id }))
    delete payload.rolesIds 


    if (modoEditar.value && !payload.contrasena_usuario) {
      delete payload.contrasena_usuario
    }

    if (modoEditar.value) {
      await api.put(`/usuarios/${editandoId.value}`, payload)
    } else {
      await api.post('/usuarios', payload)
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD
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
=======
    const rol = rolesDisponibles.value.find(r => r.nombreRol?.toUpperCase() === rolNombre)

    const payload = {
      nombreUsuario: rolNombre.charAt(0) + rolNombre.slice(1).toLowerCase() + ' de Prueba',
      correoUsuario: correo,
      contrasena_usuario: contrasena,
      estado_cuenta: 'activo',
      roles: rol ? [{ id_rol: rol.id_rol }] : []
    }
    await api.post('/usuarios', payload)
    mensajeDemo.value = ` Usuario ${rolNombre} creado: ${correo}`
    await cargar()
  } catch (e) {
    mensajeDemo.value = ` Error: ${e.response?.data?.error || e.message}. Intente crear el usuario manualmente.`
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
  }
}

function estadoBadge(estado) {
  if (estado === 'activo' || !estado) return 'badge-vigente'
  if (estado === 'bloqueado') return 'badge-rechazado'
  return 'badge-en_validacion'
}

<<<<<<< HEAD
onMounted(cargar)
</script>

<style scoped>
.demo-section { margin-bottom: 1.5rem; }
.demo-buttons { display: flex; flex-wrap: wrap; gap: 0.75rem; }
=======
onMounted(() => {
  cargar()
  cargarRoles()
})
</script>

<style scoped>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
.modal-overlay {
  position: fixed; inset: 0; background: rgba(0,0,0,0.5);
  display: flex; align-items: center; justify-content: center; z-index: 1000;
}
.modal-box { width: 100%; max-width: 480px; padding: 2rem; }
.modal-actions { display: flex; justify-content: flex-end; gap: 1rem; margin-top: 1.5rem; }
<<<<<<< HEAD
</style>
=======
.table-container {
  margin-top: 2rem;
}
</style>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Gestión de Contenido</h1>
      <button class="btn-primary" @click="abrirModalNuevo" aria-label="Crear nuevo contenido">
        + Nuevo Contenido
      </button>
    </div>

    <!-- Filtros -->
    <div class="filters-bar card">
      <div class="filters-row">
        <div class="filter-group">
          <label for="filtro-estado">Estado</label>
          <select id="filtro-estado" v-model="filtroEstado" class="form-control" aria-label="Filtrar por estado">
            <option value="">Todos</option>
            <option value="borrador">Borrador</option>
            <option value="en_validacion">En Validación</option>
            <option value="publicado">Publicado</option>
            <option value="rechazado">Rechazado</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="filtro-tipo">Tipo</label>
          <select id="filtro-tipo" v-model="filtroTipo" class="form-control" aria-label="Filtrar por tipo">
            <option value="">Todos</option>
            <option value="noticia">Noticia</option>
            <option value="evento">Evento</option>
            <option value="proceso">Proceso</option>
            <option value="info-general">Info General</option>
          </select>
        </div>
      </div>
    </div>

    <LoadingSpinner v-if="contenidoStore.loading" />
    <ErrorMessage v-else-if="contenidoStore.error" :message="contenidoStore.error" :on-retry="() => contenidoStore.cargarContenidos()" />

    <div v-else class="table-container">
      <table class="table" aria-label="Tabla de contenidos">
        <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Tipo</th>
            <th>Estado</th>
            <th>Fecha</th>
            <th>Autor</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="contenidosFiltrados.length === 0">
            <td colspan="7" style="text-align:center; color: var(--color-text-light);">No hay contenidos.</td>
          </tr>
          <tr v-for="c in contenidosFiltrados" :key="c.id">
            <td>{{ c.id }}</td>
            <td>{{ c.titulo }}</td>
            <td>{{ c.tipo }}</td>
            <td><span class="badge" :class="`badge-${c.estado}`">{{ estadoLabel(c.estado) }}</span></td>
            <td>{{ c.fechaCreacion }}</td>
            <td>{{ c.autor }}</td>
            <td class="acciones-cell">
              <!-- Acciones por estado -->
              <template v-if="c.estado === 'borrador'">
                <button class="btn-sm btn-secondary" @click="editarContenido(c)" :aria-label="`Editar ${c.titulo}`">Editar</button>
                <button class="btn-sm btn-outline" @click="enviarValidacion(c)" :aria-label="`Enviar a validación ${c.titulo}`">Enviar</button>
<<<<<<< HEAD
                <button class="btn-sm btn-danger" @click="eliminarContenido(c)" :aria-label="`Eliminar ${c.titulo}`">Eliminar</button>
=======
                <button v-if="authStore.userRole === 'ADMINISTRADOR'" class="btn-sm btn-danger" @click="eliminarContenido(c)" :aria-label="`Eliminar ${c.titulo}`">Eliminar</button>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
              </template>
              <template v-else-if="c.estado === 'en_validacion'">
                <button class="btn-sm btn-secondary" @click="verDetalle(c)" :aria-label="`Ver detalle de ${c.titulo}`">Ver</button>
              </template>
              <template v-else-if="c.estado === 'publicado'">
                <button class="btn-sm btn-secondary" @click="verDetalle(c)" :aria-label="`Ver ${c.titulo}`">Ver</button>
                <button class="btn-sm btn-outline" @click="ocultarContenido(c)" :aria-label="`Ocultar ${c.titulo}`">Ocultar</button>
              </template>
              <template v-else-if="c.estado === 'rechazado'">
                <button class="btn-sm btn-secondary" @click="verMotivo(c)" :aria-label="`Ver motivo de rechazo de ${c.titulo}`">Motivo</button>
                <button class="btn-sm btn-outline" @click="editarContenido(c)" :aria-label="`Editar ${c.titulo}`">Editar</button>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal Crear/Editar Contenido -->
    <div v-if="modalVisible" class="modal-overlay" role="dialog" aria-modal="true" :aria-label="editandoId ? 'Editar contenido' : 'Nuevo contenido'">
      <div class="modal-content">
        <div class="modal-header">
          <h2>{{ editandoId ? 'Editar Contenido' : 'Nuevo Contenido' }}</h2>
          <button class="modal-close" @click="cerrarModal" aria-label="Cerrar modal">×</button>
        </div>
        <form @submit.prevent="guardarContenido">
          <div class="form-group">
            <label for="c-titulo">Título <span aria-hidden="true">*</span></label>
            <input id="c-titulo" v-model="formContenido.titulo" type="text" class="form-control" required />
          </div>
          <div class="form-group">
            <label for="c-tipo">Tipo de Contenido</label>
            <select id="c-tipo" v-model="formContenido.tipo" class="form-control">
              <option value="noticia">Noticia</option>
              <option value="evento">Evento</option>
              <option value="proceso">Proceso</option>
              <option value="info-general">Información General</option>
            </select>
          </div>
          <div class="form-group">
            <label for="c-cuerpo">Contenido</label>
            <textarea id="c-cuerpo" v-model="formContenido.cuerpo" class="form-control" rows="4" placeholder="Ingrese el contenido..."></textarea>
          </div>
          <div class="form-group">
            <label for="c-categoria">Categoría</label>
            <input id="c-categoria" v-model="formContenido.categoria" type="text" class="form-control" placeholder="Ej: institucional" />
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="c-fecha-ini">Vigencia Inicio</label>
              <input id="c-fecha-ini" v-model="formContenido.fechaVigenciaInicio" type="date" class="form-control" />
            </div>
            <div class="form-group">
              <label for="c-fecha-fin">Vigencia Fin</label>
              <input id="c-fecha-fin" v-model="formContenido.fechaVigenciaFin" type="date" class="form-control" />
            </div>
          </div>

          <div v-if="mensajeForm" class="alert" :class="mensajeForm.tipo === 'error' ? 'alert-error' : 'alert-success'">
            {{ mensajeForm.texto }}
          </div>

          <div class="modal-actions">
            <button type="button" class="btn-outline" @click="cerrarModal">Cancelar</button>
            <button type="submit" class="btn-primary">{{ editandoId ? 'Guardar Cambios' : 'Crear' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useContenidoStore } from '../../stores/contenido.js'
import { useAuthStore } from '../../stores/auth.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'
import ErrorMessage from '../../components/ErrorMessage.vue'

const contenidoStore = useContenidoStore()
const authStore = useAuthStore()

const filtroEstado = ref('')
const filtroTipo = ref('')
const modalVisible = ref(false)
const editandoId = ref(null)
const mensajeForm = ref(null)

const formContenido = ref({
  titulo: '',
  tipo: 'noticia',
  cuerpo: '',
  categoria: '',
  fechaVigenciaInicio: '',
  fechaVigenciaFin: ''
})

const contenidosFiltrados = computed(() => {
  let lista = contenidoStore.contenidos
  if (filtroEstado.value) lista = lista.filter(c => c.estado === filtroEstado.value)
  if (filtroTipo.value) lista = lista.filter(c => c.tipo === filtroTipo.value)
  return lista
})

function estadoLabel(estado) {
  const labels = { borrador: 'Borrador', en_validacion: 'En Validación', publicado: 'Publicado', rechazado: 'Rechazado' }
  return labels[estado] || estado
}

function abrirModalNuevo() {
  editandoId.value = null
  formContenido.value = { titulo: '', tipo: 'noticia', cuerpo: '', categoria: '', fechaVigenciaInicio: '', fechaVigenciaFin: '' }
  mensajeForm.value = null
  modalVisible.value = true
}

function editarContenido(c) {
  editandoId.value = c.id
  formContenido.value = { titulo: c.titulo, tipo: c.tipo, cuerpo: c.cuerpo || '', categoria: c.categoria || '', fechaVigenciaInicio: c.fechaVigenciaInicio || '', fechaVigenciaFin: c.fechaVigenciaFin || '' }
  mensajeForm.value = null
  modalVisible.value = true
}

function cerrarModal() {
  modalVisible.value = false
}

async function guardarContenido() {
  mensajeForm.value = null
  const data = {
    ...formContenido.value,
    autor: authStore.userName,
    estado: 'borrador'
  }
  try {
    if (editandoId.value) {
      await contenidoStore.editarContenido(editandoId.value, data)
      mensajeForm.value = { tipo: 'success', texto: 'Contenido actualizado.' }
    } else {
      await contenidoStore.crearContenido(data)
      mensajeForm.value = { tipo: 'success', texto: 'Contenido creado.' }
    }
    setTimeout(() => cerrarModal(), 1000)
  } catch (_) {
    mensajeForm.value = { tipo: 'error', texto: 'Error al guardar.' }
  }
}

async function enviarValidacion(c) {
  if (confirm(`¿Enviar "${c.titulo}" a validación?`)) {
    await contenidoStore.enviarAValidacion(c.id)
  }
}

async function eliminarContenido(c) {
  if (confirm(`¿Eliminar "${c.titulo}"?`)) {
    await contenidoStore.eliminarContenido(c.id)
  }
}

function verDetalle(c) {
  alert(`Título: ${c.titulo}\nTipo: ${c.tipo}\nEstado: ${c.estado}\n\n${c.cuerpo || '(sin contenido)'}`)
}

async function ocultarContenido(c) {
  if (confirm(`¿Ocultar "${c.titulo}"?`)) {
    await contenidoStore.editarContenido(c.id, { ...c, estado: 'borrador' })
  }
}

function verMotivo(c) {
  alert(`Motivo de rechazo:\n${c.motivoRechazo || 'No se especificó motivo.'}`)
}

onMounted(() => contenidoStore.cargarContenidos())
</script>

<style scoped>
.view-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.25rem;
}

.page-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
}

.filters-bar {
  margin-bottom: 1.25rem;
}

.filters-row {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  min-width: 150px;
}

.filter-group label {
  font-size: 0.8rem;
  font-weight: 500;
  color: var(--color-text-light);
}

.table-container {
  overflow-x: auto;
}

.acciones-cell {
  display: flex;
  gap: 0.4rem;
  flex-wrap: wrap;
}

.btn-sm {
  padding: 0.25rem 0.6rem;
  font-size: 0.75rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: 500;
}

.btn-sm.btn-secondary { background-color: var(--color-secondary); color: white; }
.btn-sm.btn-danger { background-color: var(--color-danger); color: white; }
.btn-sm.btn-outline { background-color: transparent; color: var(--color-primary); border: 1px solid var(--color-primary); }

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}
</style>

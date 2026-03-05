<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Gestión de PIUs</h1>
      <div class="header-actions">
        <select v-model="filtroEstado" class="form-control" aria-label="Filtrar por estado" style="width: auto;">
          <option value="">Todos los estados</option>
          <option value="activo">Activo</option>
          <option value="inactivo">Inactivo</option>
          <option value="mantenimiento">Mantenimiento</option>
        </select>
        <button class="btn-primary" @click="abrirModalNuevo" aria-label="Agregar nuevo PIU">
          + Nuevo PIU
        </button>
      </div>
    </div>

    <LoadingSpinner v-if="piuStore.loading" />
    <ErrorMessage v-else-if="piuStore.error" :message="piuStore.error" :on-retry="piuStore.cargarPIUs" />

    <div v-else class="table-container">
      <table class="table" aria-label="Lista de PIUs">
        <thead>
          <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Ubicación</th>
            <th>Estado</th>
            <th>Fecha Instalación</th>
            <th>Acciones</th>
          </tr>
        </thead>
<tbody>
          <tr v-if="piusFiltrados.length === 0">
            <td colspan="6" style="text-align:center; color: var(--color-text-light);">No hay PIUs registrados.</td>
          </tr>
          <tr v-for="piu in piusFiltrados" :key="piu.id_piu">
            <td>{{ piu.id_piu }}</td>
            <td><strong>{{ piu.codigoPiu }}</strong></td>
            <td>
              Lat: {{ piu.latitudPiu }} <br>
              Lon: {{ piu.longitudPiu }}
            </td>
            <td>
              <span class="estado-dot" :class="`estado-${piu.estadoPiu}`" aria-label="Estado del PIU"></span>
              {{ estadoLabel(piu.estadoPiu) }}
            </td>
            <td>{{ piu.fechaInstalacion }}</td>
            <td class="acciones-cell">
              <button class="btn-sm btn-secondary" @click="editarPIU(piu)" :aria-label="`Editar PIU ${piu.codigoPiu}`">Editar</button>
<<<<<<< HEAD
              <button class="btn-sm btn-danger" @click="confirmarBaja(piu)" :aria-label="`Dar de baja PIU ${piu.codigoPiu}`">Baja</button>
=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
              <button class="btn-sm btn-outline" @click="solicitarTraslado(piu)" :aria-label="`Trasladar PIU ${piu.codigoPiu}`">Trasladar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    
    <div v-if="modalVisible" class="modal-overlay" role="dialog" aria-modal="true" :aria-label="editando ? 'Editar PIU' : 'Nuevo PIU'">
      <div class="modal-content">
        <div class="modal-header">
          <h2>{{ editando ? 'Editar PIU' : 'Nuevo PIU' }}</h2>
          <button class="modal-close" @click="cerrarModal" aria-label="Cerrar modal">×</button>
        </div>

        <form @submit.prevent="guardarPIU">
          <div class="form-group">
            <label for="piu-codigo">Código PIU</label>
            <input id="piu-codigo" v-model="formPIU.codigoPiu" type="text" class="form-control" required placeholder="Ej: PIU-005" />
          </div>
          <div class="form-group">
            <label for="piu-estado">Estado</label>
            <select id="piu-estado" v-model="formPIU.estadoPiu" class="form-control" required>
              <option value="activo">Activo</option>
              <option value="inactivo">Inactivo</option>
              <option value="mantenimiento">Mantenimiento</option>
            </select>
          </div>
          <div class="form-group">
            <label for="piu-latitud">Latitud</label>
            <input id="piu-latitud" v-model="formPIU.latitudPiu" type="number" step="any" class="form-control" placeholder="Ej: -33.456" />
          </div>
          <div class="form-group">
            <label for="piu-longitud">Longitud</label>
            <input id="piu-longitud" v-model="formPIU.longitudPiu" type="number" step="any" class="form-control" placeholder="Ej: -70.654" />
          </div>
          <div class="form-group">
            <label for="piu-fecha">Fecha de Instalación</label>
            <input id="piu-fecha" v-model="formPIU.fechaInstalacion" type="date" class="form-control" />
          </div>

          <div v-if="mensajeForm" class="alert" :class="mensajeForm.tipo === 'error' ? 'alert-error' : 'alert-success'">
            {{ mensajeForm.texto }}
          </div>

          <div class="modal-actions">
            <button type="button" class="btn-outline" @click="cerrarModal">Cancelar</button>
            <button type="submit" class="btn-primary">{{ editando ? 'Guardar Cambios' : 'Crear PIU' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { usePiuStore } from '../../stores/piu.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'
import ErrorMessage from '../../components/ErrorMessage.vue'

const piuStore = usePiuStore()
const filtroEstado = ref('')
const modalVisible = ref(false)
const editando = ref(false)
const editandoId = ref(null)
const mensajeForm = ref(null)

const formPIU = ref({
  codigoPiu: '',
  estadoPiu: 'Activo',
  latitudPiu: null,
  longitudPiu: null,
  fechaInstalacion: ''
})

const piusFiltrados = computed(() => {
  if (!filtroEstado.value) return piuStore.pius
  return piuStore.pius.filter(p => p.estadoPiu === filtroEstado.value)
})

function estadoLabel(estado) {
  const labels = { activo: 'Activo', inactivo: 'Inactivo', mantenimiento: 'Mantenimiento' }
  return labels[estado] || estado
}

function abrirModalNuevo() {
  editando.value = false
  editandoId.value = null
  formPIU.value = { codigoPiu: '', estadoPiu: 'activo', latitudPiu: null, longitudPiu: null, fechaInstalacion: '' }
  mensajeForm.value = null
  modalVisible.value = true
}

function editarPIU(piu) {
  editando.value = true
  editandoId.value = piu.id_piu 
  formPIU.value = { 
    codigoPiu: piu.codigoPiu, 
    estadoPiu: piu.estadoPiu, 
    latitudPiu: piu.latitudPiu, 
    longitudPiu: piu.longitudPiu, 

    fechaInstalacion: piu.fechaInstalacion ? piu.fechaInstalacion.split('T')[0] : ''
  }
  mensajeForm.value = null
  modalVisible.value = true
}
function cerrarModal() {
  modalVisible.value = false
}

async function guardarPIU() {
  mensajeForm.value = null
  try {
    if (editando.value) {
      await piuStore.actualizarPIU(editandoId.value, formPIU.value)
      mensajeForm.value = { tipo: 'success', texto: 'PIU actualizado correctamente.' }
    } else {
      await piuStore.crearPIU(formPIU.value)
      mensajeForm.value = { tipo: 'success', texto: 'PIU creado correctamente.' }
    }
    setTimeout(() => cerrarModal(), 1200)
  } catch (_) {
    mensajeForm.value = { tipo: 'error', texto: 'Error al guardar. Intente nuevamente.' }
  }
}

async function confirmarBaja(piu) {
  if (confirm(`¿Está seguro de dar de baja el PIU ${piu.codigoPiu}?`)) {
    await piuStore.solicitarDesconexion(piu.id_piu, 'Baja administrativa')
  }
}

function solicitarTraslado(piu) {
  const nuevaLat = prompt(`Nueva latitud para ${piu.codigoPiu}:`, piu.latitudPiu || '')
  const nuevaLon = prompt(`Nueva longitud para ${piu.codigoPiu}:`, piu.longitudPiu || '')
  
  if (nuevaLat !== null && nuevaLon !== null) {
    piuStore.actualizarPIU(piu.id_piu, { 
      ...piu, 
      latitudPiu: parseFloat(nuevaLat),
      longitudPiu: parseFloat(nuevaLon)
    })
  }
}

onMounted(() => piuStore.cargarPIUs())
</script>

<style scoped>
.view-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.page-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
}

.header-actions {
  display: flex;
  gap: 0.75rem;
  align-items: center;
}

.table-container {
  overflow-x: auto;
}

.estado-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 4px;
  vertical-align: middle;
}

.estado-activo { background-color: var(--color-success); }
.estado-inactivo { background-color: var(--color-danger); }
.estado-mantenimiento { background-color: var(--color-warning); }

.acciones-cell {
  display: flex;
  gap: 0.4rem;
  flex-wrap: wrap;
}

.btn-sm {
  padding: 0.25rem 0.6rem;
  font-size: 0.8rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: 500;
}

.btn-sm.btn-secondary {
  background-color: var(--color-secondary);
  color: white;
}

.btn-sm.btn-danger {
  background-color: var(--color-danger);
  color: white;
}

.btn-sm.btn-outline {
  background-color: transparent;
  color: var(--color-primary);
  border: 1px solid var(--color-primary);
}
</style>

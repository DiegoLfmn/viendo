<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Monitoreo de PIUs</h1>
      <button class="btn-secondary" @click="refrescar" aria-label="Refrescar estado de PIUs">
        🔄 Refrescar
      </button>
    </div>

    <LoadingSpinner v-if="piuStore.loading" />

    <!-- Grid de tarjetas por PIU -->
    <section aria-label="Estado de dispositivos PIU">
      <h2 class="section-title">Estado de Dispositivos</h2>
      <div class="piu-grid">
        <div
          v-for="piu in piuStore.pius"
          :key="piu.id"
          class="piu-card card"
          :class="`estado-${piu.estado}`"
          :aria-label="`PIU ${piu.codigo}, estado: ${estadoLabel(piu.estado)}`"
        >
          <div class="piu-card-header">
            <span class="piu-codigo">{{ piu.codigo }}</span>
            <span class="piu-estado-dot" :class="`dot-${piu.estado}`" aria-hidden="true"></span>
          </div>
          <p class="piu-ubicacion">{{ piu.ubicacion }}</p>
          <p class="piu-estado-label">{{ estadoLabel(piu.estado) }}</p>
          <p class="piu-ultima-actividad">Última actividad: hace {{ Math.floor(Math.random() * 30) + 1 }} min</p>
          <button
            v-if="piu.estado === 'activo'"
            class="btn-sm btn-danger"
            @click="solicitarDesconexion(piu)"
            :aria-label="`Solicitar desconexión de ${piu.codigo}`"
          >
            Solicitar desconexión
          </button>
        </div>
      </div>
    </section>

    <!-- Tabla de incidencias -->
    <section class="incidencias-section" aria-label="Incidencias abiertas">
      <div class="section-header">
        <h2 class="section-title">Incidencias Abiertas</h2>
        <button class="btn-primary" @click="abrirModalIncidencia" aria-label="Nueva incidencia">
          + Nueva Incidencia
        </button>
      </div>

      <div class="table-container">
        <table class="table" aria-label="Tabla de incidencias">
          <thead>
            <tr>
              <th>PIU</th>
              <th>Descripción</th>
              <th>Estado</th>
              <th>Prioridad</th>
              <th>Fecha Reporte</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="piuStore.incidencias.length === 0">
              <td colspan="6" style="text-align:center; color: var(--color-text-light);">No hay incidencias registradas.</td>
            </tr>
            <tr v-for="inc in piuStore.incidencias" :key="inc.id">
              <td><strong>{{ inc.piuCodigo }}</strong></td>
              <td>{{ inc.descripcion }}</td>
              <td>
                <span class="badge" :class="inc.estado === 'abierta' ? 'badge-rechazado' : 'badge-en_validacion'">
                  {{ inc.estado }}
                </span>
              </td>
              <td>
                <span class="badge" :class="inc.prioridad === 'alta' ? 'badge-rechazado' : 'badge-en_validacion'">
                  {{ inc.prioridad }}
                </span>
              </td>
              <td>{{ inc.fechaReporte }}</td>
              <td class="acciones-cell">
                <button class="btn-sm btn-secondary" @click="verIncidencia(inc)" :aria-label="`Ver detalle incidencia ${inc.id}`">Ver</button>
                <button class="btn-sm btn-outline" @click="cerrarIncidencia(inc)" :aria-label="`Cerrar incidencia ${inc.id}`">Cerrar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Modal Nueva Incidencia -->
    <div v-if="modalIncidencia" class="modal-overlay" role="dialog" aria-modal="true" aria-label="Nueva incidencia">
      <div class="modal-content">
        <div class="modal-header">
          <h2>Nueva Incidencia</h2>
          <button class="modal-close" @click="modalIncidencia = false" aria-label="Cerrar">×</button>
        </div>
        <form @submit.prevent="crearIncidencia">
          <div class="form-group">
            <label for="inc-piu">PIU</label>
            <select id="inc-piu" v-model="formIncidencia.piuId" class="form-control" required>
              <option v-for="piu in piuStore.pius" :key="piu.id" :value="piu.id">{{ piu.codigo }} - {{ piu.ubicacion }}</option>
            </select>
          </div>
          <div class="form-group">
            <label for="inc-descripcion">Descripción</label>
            <textarea id="inc-descripcion" v-model="formIncidencia.descripcion" class="form-control" rows="3" required placeholder="Describa la incidencia..."></textarea>
          </div>
          <div class="form-group">
            <label for="inc-prioridad">Prioridad</label>
            <select id="inc-prioridad" v-model="formIncidencia.prioridad" class="form-control">
              <option value="baja">Baja</option>
              <option value="media">Media</option>
              <option value="alta">Alta</option>
            </select>
          </div>
          <div class="modal-actions">
            <button type="button" class="btn-outline" @click="modalIncidencia = false">Cancelar</button>
            <button type="submit" class="btn-primary">Registrar Incidencia</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { usePiuStore } from '../../stores/piu.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'
import piuService from '../../services/piuService.js'

const piuStore = usePiuStore()
const modalIncidencia = ref(false)
const formIncidencia = ref({ piuId: null, descripcion: '', prioridad: 'media' })

function estadoLabel(estado) {
  const labels = { activo: 'Activo', inactivo: 'Inactivo', mantenimiento: 'Mantenimiento' }
  return labels[estado] || estado
}

async function refrescar() {
  await piuStore.cargarEstado()
  await piuStore.cargarIncidencias()
}

async function solicitarDesconexion(piu) {
  const motivo = prompt(`Motivo de desconexión para ${piu.codigo}:`)
  if (motivo) {
    await piuStore.solicitarDesconexion(piu.id, motivo)
  }
}

function abrirModalIncidencia() {
  formIncidencia.value = { piuId: piuStore.pius[0]?.id || null, descripcion: '', prioridad: 'media' }
  modalIncidencia.value = true
}

async function crearIncidencia() {
  const piu = piuStore.pius.find(p => p.id === formIncidencia.value.piuId)
  const nueva = {
    ...formIncidencia.value,
    piuCodigo: piu?.codigo || 'N/A',
    estado: 'abierta',
    fechaReporte: new Date().toISOString().split('T')[0]
  }
  try {
    await piuService.crearIncidencia(nueva)
  } catch (_) {
    // mock
  }
  piuStore.incidencias.push({ ...nueva, id: Date.now() })
  modalIncidencia.value = false
}

function verIncidencia(inc) {
  alert(`Incidencia: ${inc.descripcion}\nEstado: ${inc.estado}\nFecha: ${inc.fechaReporte}`)
}

function cerrarIncidencia(inc) {
  if (confirm(`¿Cerrar la incidencia del PIU ${inc.piuCodigo}?`)) {
    const idx = piuStore.incidencias.findIndex(i => i.id === inc.id)
    if (idx !== -1) piuStore.incidencias[idx].estado = 'cerrada'
  }
}

onMounted(async () => {
  await piuStore.cargarPIUs()
  await piuStore.cargarIncidencias()
})
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

.section-title {
  color: var(--color-primary);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.piu-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.piu-card {
  border-top: 4px solid var(--color-border);
}

.piu-card.estado-activo { border-top-color: var(--color-success); }
.piu-card.estado-inactivo { border-top-color: var(--color-danger); }
.piu-card.estado-mantenimiento { border-top-color: var(--color-warning); }

.piu-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.4rem;
}

.piu-codigo {
  font-weight: 700;
  color: var(--color-primary);
}

.piu-estado-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  flex-shrink: 0;
}

.dot-activo { background-color: var(--color-success); }
.dot-inactivo { background-color: var(--color-danger); }
.dot-mantenimiento { background-color: var(--color-warning); }

.piu-ubicacion {
  font-size: 0.82rem;
  color: var(--color-text-light);
  margin-bottom: 0.25rem;
}

.piu-estado-label {
  font-size: 0.85rem;
  font-weight: 500;
  margin-bottom: 0.25rem;
}

.piu-ultima-actividad {
  font-size: 0.78rem;
  color: var(--color-text-light);
  margin-bottom: 0.5rem;
}

.incidencias-section {
  margin-top: 1.5rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.table-container {
  overflow-x: auto;
}

.acciones-cell {
  display: flex;
  gap: 0.4rem;
}

.btn-sm {
  padding: 0.25rem 0.6rem;
  font-size: 0.8rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: 500;
}

.btn-sm.btn-secondary { background-color: var(--color-secondary); color: white; }
.btn-sm.btn-danger { background-color: var(--color-danger); color: white; }
.btn-sm.btn-outline { background-color: transparent; color: var(--color-primary); border: 1px solid var(--color-primary); }
</style>

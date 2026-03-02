<template>
  <div class="page-content">
    <h1 class="page-title">Validación de Contenido</h1>
    <p class="page-desc">Revise y apruebe o rechace los contenidos enviados a validación.</p>

    <LoadingSpinner v-if="contenidoStore.loading" />

    <div v-else class="validacion-layout">
      <!-- Lista de pendientes -->
      <div class="pendientes-lista">
        <h2 class="section-title">Pendientes de Validación</h2>

        <div v-if="pendientes.length === 0" class="card no-results">
          <p>✅ No hay contenidos pendientes de validación.</p>
        </div>

        <div
          v-for="c in pendientes"
          :key="c.id"
          class="pendiente-item card"
          :class="{ activo: seleccionado?.id === c.id }"
          role="button"
          tabindex="0"
          @click="seleccionar(c)"
          @keydown.enter="seleccionar(c)"
          :aria-selected="seleccionado?.id === c.id"
        >
          <h3>{{ c.titulo }}</h3>
          <div class="item-meta">
            <span class="meta-info">{{ c.tipo }}</span>
            <span class="meta-info">Por: {{ c.autor }}</span>
            <span class="meta-info">{{ c.fechaCreacion }}</span>
          </div>
        </div>
      </div>

      <!-- Preview -->
      <div v-if="seleccionado" class="preview-panel card">
        <h2 class="preview-titulo">{{ seleccionado.titulo }}</h2>
        <div class="preview-meta">
          <span class="badge badge-en_validacion">En Validación</span>
          <span class="meta-info">Tipo: {{ seleccionado.tipo }}</span>
          <span class="meta-info">Autor: {{ seleccionado.autor }}</span>
          <span class="meta-info">Fecha: {{ seleccionado.fechaCreacion }}</span>
        </div>

        <div class="preview-contenido">
          <p>{{ seleccionado.cuerpo || '(Sin contenido)' }}</p>
        </div>

        <div v-if="mensajeAccion" class="alert" :class="mensajeAccion.tipo === 'error' ? 'alert-error' : 'alert-success'">
          {{ mensajeAccion.texto }}
        </div>

        <!-- Botones de acción -->
        <div class="acciones-validacion">
          <button
            class="btn-primary"
            @click="aprobar"
            :disabled="procesando"
            aria-label="Aprobar contenido"
          >
            ✅ Aprobar
          </button>
          <button
            class="btn-danger"
            @click="mostrarRechazo = true"
            :disabled="procesando"
            aria-label="Rechazar contenido"
          >
            ❌ Rechazar
          </button>
        </div>

        <!-- Formulario de rechazo -->
        <div v-if="mostrarRechazo" class="rechazo-form">
          <div class="form-group">
            <label for="motivo-rechazo">Motivo de rechazo <span aria-hidden="true">*</span></label>
            <textarea
              id="motivo-rechazo"
              v-model="motivoRechazo"
              class="form-control"
              rows="3"
              required
              placeholder="Explique el motivo del rechazo..."
              aria-required="true"
            ></textarea>
          </div>
          <div class="rechazo-acciones">
            <button class="btn-outline" @click="mostrarRechazo = false; motivoRechazo = ''">Cancelar</button>
            <button class="btn-danger" @click="rechazar" :disabled="!motivoRechazo.trim() || procesando">
              Confirmar Rechazo
            </button>
          </div>
        </div>
      </div>

      <div v-else-if="pendientes.length > 0" class="placeholder-panel card">
        <p>← Seleccione un contenido para revisarlo.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useContenidoStore } from '../../stores/contenido.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'

const contenidoStore = useContenidoStore()
const seleccionado = ref(null)
const motivoRechazo = ref('')
const mostrarRechazo = ref(false)
const procesando = ref(false)
const mensajeAccion = ref(null)

const pendientes = computed(() => contenidoStore.contenidos.filter(c => c.estado === 'en_validacion'))

function seleccionar(c) {
  seleccionado.value = c
  mostrarRechazo.value = false
  motivoRechazo.value = ''
  mensajeAccion.value = null
}

async function aprobar() {
  if (!seleccionado.value) return
  procesando.value = true
  mensajeAccion.value = null
  try {
    await contenidoStore.aprobarContenido(seleccionado.value.id)
    mensajeAccion.value = { tipo: 'success', texto: 'Contenido aprobado y publicado exitosamente.' }
    setTimeout(() => {
      seleccionado.value = null
      mensajeAccion.value = null
    }, 1500)
  } catch (_) {
    mensajeAccion.value = { tipo: 'error', texto: 'Error al aprobar el contenido.' }
  } finally {
    procesando.value = false
  }
}

async function rechazar() {
  if (!seleccionado.value || !motivoRechazo.value.trim()) return
  procesando.value = true
  mensajeAccion.value = null
  try {
    await contenidoStore.rechazarContenido(seleccionado.value.id, motivoRechazo.value)
    mensajeAccion.value = { tipo: 'success', texto: 'Contenido rechazado. Se notificó al autor.' }
    mostrarRechazo.value = false
    motivoRechazo.value = ''
    setTimeout(() => {
      seleccionado.value = null
      mensajeAccion.value = null
    }, 1500)
  } catch (_) {
    mensajeAccion.value = { tipo: 'error', texto: 'Error al rechazar el contenido.' }
  } finally {
    procesando.value = false
  }
}

onMounted(() => contenidoStore.cargarContenidos())
</script>

<style scoped>
.page-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 0.25rem;
}

.page-desc {
  color: var(--color-text-light);
  font-size: 0.9rem;
  margin-bottom: 1.5rem;
}

.section-title {
  color: var(--color-primary);
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: 0.75rem;
}

.validacion-layout {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 1.25rem;
  align-items: start;
}

.pendientes-lista {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.pendiente-item {
  cursor: pointer;
  border-left: 3px solid transparent;
  transition: all var(--transition);
}

.pendiente-item:hover {
  border-left-color: var(--color-secondary);
}

.pendiente-item.activo {
  border-left-color: var(--color-primary);
  background-color: rgba(0, 51, 102, 0.04);
}

.pendiente-item h3 {
  color: var(--color-primary);
  font-size: 0.95rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
}

.item-meta {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.meta-info {
  font-size: 0.78rem;
  color: var(--color-text-light);
}

.preview-titulo {
  color: var(--color-primary);
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 0.75rem;
}

.preview-meta {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  align-items: center;
  margin-bottom: 1.25rem;
}

.preview-contenido {
  background-color: var(--color-bg);
  border-radius: var(--border-radius);
  padding: 1rem;
  margin-bottom: 1.25rem;
}

.preview-contenido p {
  line-height: 1.7;
  color: var(--color-text);
}

.acciones-validacion {
  display: flex;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.rechazo-form {
  border-top: 1px solid var(--color-border);
  padding-top: 1rem;
}

.rechazo-acciones {
  display: flex;
  gap: 0.75rem;
  justify-content: flex-end;
  margin-top: 0.75rem;
}

.no-results {
  text-align: center;
  padding: 2rem;
  color: var(--color-text-light);
}

.placeholder-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 200px;
  color: var(--color-text-light);
}

@media (max-width: 768px) {
  .validacion-layout {
    grid-template-columns: 1fr;
  }
}
</style>

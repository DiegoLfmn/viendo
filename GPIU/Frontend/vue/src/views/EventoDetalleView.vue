<template>
  <div class="page-content">
    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargar" />

    <div v-else-if="evento">
      <div class="evento-header">
        <button class="btn-volver btn-outline" @click="router.push('/eventos')" aria-label="Volver a eventos">
          ← Volver
        </button>
        <span class="badge" :class="evento.estaVigente ? 'badge-vigente' : 'badge-pasado'">
          {{ evento.estaVigente ? 'Vigente' : 'Finalizado' }}
        </span>
      </div>

      <div class="card evento-detalle">
        <h1 class="evento-titulo">{{ evento.titulo }}</h1>

        <div class="evento-meta-grid">
          <div class="meta-item">
            <span class="meta-label">📅 Fecha</span>
            <span class="meta-value">{{ formatFecha(evento.fecha) }}</span>
          </div>
          <div class="meta-item" v-if="evento.horaInicio">
            <span class="meta-label">🕐 Horario</span>
            <span class="meta-value">{{ evento.horaInicio }}{{ evento.horaFin ? ` — ${evento.horaFin}` : '' }}</span>
          </div>
          <div class="meta-item" v-if="evento.sala">
            <span class="meta-label">📍 Sala</span>
            <span class="meta-value">{{ evento.sala }}</span>
          </div>
          <div class="meta-item" v-if="evento.edificio">
            <span class="meta-label">🏢 Edificio</span>
            <span class="meta-value">{{ evento.edificio }}</span>
          </div>
          <div class="meta-item" v-if="evento.campus">
            <span class="meta-label">🗺 Campus</span>
            <span class="meta-value">{{ evento.campus }}</span>
          </div>
          <div class="meta-item" v-if="evento.organizador">
            <span class="meta-label">🏛 Organizador</span>
            <span class="meta-value">{{ evento.organizador }}</span>
          </div>
        </div>

        <div v-if="evento.descripcion" class="evento-descripcion">
          <h2>Descripción</h2>
          <p>{{ evento.descripcion }}</p>
        </div>

        <div class="evento-acciones">
          <button
            v-if="evento.sala"
            class="btn-primary"
            @click="verRuta"
            aria-label="Ver ruta al evento"
          >
            🗺 Ver ruta al evento
          </button>
          <button class="btn-outline" @click="router.push('/eventos')" aria-label="Volver a la lista de eventos">
            ← Volver a Eventos
          </button>
        </div>
      </div>
    </div>

    <div v-else class="card">
      <p>No se encontró el evento solicitado.</p>
      <button class="btn-secondary" @click="router.push('/eventos')">Volver a Eventos</button>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useEventosStore } from '../stores/eventos.js'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'

const route = useRoute()
const router = useRouter()
const eventosStore = useEventosStore()

const loading = computed(() => eventosStore.loading)
const error = computed(() => eventosStore.error)
const evento = computed(() => eventosStore.eventoActual)

function formatFecha(fecha) {
  if (!fecha) return ''
  return new Date(fecha + 'T00:00:00').toLocaleDateString('es-CL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

function verRuta() {
  router.push(`/ruta/${route.params.id}`)
}

async function cargar() {
  await eventosStore.cargarEventoDetalle(route.params.id)
}

onMounted(cargar)
</script>

<style scoped>
.evento-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.25rem;
}

.btn-volver {
  font-size: 0.9rem;
}

.evento-detalle {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.evento-titulo {
  color: var(--color-primary);
  font-size: 1.75rem;
  font-weight: 700;
  line-height: 1.3;
}

.evento-meta-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 0.75rem;
}

.meta-item {
  display: flex;
  flex-direction: column;
  gap: 0.15rem;
}

.meta-label {
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--color-text-light);
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.meta-value {
  font-size: 0.95rem;
  color: var(--color-text);
}

.evento-descripcion h2 {
  color: var(--color-primary);
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.evento-descripcion p {
  color: var(--color-text);
  line-height: 1.7;
}

.evento-acciones {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  padding-top: 0.5rem;
  border-top: 1px solid var(--color-border);
}
</style>

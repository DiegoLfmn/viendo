<template>
  <div class="page-content">
    <div class="ruta-header">
      <button class="btn-outline" @click="router.back()" aria-label="Volver">← Volver</button>
      <h1 class="page-title">Ruta al Destino</h1>
    </div>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargarRuta" />

    <div v-else class="ruta-layout">
      <!-- Info de la ruta -->
      <div class="card ruta-info">
        <h2>Información de la Ruta</h2>
        <div class="ruta-meta">
          <div class="meta-row">
            <span class="meta-label">📍 Origen:</span>
            <span>{{ ruta.origen || 'PIU - Punto de Información' }}</span>
          </div>
          <div class="meta-row">
            <span class="meta-label">🎯 Destino:</span>
            <span>{{ ruta.destino || `Sala ${destinoId}` }}</span>
          </div>
          <div class="meta-row" v-if="ruta.distancia">
            <span class="meta-label">📏 Distancia:</span>
            <span>{{ ruta.distancia }} metros</span>
          </div>
          <div class="meta-row" v-if="ruta.tiempo">
            <span class="meta-label">⏱ Tiempo estimado:</span>
            <span>{{ ruta.tiempo }} minutos</span>
          </div>
        </div>

        <!-- Botones de acción -->
        <div class="ruta-acciones">
          <button
            class="btn-primary"
            :class="{ activa: modoAccesible }"
            @click="toggleAccesible"
            aria-label="Ruta accesible para personas con movilidad reducida"
          >
            ♿ Ruta Accesible
          </button>
          <button class="btn-secondary" @click="generarQR" aria-label="Generar código QR">
            📱 Generar QR
          </button>
        </div>

        <div v-if="qrVisible" class="qr-section card">
          <p class="qr-label">Código QR (URL de la ruta):</p>
          <code class="qr-url">{{ rutaURL }}</code>
        </div>
      </div>

      <!-- Mapa SVG simplificado -->
      <div class="card mapa-container">
        <h2>Mapa del Campus</h2>
        <svg
          class="mapa-svg"
          viewBox="0 0 400 300"
          role="img"
          aria-label="Mapa simplificado del campus con ruta marcada"
        >
          <!-- Fondo -->
          <rect width="400" height="300" fill="#f0f4f8" rx="8" />

          <!-- Edificios -->
          <rect x="20" y="30" width="80" height="50" fill="#cfd8dc" stroke="#90a4ae" stroke-width="1" rx="4" />
          <text x="60" y="60" text-anchor="middle" font-size="9" fill="#37474f">Biblioteca</text>

          <rect x="150" y="30" width="80" height="50" fill="#cfd8dc" stroke="#90a4ae" stroke-width="1" rx="4" />
          <text x="190" y="60" text-anchor="middle" font-size="9" fill="#37474f">Edif. A</text>

          <rect x="300" y="30" width="80" height="50" fill="#cfd8dc" stroke="#90a4ae" stroke-width="1" rx="4" />
          <text x="340" y="60" text-anchor="middle" font-size="9" fill="#37474f">Auditorio</text>

          <rect x="20" y="150" width="80" height="50" fill="#cfd8dc" stroke="#90a4ae" stroke-width="1" rx="4" />
          <text x="60" y="180" text-anchor="middle" font-size="9" fill="#37474f">Ingeniería</text>

          <rect x="150" y="150" width="80" height="50" fill="#cfd8dc" stroke="#90a4ae" stroke-width="1" rx="4" />
          <text x="190" y="180" text-anchor="middle" font-size="9" fill="#37474f">Edif. B</text>

          <rect x="300" y="150" width="80" height="50" fill="#b3e5fc" stroke="#0288d1" stroke-width="2" rx="4" />
          <text x="340" y="175" text-anchor="middle" font-size="9" fill="#01579b" font-weight="bold">DESTINO</text>
          <text x="340" y="187" text-anchor="middle" font-size="8" fill="#01579b">Sala {{ destinoId }}</text>

          <!-- Caminos principales -->
          <line x1="100" y1="55" x2="150" y2="55" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="230" y1="55" x2="300" y2="55" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="100" y1="175" x2="150" y2="175" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="230" y1="175" x2="300" y2="175" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="60" y1="80" x2="60" y2="150" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="190" y1="80" x2="190" y2="150" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />
          <line x1="340" y1="80" x2="340" y2="150" stroke="#b0bec5" stroke-width="8" stroke-linecap="round" />

          <!-- Ruta marcada en azul -->
          <polyline
            points="60,250 60,210 60,175 100,175 150,175 230,175 300,175 340,175 340,150"
            fill="none"
            stroke="#0066cc"
            stroke-width="3"
            stroke-dasharray="8,4"
            stroke-linecap="round"
            stroke-linejoin="round"
          />

          <!-- Origen (PIU) -->
          <circle cx="60" cy="250" r="10" fill="#28a745" />
          <text x="60" y="253" text-anchor="middle" font-size="8" fill="white" font-weight="bold">PIU</text>
          <text x="60" y="270" text-anchor="middle" font-size="8" fill="#28a745">Inicio</text>

          <!-- Destino -->
          <circle cx="340" cy="165" r="8" fill="#dc3545" />
          <text x="340" y="168" text-anchor="middle" font-size="8" fill="white" font-weight="bold">D</text>

          <!-- Flecha de dirección -->
          <polygon points="340,148 336,156 344,156" fill="#0066cc" />
        </svg>
        <p class="mapa-leyenda">
          <span class="leyenda-item"><span class="dot verde"></span> Origen (PIU)</span>
          <span class="leyenda-item"><span class="dot rojo"></span> Destino</span>
          <span class="leyenda-item"><span class="linea-azul"></span> Ruta</span>
        </p>
      </div>

      <!-- Instrucciones paso a paso -->
      <div class="card instrucciones">
        <h2>Instrucciones</h2>
        <ol class="pasos-lista">
          <li v-for="(paso, idx) in ruta.instrucciones" :key="idx">{{ paso }}</li>
          <template v-if="!ruta.instrucciones?.length">
            <li>Desde el PIU, avanzar hacia el norte por el pasillo principal.</li>
            <li>Girar a la derecha en el cruce de Edificio A.</li>
            <li>Continuar por el corredor este hasta el Edificio B.</li>
            <li>Subir al piso 3 por el ascensor o las escaleras.</li>
            <li>La Sala {{ destinoId }} se encuentra al final del corredor.</li>
          </template>
        </ol>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'
import infoPrivadaService from '../services/infoPrivadaService.js'

const route = useRoute()
const router = useRouter()

const destinoId = computed(() => route.params.destinoId)
const loading = ref(false)
const error = ref(null)
const ruta = ref({})
const modoAccesible = ref(false)
const qrVisible = ref(false)

const rutaURL = computed(() => `${window.location.origin}/ruta/${destinoId.value}`)

async function cargarRuta() {
  loading.value = true
  error.value = null
  try {
    const response = await infoPrivadaService.getRuta(destinoId.value)
    ruta.value = response.data
  } catch (_) {
    ruta.value = {
      origen: 'PIU - Biblioteca Central',
      destino: `Sala ${destinoId.value}`,
      distancia: 185,
      tiempo: 3,
      instrucciones: null
    }
  } finally {
    loading.value = false
  }
}

async function toggleAccesible() {
  modoAccesible.value = !modoAccesible.value
  loading.value = true
  try {
    const response = await infoPrivadaService.getRuta(`${destinoId.value}?accesible=${modoAccesible.value}`)
    ruta.value = response.data
  } catch (_) {
    if (modoAccesible.value) {
      ruta.value = {
        ...ruta.value,
        instrucciones: [
          'Desde el PIU, avanzar hacia el norte usando la rampa accesible.',
          'Tomar el ascensor en el Edificio Central, ir al piso 3.',
          'Avanzar por el corredor accesible señalizado hasta el Edificio B.',
          `La Sala ${destinoId.value} está al final del corredor accesible.`
        ],
        distancia: 220,
        tiempo: 4
      }
    }
  } finally {
    loading.value = false
  }
}

function generarQR() {
  qrVisible.value = !qrVisible.value
}

onMounted(cargarRuta)
</script>

<style scoped>
.ruta-header {
  display: flex;
  align-items: center;
  gap: 1.25rem;
  margin-bottom: 1.25rem;
}

.page-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0;
}

.ruta-layout {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  gap: 1.25rem;
}

.ruta-info {
  grid-column: 1;
}

.mapa-container {
  grid-column: 2;
  grid-row: 1 / 3;
}

.instrucciones {
  grid-column: 1;
}

.ruta-meta {
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  margin-bottom: 1.25rem;
}

.meta-row {
  display: flex;
  gap: 0.5rem;
  font-size: 0.95rem;
}

.meta-label {
  font-weight: 600;
  color: var(--color-text-light);
  min-width: 130px;
}

.ruta-acciones {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.qr-section {
  margin-top: 1rem;
  background-color: var(--color-bg);
}

.qr-label {
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
}

.qr-url {
  font-family: monospace;
  font-size: 0.8rem;
  word-break: break-all;
  color: var(--color-primary);
}

.mapa-svg {
  width: 100%;
  height: auto;
  border-radius: var(--border-radius);
}

.mapa-leyenda {
  display: flex;
  gap: 1rem;
  margin-top: 0.5rem;
  font-size: 0.8rem;
  color: var(--color-text-light);
  flex-wrap: wrap;
}

.leyenda-item {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
}

.dot.verde { background-color: #28a745; }
.dot.rojo { background-color: #dc3545; }

.linea-azul {
  display: inline-block;
  width: 20px;
  height: 3px;
  background-color: #0066cc;
  border-radius: 2px;
}

.pasos-lista {
  padding-left: 1.25rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.pasos-lista li {
  font-size: 0.9rem;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .ruta-layout {
    grid-template-columns: 1fr;
  }

  .mapa-container {
    grid-column: 1;
    grid-row: auto;
  }

  .ruta-header {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>

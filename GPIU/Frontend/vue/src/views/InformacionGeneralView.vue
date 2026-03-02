<template>
  <div class="page-content">
    <h1 class="page-title">Información General de la Universidad</h1>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargar" />

    <div v-else class="info-sections">
      <div class="card info-section">
        <h2>🏛 Historia</h2>
        <p v-if="info.historia">{{ info.historia }}</p>
        <p v-else>
          Fundada en 1947, nuestra universidad ha sido pionera en la educación superior de Chile,
          formando profesionales comprometidos con el desarrollo del país. A lo largo de décadas
          de trayectoria, ha consolidado su posición como referente académico nacional e internacional.
        </p>
      </div>

      <div class="card info-section">
        <h2>🗂 Estructura Organizacional</h2>
        <div v-if="info.estructura">{{ info.estructura }}</div>
        <div v-else class="org-chart">
          <div class="org-item nivel-1">Rector</div>
          <div class="org-row">
            <div class="org-item nivel-2">Vicerrector Académico</div>
            <div class="org-item nivel-2">Vicerrector de Administración</div>
            <div class="org-item nivel-2">Vicerrector de Investigación</div>
          </div>
          <div class="org-row">
            <div class="org-item nivel-3">Facultad de Ingeniería</div>
            <div class="org-item nivel-3">Facultad de Ciencias</div>
            <div class="org-item nivel-3">Facultad de Humanidades</div>
            <div class="org-item nivel-3">Facultad de Medicina</div>
          </div>
        </div>
      </div>

      <div class="card info-section">
        <h2>🗺 Distribución de Campus</h2>
        <div v-if="info.campus">{{ info.campus }}</div>
        <div v-else class="campus-grid">
          <div class="campus-card" v-for="campus in campusMock" :key="campus.nombre">
            <h3>{{ campus.nombre }}</h3>
            <p>{{ campus.descripcion }}</p>
            <ul>
              <li v-for="edificio in campus.edificios" :key="edificio">{{ edificio }}</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'
import contenidoService from '../services/contenidoService.js'

const loading = ref(false)
const error = ref(null)
const info = ref({})

const campusMock = [
  {
    nombre: 'Campus Principal',
    descripcion: 'Sede central de la universidad, ubicada en el centro de la ciudad.',
    edificios: ['Edificio Central (A)', 'Biblioteca Central', 'Casino Central', 'Auditorio Principal']
  },
  {
    nombre: 'Campus Norte',
    descripcion: 'Campus dedicado principalmente a las ciencias e ingeniería.',
    edificios: ['Edificio de Ingeniería', 'Laboratorios de Ciencias', 'Centro de Cómputo']
  },
  {
    nombre: 'Campus Sur',
    descripcion: 'Campus de ciencias de la salud y medicina.',
    edificios: ['Facultad de Medicina', 'Hospital Clínico Universitario', 'Instituto de Ciencias Biomédicas']
  }
]

async function cargar() {
  loading.value = true
  error.value = null
  try {
    const response = await contenidoService.getInformacionGeneral()
    info.value = response.data || {}
  } catch (_) {
    info.value = {}
  } finally {
    loading.value = false
  }
}

onMounted(cargar)
</script>

<style scoped>
.page-title {
  color: var(--color-primary);
  font-size: 1.75rem;
  font-weight: 700;
  margin-bottom: 1.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 3px solid var(--color-accent);
}

.info-sections {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.info-section h2 {
  color: var(--color-primary);
  font-size: 1.2rem;
  margin-bottom: 1rem;
}

.info-section p {
  line-height: 1.7;
  color: var(--color-text);
}

.org-chart {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.org-row {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
  justify-content: center;
}

.org-item {
  padding: 0.5rem 1rem;
  border-radius: var(--border-radius);
  font-size: 0.9rem;
  font-weight: 500;
}

.nivel-1 {
  background-color: var(--color-primary);
  color: var(--color-white);
}

.nivel-2 {
  background-color: var(--color-secondary);
  color: var(--color-white);
}

.nivel-3 {
  background-color: rgba(0, 102, 204, 0.15);
  color: var(--color-primary);
  border: 1px solid rgba(0, 102, 204, 0.3);
}

.campus-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1rem;
}

.campus-card {
  background-color: var(--color-bg);
  border-radius: var(--border-radius);
  padding: 1rem;
  border-left: 4px solid var(--color-secondary);
}

.campus-card h3 {
  color: var(--color-primary);
  font-size: 0.95rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
}

.campus-card p {
  color: var(--color-text-light);
  font-size: 0.85rem;
  margin-bottom: 0.5rem;
}

.campus-card ul {
  list-style: none;
  padding: 0;
}

.campus-card ul li {
  font-size: 0.85rem;
  color: var(--color-text);
  padding: 0.15rem 0;
}

.campus-card ul li::before {
  content: '▸ ';
  color: var(--color-secondary);
}
</style>

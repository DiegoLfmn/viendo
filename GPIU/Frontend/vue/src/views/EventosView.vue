<template>
  <div class="page-content">
    <h1 class="page-title">Eventos Universitarios</h1>

    <!-- Barra de búsqueda y filtros -->
    <div class="filters-bar card">
      <div class="search-row">
        <input
          v-model="textoBusqueda"
          type="search"
          class="form-control search-input"
          placeholder="Buscar por nombre, descripción u organizador..."
          aria-label="Buscar eventos"
          @input="filtrar"
        />
        <button class="btn-primary" @click="filtrar" aria-label="Buscar">
          🔍 Buscar
        </button>
      </div>

      <div class="filters-row">
        <div class="filter-group">
          <label for="filtro-fecha">Fecha</label>
          <input
            id="filtro-fecha"
            v-model="filtroFecha"
            type="date"
            class="form-control"
            @change="filtrar"
            aria-label="Filtrar por fecha"
          />
        </div>

        <div class="filter-group">
          <label for="filtro-campus">Campus</label>
          <select id="filtro-campus" v-model="filtroCampus" class="form-control" @change="filtrar" aria-label="Filtrar por campus">
            <option value="">Todos los campus</option>
            <option value="Campus Principal">Campus Principal</option>
            <option value="Campus Norte">Campus Norte</option>
            <option value="Campus Sur">Campus Sur</option>
          </select>
        </div>

        <div class="filter-group">
          <label for="filtro-organizador">Organizador</label>
          <select id="filtro-organizador" v-model="filtroOrganizador" class="form-control" @change="filtrar" aria-label="Filtrar por organizador">
            <option value="">Todas las unidades</option>
            <option value="Dirección de Docencia">Dirección de Docencia</option>
            <option value="Centro de Estudiantes">Centro de Estudiantes</option>
            <option value="Facultad de Ingeniería">Facultad de Ingeniería</option>
            <option value="Departamento de Informática">Departamento de Informática</option>
          </select>
        </div>

        <div class="filter-group">
          <label for="filtro-orden">Ordenar por</label>
          <select id="filtro-orden" v-model="orden" class="form-control" @change="filtrar" aria-label="Ordenar eventos">
            <option value="fecha_asc">Fecha ascendente</option>
            <option value="fecha_desc">Fecha descendente</option>
            <option value="nombre_az">Nombre A-Z</option>
          </select>
        </div>

        <button class="btn-outline btn-limpiar" @click="limpiarFiltros" aria-label="Limpiar filtros">
          Limpiar filtros
        </button>
      </div>
    </div>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargar" />

    <div v-else>
      <!-- Sin resultados -->
      <div v-if="eventosFiltrados.length === 0" class="no-results card">
        <p>No hay resultados para los filtros seleccionados.</p>
        <button class="btn-secondary" @click="limpiarFiltros">Limpiar filtros</button>
      </div>

      <!-- Grid de eventos -->
      <div v-else class="eventos-grid">
        <EventCard
          v-for="evento in eventosFiltrados"
          :key="evento.idEvento"
          :evento="evento"
          @click="irDetalle"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useEventosStore } from '../stores/eventos.js'
import EventCard from '../components/EventCard.vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'

const router = useRouter()
const eventosStore = useEventosStore()

const textoBusqueda = ref('')
const filtroFecha = ref('')
const filtroCampus = ref('')
const filtroOrganizador = ref('')
const orden = ref('fecha_asc')

const loading = computed(() => eventosStore.loading)
const error = computed(() => eventosStore.error)

const eventosFiltrados = computed(() => {
  let lista = [...eventosStore.eventos]

  if (textoBusqueda.value.trim()) {
    const txt = textoBusqueda.value.toLowerCase()
    lista = lista.filter(e =>
      (e.titulo || '').toLowerCase().includes(txt) ||
      (e.descripcion || '').toLowerCase().includes(txt) ||
      (e.organizador || '').toLowerCase().includes(txt)
    )
  }

  if (filtroFecha.value) {
    lista = lista.filter(e => e.fecha === filtroFecha.value)
  }

  if (filtroCampus.value) {
    lista = lista.filter(e => e.campus === filtroCampus.value)
  }

  if (filtroOrganizador.value) {
    lista = lista.filter(e => e.organizador === filtroOrganizador.value)
  }

  if (orden.value === 'fecha_asc') {
    lista.sort((a, b) => a.fecha < b.fecha ? -1 : 1)
  } else if (orden.value === 'fecha_desc') {
    lista.sort((a, b) => a.fecha > b.fecha ? -1 : 1)
  } else if (orden.value === 'nombre_az') {
    lista.sort((a, b) => a.titulo.localeCompare(b.titulo))
  }

  return lista
})

async function cargar() {
  await eventosStore.cargarEventos()
}

function filtrar() {
  // El filtrado es reactivo, no necesita acción adicional
}

function limpiarFiltros() {
  textoBusqueda.value = ''
  filtroFecha.value = ''
  filtroCampus.value = ''
  filtroOrganizador.value = ''
  orden.value = 'fecha_asc'
}

function irDetalle(evento) {
  router.push(`/eventos/${evento.idEvento}`)
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

.filters-bar {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.search-row {
  display: flex;
  gap: 0.75rem;
}

.search-input {
  flex: 1;
}

.filters-row {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  align-items: flex-end;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  min-width: 160px;
}

.filter-group label {
  font-size: 0.8rem;
  font-weight: 500;
  color: var(--color-text-light);
}

.btn-limpiar {
  height: fit-content;
  margin-top: auto;
}

.no-results {
  text-align: center;
  padding: 2.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  color: var(--color-text-light);
}

.eventos-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.25rem;
}

@media (max-width: 768px) {
  .search-row {
    flex-direction: column;
  }

  .filters-row {
    flex-direction: column;
  }

  .filter-group {
    min-width: 100%;
  }
}
</style>

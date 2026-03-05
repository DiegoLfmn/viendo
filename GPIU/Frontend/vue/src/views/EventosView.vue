<template>
  <div class="page-content">
<<<<<<< HEAD
    <h1 class="page-title">Eventos Universitarios</h1>
=======
    <h1 class="page-title">Eventos y Noticias</h1>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

    <!-- Barra de búsqueda y filtros -->
    <div class="filters-bar card">
      <div class="search-row">
        <input
          v-model="textoBusqueda"
          type="search"
          class="form-control search-input"
<<<<<<< HEAD
          placeholder="Buscar por nombre, descripción u organizador..."
          aria-label="Buscar eventos"
          @input="filtrar"
        />
        <button class="btn-primary" @click="filtrar" aria-label="Buscar">
          🔍 Buscar
        </button>
=======
          placeholder="Buscar por título o descripción..."
          aria-label="Buscar contenidos"
        />
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      </div>

      <div class="filters-row">
        <div class="filter-group">
<<<<<<< HEAD
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
=======
          <label for="filtro-tipo">Tipo</label>
          <select id="filtro-tipo" v-model="filtroTipo" class="form-control" aria-label="Filtrar por tipo">
            <option value="">Todos</option>
            <option value="noticia">Noticia</option>
            <option value="evento">Evento</option>
            <option value="proceso">Proceso</option>
            <option value="info-general">Info General</option>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          </select>
        </div>

        <div class="filter-group">
<<<<<<< HEAD
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
=======
          <label for="filtro-categoria">Categoría</label>
          <select id="filtro-categoria" v-model="filtroCategoria" class="form-control" aria-label="Filtrar por categoría">
            <option value="">Todas</option>
            <option value="matricula">Matrícula</option>
            <option value="examenes">Exámenes</option>
            <option value="normativa">Normativas</option>
            <option value="avisos">Avisos</option>
            <option value="administrativo">Administrativo</option>
            <option value="institucional">Institucional</option>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          </select>
        </div>

        <button class="btn-outline btn-limpiar" @click="limpiarFiltros" aria-label="Limpiar filtros">
          Limpiar filtros
        </button>
      </div>
    </div>

<<<<<<< HEAD
    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargar" />

    <div v-else>
      <!-- Sin resultados -->
      <div v-if="eventosFiltrados.length === 0" class="no-results card">
=======
    <LoadingSpinner v-if="contenidoStore.loading" />
    <ErrorMessage v-else-if="contenidoStore.error" :message="contenidoStore.error" :on-retry="() => contenidoStore.cargarContenidos()" />

    <div v-else>
      <!-- Sin resultados -->
      <div v-if="contenidosFiltrados.length === 0" class="no-results card">
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
        <p>No hay resultados para los filtros seleccionados.</p>
        <button class="btn-secondary" @click="limpiarFiltros">Limpiar filtros</button>
      </div>

<<<<<<< HEAD
      <!-- Grid de eventos -->
      <div v-else class="eventos-grid">
        <EventCard
          v-for="evento in eventosFiltrados"
          :key="evento.idEvento"
          :evento="evento"
          @click="irDetalle"
        />
=======
      <!-- Layout lista + detalle -->
      <div v-else class="contenidos-layout">
        <!-- Lista -->
        <div class="contenidos-lista">
          <div
            v-for="item in contenidosFiltrados"
            :key="item.id"
            class="contenido-item card"
            :class="{ activo: seleccionado?.id === item.id }"
            role="button"
            tabindex="0"
            :aria-selected="seleccionado?.id === item.id"
            @click="seleccionar(item)"
            @keydown.enter="seleccionar(item)"
          >
            <div class="item-header">
              <h3>{{ item.titulo }}</h3>
              <span class="badge tipo-badge" :class="`tipo-${item.tipo}`">{{ tipoLabel(item.tipo) }}</span>
            </div>
            <div class="item-meta">
              <span v-if="item.categoria" class="categoria-tag">{{ item.categoria }}</span>
              <span v-if="item.fechaCreacion" class="fecha">{{ item.fechaCreacion }}</span>
            </div>
          </div>
        </div>

        <!-- Detalle -->
        <div v-if="seleccionado" class="contenido-detalle card">
          <h2>{{ seleccionado.titulo }}</h2>
          <div class="detalle-meta">
            <span class="badge tipo-badge" :class="`tipo-${seleccionado.tipo}`">{{ tipoLabel(seleccionado.tipo) }}</span>
            <span v-if="seleccionado.autorNombre" class="meta-info">Autor: {{ seleccionado.autorNombre }}</span>
            <span v-if="seleccionado.fechaCreacion" class="meta-info">Fecha: {{ seleccionado.fechaCreacion }}</span>
            <span v-if="seleccionado.categoria" class="meta-info">Categoría: {{ seleccionado.categoria }}</span>
          </div>
          <div class="detalle-cuerpo">
            <p>{{ seleccionado.cuerpo || seleccionado.texto }}</p>
          </div>
          <button class="btn-outline" style="margin-top: 1rem;" @click="seleccionado = null">Cerrar detalle</button>
        </div>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
<<<<<<< HEAD
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
=======
import { useContenidoStore } from '../stores/contenido.js'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'

const contenidoStore = useContenidoStore()

const textoBusqueda = ref('')
const filtroTipo = ref('')
const filtroCategoria = ref('')
const seleccionado = ref(null)

const contenidosFiltrados = computed(() => {
  // Mostrar todos los contenidos publicados (noticia, evento, proceso, info-general)
  let lista = contenidoStore.contenidos.filter(c => c.estado === 'publicado')

  if (filtroTipo.value) {
    lista = lista.filter(c => c.tipo === filtroTipo.value)
  }

  if (filtroCategoria.value) {
    lista = lista.filter(c => c.categoria === filtroCategoria.value)
  }

  if (textoBusqueda.value.trim()) {
    const txt = textoBusqueda.value.toLowerCase()
    lista = lista.filter(c =>
      (c.titulo || '').toLowerCase().includes(txt) ||
      (c.cuerpo || c.texto || '').toLowerCase().includes(txt)
    )
  }

  return lista
})

function tipoLabel(tipo) {
  const labels = {
    noticia: 'Noticia',
    evento: 'Evento',
    proceso: 'Proceso',
    'info-general': 'Info General'
  }
  return labels[tipo] || tipo
}

function seleccionar(item) {
  seleccionado.value = seleccionado.value?.id === item.id ? null : item
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
}

function limpiarFiltros() {
  textoBusqueda.value = ''
<<<<<<< HEAD
  filtroFecha.value = ''
  filtroCampus.value = ''
  filtroOrganizador.value = ''
  orden.value = 'fecha_asc'
}

function irDetalle(evento) {
  router.push(`/eventos/${evento.idEvento}`)
}

onMounted(cargar)
=======
  filtroTipo.value = ''
  filtroCategoria.value = ''
}

onMounted(() => contenidoStore.cargarContenidos())
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
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
<<<<<<< HEAD

=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
.filters-bar {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}
<<<<<<< HEAD

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
=======
.search-row { display: flex; gap: 0.75rem; }
.search-input { flex: 1; }
.filters-row { display: flex; gap: 0.75rem; flex-wrap: wrap; align-items: flex-end; }
.filter-group { display: flex; flex-direction: column; gap: 0.25rem; min-width: 160px; }
.filter-group label { font-size: 0.8rem; font-weight: 500; color: var(--color-text-light); }
.btn-limpiar { height: fit-content; margin-top: auto; }
.contenidos-layout { display: grid; grid-template-columns: 1fr 1fr; gap: 1.25rem; }
.contenidos-lista { display: flex; flex-direction: column; gap: 0.75rem; }
.contenido-item {
  cursor: pointer;
  border-left: 3px solid transparent;
  transition: all var(--transition);
}
.contenido-item:hover {
  border-left-color: var(--color-secondary);
  background-color: rgba(0, 102, 204, 0.03);
}
.contenido-item.activo {
  border-left-color: var(--color-primary);
  background-color: rgba(0, 51, 102, 0.05);
}
.item-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}
.item-header h3 { color: var(--color-primary); font-size: 0.95rem; font-weight: 600; }
.item-meta { display: flex; gap: 0.5rem; flex-wrap: wrap; font-size: 0.8rem; }
.tipo-badge { font-size: 0.7rem; white-space: nowrap; }
.tipo-noticia { background-color: #e3f2fd; color: #1565c0; }
.tipo-evento { background-color: #e8f5e9; color: #2e7d32; }
.tipo-proceso { background-color: #fff3e0; color: #e65100; }
.tipo-info-general { background-color: #f3e5f5; color: #6a1b9a; }
.categoria-tag {
  background-color: rgba(0, 102, 204, 0.1);
  color: var(--color-secondary);
  padding: 0.1rem 0.5rem;
  border-radius: 10px;
  text-transform: capitalize;
}
.fecha { color: var(--color-text-light); }
.contenido-detalle h2 { color: var(--color-primary); font-size: 1.15rem; font-weight: 600; margin-bottom: 0.75rem; }
.detalle-meta { display: flex; gap: 0.5rem; flex-wrap: wrap; align-items: center; margin-bottom: 1rem; }
.meta-info { font-size: 0.85rem; color: var(--color-text-light); }
.detalle-cuerpo p { line-height: 1.7; color: var(--color-text); }
.no-results { text-align: center; padding: 2.5rem; display: flex; flex-direction: column; align-items: center; gap: 1rem; color: var(--color-text-light); }
@media (max-width: 768px) {
  .contenidos-layout { grid-template-columns: 1fr; }
  .search-row { flex-direction: column; }
  .filters-row { flex-direction: column; }
  .filter-group { min-width: 100%; }
}
</style>
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

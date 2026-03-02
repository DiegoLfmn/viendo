<template>
  <div class="page-content">
    <h1 class="page-title">Procesos Administrativos</h1>

    <!-- Filtros -->
    <div class="filters-bar card">
      <div class="search-row">
        <input
          v-model="textoBusqueda"
          type="search"
          class="form-control"
          placeholder="Buscar proceso..."
          aria-label="Buscar procesos"
        />
      </div>
      <div class="filters-row">
        <div class="filter-group">
          <label for="filtro-categoria">Categoría</label>
          <select id="filtro-categoria" v-model="filtroCategoria" class="form-control" aria-label="Filtrar por categoría">
            <option value="">Todas</option>
            <option value="matricula">Matrícula</option>
            <option value="examenes">Exámenes</option>
            <option value="normativa">Normativas</option>
            <option value="avisos">Avisos</option>
            <option value="administrativo">Administrativo</option>
          </select>
        </div>
        <button class="btn-outline" @click="limpiarFiltros">Limpiar filtros</button>
      </div>
    </div>

    <LoadingSpinner v-if="loading" />
    <ErrorMessage v-else-if="error" :message="error" :on-retry="cargar" />

    <div v-else class="procesos-layout">
      <!-- Lista -->
      <div class="procesos-lista">
        <div v-if="procesosFiltrados.length === 0" class="card no-results">
          <p>No hay resultados para los filtros seleccionados.</p>
          <button class="btn-secondary" @click="limpiarFiltros">Limpiar filtros</button>
        </div>

        <div
          v-for="proceso in procesosFiltrados"
          :key="proceso.id"
          class="proceso-item card"
          :class="{ activo: procesoSeleccionado?.id === proceso.id }"
          role="button"
          tabindex="0"
          :aria-selected="procesoSeleccionado?.id === proceso.id"
          @click="seleccionarProceso(proceso)"
          @keydown.enter="seleccionarProceso(proceso)"
        >
          <div class="proceso-item-header">
            <h3>{{ proceso.titulo }}</h3>
            <span class="badge" :class="`badge-${proceso.estado}`">{{ estadoLabel(proceso.estado) }}</span>
          </div>
          <div class="proceso-meta">
            <span v-if="proceso.categoria" class="categoria-tag">{{ proceso.categoria }}</span>
            <span v-if="proceso.fechaVigenciaFin" class="vigencia">Vigente hasta: {{ proceso.fechaVigenciaFin }}</span>
          </div>
        </div>
      </div>

      <!-- Detalle -->
      <div v-if="procesoSeleccionado" class="proceso-detalle card">
        <h2>{{ procesoSeleccionado.titulo }}</h2>
        <div class="detalle-meta">
          <span class="badge" :class="`badge-${procesoSeleccionado.estado}`">{{ estadoLabel(procesoSeleccionado.estado) }}</span>
          <span v-if="procesoSeleccionado.autor" class="meta-info">Unidad: {{ procesoSeleccionado.autor }}</span>
          <span v-if="procesoSeleccionado.fechaVigenciaInicio" class="meta-info">
            Vigencia: {{ procesoSeleccionado.fechaVigenciaInicio }}{{ procesoSeleccionado.fechaVigenciaFin ? ` al ${procesoSeleccionado.fechaVigenciaFin}` : '' }}
          </span>
        </div>
        <div class="detalle-cuerpo">
          <p>{{ procesoSeleccionado.cuerpo }}</p>
        </div>
        <button class="btn-outline" style="margin-top: 1rem;" @click="procesoSeleccionado = null">Cerrar detalle</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'
import contenidoService from '../services/contenidoService.js'

const loading = ref(false)
const error = ref(null)
const procesos = ref([])
const textoBusqueda = ref('')
const filtroCategoria = ref('')
const procesoSeleccionado = ref(null)

const procesosFiltrados = computed(() => {
  let lista = [...procesos.value]
  if (textoBusqueda.value.trim()) {
    const txt = textoBusqueda.value.toLowerCase()
    lista = lista.filter(p =>
      (p.titulo || '').toLowerCase().includes(txt) ||
      (p.cuerpo || '').toLowerCase().includes(txt)
    )
  }
  if (filtroCategoria.value) {
    lista = lista.filter(p => p.categoria === filtroCategoria.value)
  }
  return lista
})

function estadoLabel(estado) {
  const labels = {
    publicado: 'Publicado',
    borrador: 'Borrador',
    en_validacion: 'En validación',
    rechazado: 'Rechazado'
  }
  return labels[estado] || estado
}

function seleccionarProceso(proceso) {
  procesoSeleccionado.value = procesoSeleccionado.value?.id === proceso.id ? null : proceso
}

function limpiarFiltros() {
  textoBusqueda.value = ''
  filtroCategoria.value = ''
}

async function cargar() {
  loading.value = true
  error.value = null
  try {
    const response = await contenidoService.getProcesosAdministrativos()
    procesos.value = response.data
  } catch (_) {
    procesos.value = [
      {
        id: 1,
        titulo: 'Proceso de Matrícula 2024',
        cuerpo: 'El proceso de matrícula para el año académico 2024 se realizará entre el 15 y 30 de enero. Los estudiantes deben presentar su documentación en la Dirección de Registro Curricular.',
        estado: 'publicado',
        categoria: 'matricula',
        autor: 'Dirección de Registro Curricular',
        fechaVigenciaInicio: '2024-01-15',
        fechaVigenciaFin: '2024-01-30'
      },
      {
        id: 2,
        titulo: 'Exámenes de Habilitación Profesional',
        cuerpo: 'Convocatoria a exámenes de habilitación profesional para egresados de las carreras de Medicina, Derecho e Ingeniería. Inscripciones abiertas del 5 al 20 de marzo.',
        estado: 'publicado',
        categoria: 'examenes',
        autor: 'Secretaría de Estudios',
        fechaVigenciaInicio: '2024-03-05',
        fechaVigenciaFin: '2024-03-20'
      },
      {
        id: 3,
        titulo: 'Normativa de Uso de Laboratorios 2024',
        cuerpo: 'Se comunica la actualización de las normativas de uso de laboratorios. Todo usuario debe registrarse en el sistema antes de utilizar cualquier instalación.',
        estado: 'publicado',
        categoria: 'normativa',
        autor: 'Dirección de Infraestructura',
        fechaVigenciaInicio: '2024-01-01',
        fechaVigenciaFin: '2024-12-31'
      },
      {
        id: 4,
        titulo: 'Aviso: Cierre Administrativo Semana Santa',
        cuerpo: 'Se informa a la comunidad universitaria que las oficinas administrativas permanecerán cerradas del 28 de marzo al 1 de abril con motivo de Semana Santa.',
        estado: 'publicado',
        categoria: 'avisos',
        autor: 'Dirección General',
        fechaVigenciaInicio: '2024-03-28',
        fechaVigenciaFin: '2024-04-01'
      }
    ]
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

.procesos-layout {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.25rem;
}

.procesos-lista {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.proceso-item {
  cursor: pointer;
  border-left: 3px solid transparent;
  transition: all var(--transition);
}

.proceso-item:hover {
  border-left-color: var(--color-secondary);
  background-color: rgba(0, 102, 204, 0.03);
}

.proceso-item.activo {
  border-left-color: var(--color-primary);
  background-color: rgba(0, 51, 102, 0.05);
}

.proceso-item-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.proceso-item-header h3 {
  color: var(--color-primary);
  font-size: 0.95rem;
  font-weight: 600;
}

.proceso-meta {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  font-size: 0.8rem;
}

.categoria-tag {
  background-color: rgba(0, 102, 204, 0.1);
  color: var(--color-secondary);
  padding: 0.1rem 0.5rem;
  border-radius: 10px;
  text-transform: capitalize;
}

.vigencia {
  color: var(--color-text-light);
}

.proceso-detalle h2 {
  color: var(--color-primary);
  font-size: 1.15rem;
  font-weight: 600;
  margin-bottom: 0.75rem;
}

.detalle-meta {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  align-items: center;
  margin-bottom: 1rem;
}

.meta-info {
  font-size: 0.85rem;
  color: var(--color-text-light);
}

.detalle-cuerpo p {
  line-height: 1.7;
  color: var(--color-text);
}

.no-results {
  text-align: center;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  color: var(--color-text-light);
}

@media (max-width: 768px) {
  .procesos-layout {
    grid-template-columns: 1fr;
  }
}
</style>

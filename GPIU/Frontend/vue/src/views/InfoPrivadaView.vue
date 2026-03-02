<template>
  <div class="page-content">
    <h1 class="page-title">Información Privada</h1>

    <div v-if="!authStore.isAuthenticated" class="card no-auth">
      <p>⚠️ Debe iniciar sesión para acceder a esta sección.</p>
      <RouterLink to="/login" class="btn-primary">Iniciar Sesión</RouterLink>
    </div>

    <div v-else>
      <p class="rol-info">
        Bienvenido, <strong>{{ authStore.userName }}</strong>. Acceso como: <span class="badge badge-vigente">{{ authStore.userRole }}</span>
      </p>

      <!-- Categorías según rol -->
      <div class="categorias-grid">
        <button
          v-for="cat in categorias"
          :key="cat.id"
          class="categoria-btn card"
          :class="{ activa: categoriaActiva === cat.id }"
          @click="seleccionarCategoria(cat)"
          :aria-selected="categoriaActiva === cat.id"
          role="tab"
        >
          <span class="cat-icon" aria-hidden="true">{{ cat.icon }}</span>
          <span>{{ cat.label }}</span>
        </button>
      </div>

      <LoadingSpinner v-if="loading" />
      <ErrorMessage v-else-if="error" :message="error" :on-retry="() => seleccionarCategoria(catActual)" />

      <div v-else-if="datos" class="datos-section">
        <component :is="componenteActual" :datos="datos" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineAsyncComponent } from 'vue'
import { useAuthStore } from '../stores/auth.js'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import ErrorMessage from '../components/ErrorMessage.vue'
import infoPrivadaService from '../services/infoPrivadaService.js'

const authStore = useAuthStore()
const loading = ref(false)
const error = ref(null)
const datos = ref(null)
const categoriaActiva = ref(null)
const catActual = ref(null)

const CATEGORIAS_POR_ROL = {
  ESTUDIANTE: [
    { id: 'malla', label: 'Malla Curricular', icon: '📚' },
    { id: 'matricula', label: 'Estado de Matrícula', icon: '📋' },
    { id: 'horario', label: 'Horario de Clases', icon: '🕐' },
    { id: 'salas', label: 'Ubicación de Salas', icon: '📍' }
  ],
  PROFESOR: [
    { id: 'cursos', label: 'Cursos a Cargo', icon: '📖' },
    { id: 'horario', label: 'Horarios', icon: '🕐' },
    { id: 'salas', label: 'Salas Asignadas', icon: '📍' }
  ],
  FUNCIONARIO: [
    { id: 'agenda', label: 'Agenda Institucional', icon: '📅' },
    { id: 'funciones', label: 'Funciones', icon: '💼' }
  ]
}

const categorias = computed(() => {
  return CATEGORIAS_POR_ROL[authStore.userRole] || []
})

const componenteActual = computed(() => {
  if (!datos.value) return null
  return {
    props: ['datos'],
    template: `
      <div class="datos-container">
        <div v-if="Array.isArray(datos)" class="datos-tabla">
          <div v-for="(item, idx) in datos" :key="idx" class="dato-item card">
            <pre style="white-space:pre-wrap;font-family:inherit">{{ JSON.stringify(item, null, 2) }}</pre>
          </div>
        </div>
        <div v-else class="dato-item card">
          <pre style="white-space:pre-wrap;font-family:inherit">{{ JSON.stringify(datos, null, 2) }}</pre>
        </div>
      </div>
    `
  }
})

const mockDatos = {
  malla: [
    { codigo: 'INF-101', nombre: 'Programación I', creditos: 6, aprobado: true },
    { codigo: 'MAT-201', nombre: 'Cálculo I', creditos: 6, aprobado: true },
    { codigo: 'INF-201', nombre: 'Estructura de Datos', creditos: 6, aprobado: false, cursando: true },
    { codigo: 'INF-301', nombre: 'Bases de Datos', creditos: 6, aprobado: false, pendiente: true }
  ],
  matricula: { estado: 'Regular', año: 2024, semestre: 1, carrera: 'Ingeniería Civil Informática', anoIngreso: 2021 },
  horario: [
    { dia: 'Lunes', hora: '08:30-10:00', asignatura: 'Estructura de Datos', sala: 'Lab 201', edificio: 'Edificio de Informática' },
    { dia: 'Martes', hora: '10:15-11:45', asignatura: 'Bases de Datos', sala: 'Sala 305', edificio: 'Edificio A' },
    { dia: 'Miércoles', hora: '08:30-10:00', asignatura: 'Estructura de Datos', sala: 'Lab 201', edificio: 'Edificio de Informática' }
  ],
  salas: [
    { nombre: 'Lab 201', edificio: 'Edificio de Informática', piso: 2, capacidad: 30, id: 201 },
    { nombre: 'Sala 305', edificio: 'Edificio A', piso: 3, capacidad: 45, id: 305 }
  ],
  cursos: [
    { codigo: 'INF-201', nombre: 'Estructura de Datos', seccion: 1, alumnos: 35, sala: 'Lab 201' },
    { codigo: 'INF-301', nombre: 'Bases de Datos', seccion: 2, alumnos: 28, sala: 'Sala 305' }
  ],
  agenda: [
    { fecha: '2024-03-15', hora: '09:00', descripcion: 'Reunión de Coordinación Departamental', lugar: 'Sala de Reuniones A' },
    { fecha: '2024-03-18', hora: '14:00', descripcion: 'Comité de Evaluación Docente', lugar: 'Auditorio Principal' }
  ],
  funciones: { cargo: 'Administrativo Senior', departamento: 'Dirección de Docencia', funciones: ['Gestión de matrículas', 'Atención de público', 'Registro curricular'] }
}

async function seleccionarCategoria(cat) {
  categoriaActiva.value = cat.id
  catActual.value = cat
  loading.value = true
  error.value = null
  datos.value = null

  try {
    let response
    if (authStore.userRole === 'ESTUDIANTE') {
      response = await infoPrivadaService.getInfoEstudiante()
    } else if (authStore.userRole === 'PROFESOR') {
      response = await infoPrivadaService.getInfoProfesor()
    } else if (authStore.userRole === 'FUNCIONARIO') {
      response = await infoPrivadaService.getInfoFuncionario()
    }
    datos.value = response?.data?.[cat.id] || mockDatos[cat.id]
  } catch (err) {
    if (err.response?.status === 403) {
      error.value = 'No tiene autorización para acceder a esta información.'
    } else {
      datos.value = mockDatos[cat.id] || {}
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (categorias.value.length > 0) {
    seleccionarCategoria(categorias.value[0])
  }
})
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

.no-auth {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  padding: 3rem;
}

.rol-info {
  margin-bottom: 1.25rem;
  font-size: 0.95rem;
}

.categorias-grid {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  margin-bottom: 1.5rem;
}

.categoria-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.4rem;
  padding: 0.75rem 1.25rem;
  cursor: pointer;
  border: 2px solid var(--color-border);
  background-color: var(--color-white);
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--color-text);
  border-radius: var(--border-radius);
  transition: all var(--transition);
  min-width: 130px;
}

.categoria-btn:hover {
  border-color: var(--color-secondary);
  color: var(--color-secondary);
}

.categoria-btn.activa {
  border-color: var(--color-primary);
  background-color: var(--color-primary);
  color: var(--color-white);
}

.cat-icon {
  font-size: 1.5rem;
}

.datos-container {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.dato-item {
  font-size: 0.9rem;
}
</style>

<template>
  <div class="admin-layout">
    <!-- Sidebar -->
    <aside class="admin-sidebar" role="complementary" aria-label="Menú de administración">
      <h2 class="sidebar-title">⚙️ Administración</h2>
      <nav>
        <ul class="sidebar-nav" role="list">
          <li><RouterLink to="/admin" exact-active-class="activo">📊 Dashboard</RouterLink></li>
          <li v-if="authStore.userRole === 'ADMINISTRADOR'">
            <RouterLink to="/admin/pius" active-class="activo">🖥 Gestión PIUs</RouterLink>
          </li>
          <li v-if="authStore.userRole === 'ADMINISTRADOR'">
            <RouterLink to="/admin/usuarios" active-class="activo">👥 Usuarios</RouterLink>
          </li>
          <li><RouterLink to="/admin/monitoreo" active-class="activo">📡 Monitoreo</RouterLink></li>
          <li><RouterLink to="/admin/contenido" active-class="activo">📝 Contenido</RouterLink></li>
<<<<<<< HEAD
          <li v-if="['ADMINISTRADOR', 'GESTOR'].includes(authStore.userRole)">
=======
          <li v-if="authStore.userRole === 'ADMINISTRADOR'">
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
            <RouterLink to="/admin/validacion" active-class="activo">✅ Validación</RouterLink>
          </li>
          <li><RouterLink to="/admin/reportes" active-class="activo">📈 Reportes</RouterLink></li>
        </ul>
      </nav>
    </aside>

    <!-- Contenido principal -->
    <main class="admin-main">
      <div class="dashboard-header">
        <h1>Panel de Administración</h1>
        <p class="rol-badge">
          Rol: <span class="badge badge-vigente">{{ authStore.userRole }}</span>
        </p>
      </div>

      <LoadingSpinner v-if="piuStore.loading" />

      <!-- Cards de resumen -->
      <div class="resumen-grid">
        <div class="resumen-card card">
          <div class="resumen-icon activo" aria-hidden="true">🟢</div>
          <div class="resumen-info">
            <span class="resumen-numero">{{ piusActivos }}</span>
            <span class="resumen-label">PIUs Activos</span>
          </div>
        </div>
        <div class="resumen-card card">
          <div class="resumen-icon falla" aria-hidden="true">🔴</div>
          <div class="resumen-info">
            <span class="resumen-numero">{{ piusConFallas }}</span>
            <span class="resumen-label">PIUs con Fallas</span>
          </div>
        </div>
        <div class="resumen-card card">
          <div class="resumen-icon pendiente" aria-hidden="true">🟡</div>
          <div class="resumen-info">
            <span class="resumen-numero">{{ contenidosPendientes }}</span>
            <span class="resumen-label">Contenidos Pendientes</span>
          </div>
        </div>
        <div class="resumen-card card">
          <div class="resumen-icon evento" aria-hidden="true">📅</div>
          <div class="resumen-info">
            <span class="resumen-numero">{{ eventosProximos }}</span>
            <span class="resumen-label">Eventos Próximos</span>
          </div>
        </div>
      </div>

      <!-- Links a secciones -->
      <div class="secciones-grid">
        <RouterLink v-if="authStore.userRole === 'ADMINISTRADOR'" to="/admin/pius" class="seccion-link card">
          <span class="sec-icon" aria-hidden="true">🖥</span>
          <h3>Gestión de PIUs</h3>
          <p>Administrar dispositivos de información universitaria.</p>
        </RouterLink>
        <RouterLink v-if="authStore.userRole === 'ADMINISTRADOR'" to="/admin/usuarios" class="seccion-link card">
          <span class="sec-icon" aria-hidden="true">👥</span>
          <h3>Gestión de Usuarios</h3>
          <p>Crear, editar y gestionar usuarios y sus roles.</p>
        </RouterLink>
        <RouterLink to="/admin/monitoreo" class="seccion-link card">
          <span class="sec-icon" aria-hidden="true">📡</span>
          <h3>Monitoreo</h3>
          <p>Estado en tiempo real de los dispositivos e incidencias.</p>
        </RouterLink>
<<<<<<< HEAD
        <RouterLink to="/admin/contenido" class="seccion-link card">
=======
        <RouterLink to="/admin/contenido" class="seccion-link card" v-if="['ADMINISTRADOR','GESTOR'].includes(authStore.userRole)">
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          <span class="sec-icon" aria-hidden="true">📝</span>
          <h3>Gestión de Contenido</h3>
          <p>Crear, editar y gestionar el contenido del sistema.</p>
        </RouterLink>
<<<<<<< HEAD
        <RouterLink v-if="['ADMINISTRADOR', 'GESTOR'].includes(authStore.userRole)" to="/admin/validacion" class="seccion-link card">
=======
        <RouterLink v-if="['ADMINISTRADOR'].includes(authStore.userRole)" to="/admin/validacion" class="seccion-link card">
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          <span class="sec-icon" aria-hidden="true">✅</span>
          <h3>Validación de Contenido</h3>
          <p>Revisar y aprobar contenidos enviados a validación.</p>
        </RouterLink>
        <RouterLink to="/admin/reportes" class="seccion-link card">
          <span class="sec-icon" aria-hidden="true">📈</span>
          <h3>Reportes</h3>
          <p>Estadísticas de uso y disponibilidad del sistema.</p>
        </RouterLink>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth.js'
import { usePiuStore } from '../../stores/piu.js'
import { useContenidoStore } from '../../stores/contenido.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'

const authStore = useAuthStore()
const piuStore = usePiuStore()
const contenidoStore = useContenidoStore()

const piusActivos = computed(() => piuStore.pius.filter(p => p.estado === 'activo').length)
const piusConFallas = computed(() => piuStore.pius.filter(p => p.estado === 'inactivo' || p.estado === 'mantenimiento').length)
const contenidosPendientes = computed(() => contenidoStore.contenidos.filter(c => c.estado === 'en_validacion').length)
const eventosProximos = computed(() => 3) // mock

onMounted(() => {
  piuStore.cargarPIUs()
  contenidoStore.cargarContenidos()
})
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: calc(100vh - 64px);
}

.admin-sidebar {
  width: 220px;
  background-color: var(--color-white);
  border-right: 1px solid var(--color-border);
  padding: 1.25rem 0;
  flex-shrink: 0;
}

.sidebar-title {
  color: var(--color-primary);
  font-size: 0.95rem;
  font-weight: 700;
  padding: 0 1rem 0.75rem;
  border-bottom: 1px solid var(--color-border);
  margin-bottom: 0.5rem;
}

.sidebar-nav {
  list-style: none;
  display: flex;
  flex-direction: column;
}

.sidebar-nav li a {
  display: block;
  padding: 0.6rem 1rem;
  color: var(--color-text);
  font-size: 0.9rem;
  transition: all var(--transition);
  border-left: 3px solid transparent;
}

.sidebar-nav li a:hover,
.sidebar-nav li a.activo,
.sidebar-nav li a.router-link-active {
  background-color: rgba(0, 51, 102, 0.06);
  color: var(--color-primary);
  border-left-color: var(--color-primary);
}

.admin-main {
  flex: 1;
  padding: 1.5rem;
  overflow-x: auto;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.dashboard-header h1 {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
}

.resumen-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.resumen-card {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.resumen-icon {
  font-size: 2rem;
}

.resumen-numero {
  display: block;
  font-size: 2rem;
  font-weight: 700;
  color: var(--color-primary);
  line-height: 1;
}

.resumen-label {
  font-size: 0.8rem;
  color: var(--color-text-light);
}

.secciones-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.seccion-link {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  text-decoration: none;
  color: var(--color-text);
  transition: all var(--transition);
}

.seccion-link:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-hover);
}

.sec-icon {
  font-size: 2rem;
}

.seccion-link h3 {
  color: var(--color-primary);
  font-size: 0.95rem;
  font-weight: 600;
}

.seccion-link p {
  font-size: 0.82rem;
  color: var(--color-text-light);
  line-height: 1.4;
}

@media (max-width: 768px) {
  .admin-layout {
    flex-direction: column;
  }

  .admin-sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid var(--color-border);
  }

  .sidebar-nav {
    flex-direction: row;
    flex-wrap: wrap;
    padding: 0 0.5rem;
  }

  .sidebar-nav li a {
    border-left: none;
    border-bottom: 3px solid transparent;
    padding: 0.5rem 0.75rem;
  }
}
</style>

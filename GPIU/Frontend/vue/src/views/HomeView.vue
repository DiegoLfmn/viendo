<template>
  <div class="home-view">
    <!-- Hero Section -->
    <section class="hero" aria-label="Bienvenida al sistema GPIU">
      <div class="hero-content">
        <h1 class="hero-title">Bienvenido al Sistema <span class="accent">GPIU</span></h1>
        <p class="hero-subtitle">
          Gestión de Puntos de Información Universitaria — Tu acceso centralizado a toda la información académica y administrativa.
        </p>
        <div v-if="authStore.isAuthenticated" class="welcome-user">
          <p>¡Hola, <strong>{{ authStore.userName }}</strong>! Has iniciado sesión como <span class="badge badge-vigente">{{ authStore.userRole }}</span></p>
        </div>
      </div>
    </section>

    <!-- Acceso Rápido -->
    <section class="quick-access page-content" aria-label="Acceso rápido">
      <h2 class="section-title">Acceso Rápido</h2>
      <div class="cards-grid">
        <RouterLink to="/informacion-general" class="quick-card card" aria-label="Información General">
          <div class="quick-icon" aria-hidden="true">🏛</div>
          <h3>Información General</h3>
          <p>Historia, estructura organizacional y distribución del campus universitario.</p>
        </RouterLink>

        <RouterLink to="/eventos" class="quick-card card" aria-label="Eventos">
          <div class="quick-icon" aria-hidden="true">📅</div>
          <h3>Eventos</h3>
          <p>Consulta eventos vigentes, charlas, seminarios y actividades académicas.</p>
        </RouterLink>

        <RouterLink to="/procesos-administrativos" class="quick-card card" aria-label="Procesos Administrativos">
          <div class="quick-icon" aria-hidden="true">📋</div>
          <h3>Procesos Administrativos</h3>
          <p>Matrícula, normativas, avisos y gestión de trámites universitarios.</p>
        </RouterLink>
        
        <RouterLink to="/ruta/1" class="quick-card card" aria-label="Mapa de Rutas">
          <div class="quick-icon" aria-hidden="true">🗺️</div>
          <h3>Mapa de Rutas</h3>
          <p>Encuentra una ruta para llegar al destino deseado dentro del campus.</p>
        </RouterLink>

        <RouterLink
<<<<<<< HEAD
          v-if="authStore.isAuthenticated"
=======
          v-if="authStore.isAuthenticated && ['ESTUDIANTE','PROFESOR','FUNCIONARIO'].includes(authStore.userRole)"
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          to="/informacion-privada"
          class="quick-card card private"
          aria-label="Información Privada"
        >
          <div class="quick-icon" aria-hidden="true">🔐</div>
          <h3>Información Privada</h3>
          <p>Malla curricular, horarios, salas y datos personalizados según tu rol.</p>
        </RouterLink>

<<<<<<< HEAD
        <div v-else class="quick-card card private-locked" aria-label="Información Privada bloqueada">
=======
        <div v-if="!authStore.isAuthenticated" class="quick-card card private-locked" aria-label="Información Privada bloqueada">
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
          <div class="quick-icon" aria-hidden="true">🔒</div>
          <h3>Información Privada</h3>
          <p>Inicia sesión para acceder a tu información personalizada.</p>
          <RouterLink to="/login" class="btn-primary" style="margin-top: 0.75rem; font-size: 0.85rem;">Iniciar Sesión</RouterLink>
        </div>
      </div>
    </section>

    <!-- Admin acceso rápido -->
    <section v-if="authStore.isAdmin" class="admin-access page-content" aria-label="Administración">
      <h2 class="section-title">Panel de Administración</h2>
      <div class="admin-links">
        <RouterLink to="/admin" class="btn-primary">Dashboard Admin</RouterLink>
        <RouterLink to="/admin/monitoreo" class="btn-secondary">Monitoreo PIUs</RouterLink>
        <RouterLink to="/admin/contenido" class="btn-secondary">Gestión Contenido</RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth.js'

const authStore = useAuthStore()
</script>

<style scoped>
.hero {
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-secondary) 100%);
  color: var(--color-white);
  padding: 4rem 1rem;
  text-align: center;
}

.hero-content {
  max-width: 700px;
  margin: 0 auto;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.accent {
  color: var(--color-accent);
}

.hero-subtitle {
  font-size: 1.1rem;
  opacity: 0.9;
  line-height: 1.7;
  margin-bottom: 1.5rem;
}

.welcome-user {
  background-color: rgba(255, 255, 255, 0.15);
  padding: 0.75rem 1.5rem;
  border-radius: var(--border-radius);
  display: inline-block;
}

.section-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 1.5rem;
  padding-bottom: 0.5rem;
  border-bottom: 3px solid var(--color-accent);
  display: inline-block;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 1.5rem;
}

.quick-card {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  text-decoration: none;
  color: var(--color-text);
  transition: all var(--transition);
  cursor: pointer;
}

.quick-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-hover);
}

.quick-card h3 {
  color: var(--color-primary);
  font-size: 1.05rem;
  font-weight: 600;
  margin: 0.5rem 0 0.4rem;
}

.quick-card p {
  color: var(--color-text-light);
  font-size: 0.9rem;
  line-height: 1.5;
}

.quick-icon {
  font-size: 2.5rem;
}

.quick-card.private {
  border-left: 4px solid var(--color-secondary);
}

.private-locked {
  opacity: 0.7;
  cursor: default;
}

.private-locked:hover {
  transform: none;
}

.admin-access {
  border-top: 1px solid var(--color-border);
}

.admin-links {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 1.75rem;
  }

  .cards-grid {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 480px) {
  .cards-grid {
    grid-template-columns: 1fr;
  }
}
</style>

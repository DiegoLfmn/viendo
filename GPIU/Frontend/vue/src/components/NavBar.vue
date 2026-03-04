<template>
  <nav class="navbar" role="navigation" aria-label="Navegación principal">
    <div class="navbar-container">
      <RouterLink to="/" class="navbar-brand" aria-label="GPIU - Inicio">
        <span class="navbar-logo">🏛</span>
        <span class="navbar-title">GPIU</span>
      </RouterLink>

      <button
        class="navbar-toggle"
        :aria-expanded="menuOpen"
        aria-controls="navbar-menu"
        aria-label="Abrir menú"
        @click="menuOpen = !menuOpen"
      >
        <span class="hamburger-line"></span>
        <span class="hamburger-line"></span>
        <span class="hamburger-line"></span>
      </button>

      <div id="navbar-menu" class="navbar-menu" :class="{ 'is-open': menuOpen }">
        <ul class="nav-links" role="list">
          <li><RouterLink to="/" @click="menuOpen = false">Inicio</RouterLink></li>
          <li><RouterLink to="/informacion-general" @click="menuOpen = false">Información General</RouterLink></li>
          <li><RouterLink to="/eventos" @click="menuOpen = false">Eventos</RouterLink></li>
          <li><RouterLink to="/procesos-administrativos" @click="menuOpen = false">Procesos Administrativos</RouterLink></li>
          <li><RouterLink to="/ruta/1" @click="menuOpen = false">Rutas</RouterLink></li>
          <li v-if="authStore.isAuthenticated">
          
            <RouterLink to="/informacion-privada" @click="menuOpen = false">Info Privada</RouterLink>
          </li>
          <li v-if="authStore.isAdmin">
            <RouterLink to="/admin" @click="menuOpen = false">Administración</RouterLink>
          </li>
        </ul>

        <div class="navbar-auth">
          <template v-if="authStore.isAuthenticated">
            <span class="user-info">
              <span class="user-name">{{ authStore.userName }}</span>
              <span class="user-role badge">{{ authStore.userRole }}</span>
            </span>
            <button class="btn-logout" @click="handleLogout" aria-label="Cerrar sesión">
              Cerrar Sesión
            </button>
          </template>
          <template v-else>
            <RouterLink to="/login" class="btn-login" @click="menuOpen = false">
              Iniciar Sesión
            </RouterLink>
          </template>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth.js'

const authStore = useAuthStore()
const router = useRouter()
const menuOpen = ref(false)

async function handleLogout() {
  await authStore.logout()
  menuOpen.value = false
  router.push('/')
}
</script>

<style scoped>
.navbar {
  background-color: var(--color-primary);
  color: var(--color-white);
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

.navbar-brand {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--color-white);
  text-decoration: none;
  font-weight: 700;
  font-size: 1.3rem;
}

.navbar-logo {
  font-size: 1.5rem;
}

.navbar-title {
  color: var(--color-accent);
  letter-spacing: 2px;
}

.navbar-toggle {
  display: none;
  flex-direction: column;
  gap: 5px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.25rem;
}

.hamburger-line {
  display: block;
  width: 24px;
  height: 2px;
  background-color: var(--color-white);
  border-radius: 2px;
}

.navbar-menu {
  display: flex;
  align-items: center;
  gap: 1rem;
  flex: 1;
  justify-content: space-between;
  margin-left: 2rem;
}

.nav-links {
  display: flex;
  list-style: none;
  gap: 0.25rem;
}

.nav-links a {
  color: rgba(255, 255, 255, 0.85);
  text-decoration: none;
  padding: 0.4rem 0.75rem;
  border-radius: var(--border-radius);
  font-size: 0.9rem;
  transition: all var(--transition);
}

.nav-links a:hover,
.nav-links a.router-link-active {
  color: var(--color-white);
  background-color: rgba(255, 255, 255, 0.15);
}

.navbar-auth {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.user-name {
  color: var(--color-white);
  font-size: 0.9rem;
  font-weight: 500;
}

.user-role {
  background-color: var(--color-accent);
  color: var(--color-primary);
  font-size: 0.7rem;
  padding: 0.15rem 0.5rem;
}

.btn-logout {
  background-color: transparent;
  border: 1px solid rgba(255, 255, 255, 0.5);
  color: var(--color-white);
  padding: 0.35rem 0.9rem;
  border-radius: var(--border-radius);
  cursor: pointer;
  font-size: 0.85rem;
  transition: all var(--transition);
}

.btn-logout:hover {
  background-color: rgba(255, 255, 255, 0.15);
  border-color: var(--color-white);
}

.btn-login {
  background-color: var(--color-accent);
  color: var(--color-primary);
  padding: 0.4rem 1rem;
  border-radius: var(--border-radius);
  font-weight: 600;
  font-size: 0.9rem;
  transition: background-color var(--transition);
}

.btn-login:hover {
  background-color: #e6c200;
  color: var(--color-primary);
}

@media (max-width: 768px) {
  .navbar-toggle {
    display: flex;
  }

  .navbar-menu {
    display: none;
    position: absolute;
    top: 64px;
    left: 0;
    right: 0;
    background-color: var(--color-primary);
    flex-direction: column;
    align-items: flex-start;
    margin-left: 0;
    padding: 1rem;
    gap: 0.75rem;
    box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  }

  .navbar-menu.is-open {
    display: flex;
  }

  .nav-links {
    flex-direction: column;
    width: 100%;
  }

  .nav-links a {
    display: block;
    padding: 0.6rem 0.75rem;
  }

  .navbar-auth {
    width: 100%;
    justify-content: space-between;
  }
}
</style>

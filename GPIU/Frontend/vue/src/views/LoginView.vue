<template>
  <div class="login-view">
    <div class="login-container">
      <div class="login-header">
        <div class="login-logo" aria-hidden="true">🏛</div>
        <h1 class="login-title">GPIU</h1>
        <p class="login-subtitle">Puntos de Información Universitaria</p>
      </div>

      <!-- Mensaje de bloqueo -->
      <div v-if="bloqueado" class="alert alert-error" role="alert" aria-live="assertive">
        <strong>⛔ Cuenta bloqueada</strong>
        <p>Ha excedido el número máximo de intentos. Podrá volver a intentar en <strong>{{ tiempoRestante }}</strong>.</p>
      </div>

      <!-- Advertencia de intentos -->
      <div v-else-if="authStore.intentosFallidos >= 3 && authStore.intentosFallidos < 5" class="alert alert-warning" role="alert">
        ⚠️ Atención: {{ 5 - authStore.intentosFallidos }} intentos restantes antes del bloqueo.
      </div>

      <!-- Formulario -->
      <form v-if="!bloqueado" class="login-form" @submit.prevent="handleLogin" novalidate>
        <div class="form-group">
          <label for="correo">Correo institucional</label>
          <input
            id="correo"
            v-model="form.correo"
            type="email"
            class="form-control"
            placeholder="usuario@universidad.cl"
            autocomplete="email"
            required
            :disabled="loading"
            aria-required="true"
          />
        </div>

        <div class="form-group">
          <label for="contrasena">Contraseña</label>
          <input
            id="contrasena"
            v-model="form.contrasena"
            type="password"
            class="form-control"
            placeholder="••••••••"
            autocomplete="current-password"
            required
            :disabled="loading"
            aria-required="true"
          />
        </div>

        <div v-if="errorMsg" class="alert alert-error" role="alert" aria-live="assertive">
          {{ errorMsg }}
        </div>

        <button
          type="submit"
          class="btn-primary btn-block"
          :disabled="loading"
          aria-label="Iniciar sesión"
        >
          <span v-if="loading">⏳ Autenticando...</span>
          <span v-else>Ingresar</span>
        </button>
      </form>

      <div class="login-divider">
        <span>o</span>
      </div>

      <RouterLink to="/" class="btn-visitante">
        Continuar como Visitante
      </RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth.js'

const authStore = useAuthStore()
const router = useRouter()

const form = ref({ correo: '', contrasena: '' })
const loading = ref(false)
const errorMsg = ref('')
let timerInterval = null
const tiempoRestante = ref('')

const bloqueado = computed(() => {
  if (!authStore.bloqueado) return false
  if (authStore.bloqueoExpiry && Date.now() > authStore.bloqueoExpiry) {
    authStore.bloqueado = false
    return false
  }
  return true
})

function updateTimer() {
  if (authStore.bloqueoExpiry) {
    const remaining = Math.max(0, authStore.bloqueoExpiry - Date.now())
    const mins = Math.floor(remaining / 60000)
    const secs = Math.floor((remaining % 60000) / 1000)
    tiempoRestante.value = `${mins}:${secs.toString().padStart(2, '0')}`
    if (remaining <= 0) {
      authStore.bloqueado = false
      clearInterval(timerInterval)
    }
  }
}

onMounted(() => {
  if (authStore.isAuthenticated) {
    router.push('/')
  }
  timerInterval = setInterval(updateTimer, 1000)
  updateTimer()
})

onUnmounted(() => {
  clearInterval(timerInterval)
})

async function handleLogin() {
  errorMsg.value = ''
  if (!form.value.correo || !form.value.contrasena) {
    errorMsg.value = 'Ingrese su correo y contraseña.'
    return
  }
  loading.value = true
  try {
    await authStore.login({ correo: form.value.correo, contrasena: form.value.contrasena })
    router.push('/')
  } catch (error) {
    if (authStore.bloqueado) {
      errorMsg.value = ''
    } else if (error.response && error.response.status === 401) {
      errorMsg.value = 'Credenciales inválidas. Verifique su correo y contraseña.'
    } else if (error.message && error.message.includes('bloqueada')) {
      errorMsg.value = error.message
    } else {
      errorMsg.value = 'Credenciales inválidas. Verifique su correo y contraseña.'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-view {
  min-height: calc(100vh - 64px);
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--color-primary) 0%, #002244 100%);
  padding: 2rem 1rem;
}

.login-container {
  background-color: var(--color-white);
  border-radius: 12px;
  padding: 2.5rem;
  width: 100%;
  max-width: 420px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
}

.login-header {
  text-align: center;
  margin-bottom: 2rem;
}

.login-logo {
  font-size: 3rem;
  margin-bottom: 0.5rem;
}

.login-title {
  color: var(--color-primary);
  font-size: 2rem;
  font-weight: 700;
  letter-spacing: 3px;
}

.login-subtitle {
  color: var(--color-text-light);
  font-size: 0.9rem;
  margin-top: 0.25rem;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 0.1rem;
}

.btn-block {
  width: 100%;
  padding: 0.65rem;
  font-size: 1rem;
  margin-top: 0.5rem;
  justify-content: center;
}

.login-divider {
  text-align: center;
  position: relative;
  margin: 1.25rem 0;
  color: var(--color-text-light);
  font-size: 0.85rem;
}

.login-divider::before,
.login-divider::after {
  content: '';
  position: absolute;
  top: 50%;
  width: 42%;
  height: 1px;
  background-color: var(--color-border);
}

.login-divider::before { left: 0; }
.login-divider::after { right: 0; }

.btn-visitante {
  display: block;
  width: 100%;
  text-align: center;
  padding: 0.6rem;
  border: 2px solid var(--color-primary);
  border-radius: var(--border-radius);
  color: var(--color-primary);
  font-weight: 500;
  font-size: 0.95rem;
  transition: all var(--transition);
}

.btn-visitante:hover {
  background-color: var(--color-primary);
  color: var(--color-white);
}
</style>

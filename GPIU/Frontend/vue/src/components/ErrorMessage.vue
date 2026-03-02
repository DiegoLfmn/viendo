<template>
  <div class="error-message alert alert-error" role="alert" aria-live="assertive">
    <span class="error-icon" aria-hidden="true">⚠️</span>
    <p class="error-text">{{ message || 'Ha ocurrido un error. Por favor intente nuevamente.' }}</p>
    <button v-if="onRetry" class="btn-retry btn-secondary" @click="onRetry" aria-label="Reintentar">
      Reintentar
    </button>
  </div>
</template>

<script setup>
defineProps({
  message: { type: String, default: '' },
  onRetry: { type: Function, default: null }
})
</script>

<style scoped>
.error-message {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.error-icon {
  font-size: 1.25rem;
  flex-shrink: 0;
}

.error-text {
  flex: 1;
  font-size: 0.95rem;
}

.btn-retry {
  font-size: 0.85rem;
  padding: 0.35rem 0.85rem;
}
</style>

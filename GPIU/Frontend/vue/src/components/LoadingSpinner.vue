<template>
  <div class="loading-spinner" role="status" aria-live="polite" aria-label="Cargando">
    <div class="spinner" aria-hidden="true"></div>
    <p class="loading-text">Cargando...</p>
  </div>
</template>

<script setup>
</script>

<style scoped>
.loading-spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  gap: 1rem;
}

.spinner {
  width: 48px;
  height: 48px;
  border: 4px solid var(--color-border);
  border-top-color: var(--color-secondary);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.loading-text {
  color: var(--color-text-light);
  font-size: 0.95rem;
}
</style>

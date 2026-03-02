<template>
  <div class="event-card card" role="article" @click="emit('click', evento)">
    <div class="event-header">
      <h3 class="event-title">{{ evento.titulo }}</h3>
      <span class="badge" :class="evento.estaVigente ? 'badge-vigente' : 'badge-pasado'">
        {{ evento.estaVigente ? 'Vigente' : 'Finalizado' }}
      </span>
    </div>

    <div class="event-meta">
      <div class="meta-item" aria-label="Fecha">
        <span class="meta-icon">📅</span>
        <span>{{ formatFecha(evento.fecha) }}</span>
      </div>
      <div v-if="evento.horaInicio" class="meta-item" aria-label="Horario">
        <span class="meta-icon">🕐</span>
        <span>{{ evento.horaInicio }}{{ evento.horaFin ? ` - ${evento.horaFin}` : '' }}</span>
      </div>
      <div v-if="evento.sala || evento.edificio" class="meta-item" aria-label="Lugar">
        <span class="meta-icon">📍</span>
        <span>{{ evento.sala || evento.edificio }}</span>
      </div>
      <div v-if="evento.organizador" class="meta-item" aria-label="Organizador">
        <span class="meta-icon">🏢</span>
        <span>{{ evento.organizador }}</span>
      </div>
    </div>

    <div class="event-footer">
      <button class="btn-ver-detalle" aria-label="Ver detalle del evento">
        Ver detalle →
      </button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  evento: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['click'])

function formatFecha(fecha) {
  if (!fecha) return ''
  return new Date(fecha + 'T00:00:00').toLocaleDateString('es-CL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}
</script>

<style scoped>
.event-card {
  cursor: pointer;
  transition: box-shadow var(--transition), transform var(--transition);
}

.event-card:hover {
  box-shadow: var(--shadow-hover);
  transform: translateY(-2px);
}

.event-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 0.75rem;
  margin-bottom: 0.75rem;
}

.event-title {
  color: var(--color-primary);
  font-size: 1rem;
  font-weight: 600;
  line-height: 1.4;
  flex: 1;
}

.event-meta {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.88rem;
  color: var(--color-text-light);
}

.meta-icon {
  font-size: 1rem;
  flex-shrink: 0;
}

.event-footer {
  margin-top: 1rem;
  padding-top: 0.75rem;
  border-top: 1px solid var(--color-border);
}

.btn-ver-detalle {
  background: none;
  border: none;
  color: var(--color-secondary);
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  padding: 0;
  transition: color var(--transition);
}

.btn-ver-detalle:hover {
  color: var(--color-primary);
}
</style>

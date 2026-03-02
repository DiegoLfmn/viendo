<template>
  <div class="content-card card" :class="{ clickable: !!onClick }" @click="onClick && onClick()">
    <div class="card-header" v-if="title || $slots.header">
      <slot name="header">
        <h3 class="card-title">{{ title }}</h3>
      </slot>
      <span v-if="badge" class="badge" :class="`badge-${badge.toLowerCase()}`">{{ badge }}</span>
    </div>
    <div class="card-body">
      <slot />
    </div>
    <div class="card-footer" v-if="$slots.footer">
      <slot name="footer" />
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: '' },
  badge: { type: String, default: '' },
  onClick: { type: Function, default: null }
})
</script>

<style scoped>
.content-card {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.content-card.clickable {
  cursor: pointer;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 0.5rem;
}

.card-title {
  color: var(--color-primary);
  font-size: 1.05rem;
  font-weight: 600;
  margin: 0;
}

.card-footer {
  margin-top: auto;
  padding-top: 0.75rem;
  border-top: 1px solid var(--color-border);
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}
</style>

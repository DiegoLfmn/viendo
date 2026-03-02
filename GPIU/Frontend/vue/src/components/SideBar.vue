<template>
  <aside class="sidebar" role="complementary" aria-label="Menú lateral">
    <nav>
      <ul class="sidebar-links" role="list">
        <slot />
      </ul>
    </nav>
  </aside>
</template>

<script setup>
</script>

<style scoped>
.sidebar {
  background-color: var(--color-white);
  border-right: 1px solid var(--color-border);
  width: 220px;
  min-height: 100%;
  padding: 1rem 0;
}

.sidebar-links {
  list-style: none;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  padding: 0 0.75rem;
}

@media (max-width: 768px) {
  .sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid var(--color-border);
    padding: 0.5rem 0;
  }

  .sidebar-links {
    flex-direction: row;
    flex-wrap: wrap;
  }
}
</style>

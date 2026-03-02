<template>
  <div class="app-layout">
    <NavBar />
    <main class="app-main" role="main">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-main {
  flex: 1;
  background-color: var(--color-bg);
}
</style>


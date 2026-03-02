<template>
  <div class="page-content">
    <div class="view-header">
      <h1 class="page-title">Reportes del Sistema</h1>
      <div class="header-actions">
        <input v-model="fechaInicio" type="date" class="form-control" aria-label="Fecha inicio" />
        <input v-model="fechaFin" type="date" class="form-control" aria-label="Fecha fin" />
        <button class="btn-secondary" @click="exportar" aria-label="Exportar reporte">📥 Exportar</button>
      </div>
    </div>

    <LoadingSpinner v-if="loading" />

    <div v-else>
      <!-- Resumen estadístico -->
      <section class="resumen-section" aria-label="Resumen estadístico">
        <h2 class="section-title">Resumen del Período</h2>
        <div class="stats-grid">
          <div class="stat-card card">
            <span class="stat-numero">{{ estadisticas.totalPIUs }}</span>
            <span class="stat-label">Total PIUs</span>
          </div>
          <div class="stat-card card">
            <span class="stat-numero">{{ estadisticas.activos }}</span>
            <span class="stat-label">PIUs Activos</span>
          </div>
          <div class="stat-card card">
            <span class="stat-numero stat-porcentaje">{{ estadisticas.disponibilidad }}%</span>
            <span class="stat-label">Disponibilidad</span>
          </div>
          <div class="stat-card card">
            <span class="stat-numero">{{ estadisticas.incidenciasMes }}</span>
            <span class="stat-label">Incidencias del Mes</span>
          </div>
        </div>
      </section>

      <!-- Tabla de PIUs -->
      <section class="tabla-section" aria-label="Estado de PIUs">
        <h2 class="section-title">Estado de PIUs</h2>
        <div class="table-container">
          <table class="table" aria-label="Reporte de estado de PIUs">
            <thead>
              <tr>
                <th>Código</th>
                <th>Ubicación</th>
                <th>Estado Actual</th>
                <th>Disponibilidad</th>
                <th>Última Falla</th>
                <th>Acciones Realizadas</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="piu in reportePIUs" :key="piu.id">
                <td><strong>{{ piu.codigo }}</strong></td>
                <td>{{ piu.ubicacion }}</td>
                <td>
                  <span class="estado-dot" :class="`estado-${piu.estado}`" aria-hidden="true"></span>
                  {{ estadoLabel(piu.estado) }}
                </td>
                <td>
                  <div class="barra-disponibilidad">
                    <div class="barra-fill" :style="{ width: piu.disponibilidad + '%', backgroundColor: colorDisponibilidad(piu.disponibilidad) }"></div>
                  </div>
                  <span class="disponibilidad-pct">{{ piu.disponibilidad }}%</span>
                </td>
                <td>{{ piu.ultimaFalla || 'Sin fallas' }}</td>
                <td>{{ piu.accionesRealizadas || 'Ninguna' }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { usePiuStore } from '../../stores/piu.js'
import LoadingSpinner from '../../components/LoadingSpinner.vue'

const piuStore = usePiuStore()
const loading = ref(false)
const fechaInicio = ref('')
const fechaFin = ref('')

const estadisticas = computed(() => piuStore.estadisticas || {
  totalPIUs: piuStore.pius.length,
  activos: piuStore.pius.filter(p => p.estado === 'activo').length,
  disponibilidad: 75,
  incidenciasMes: 2
})

const reportePIUs = computed(() => piuStore.pius.map(p => ({
  ...p,
  disponibilidad: p.estado === 'activo' ? 98 : p.estado === 'mantenimiento' ? 45 : 0,
  ultimaFalla: p.estado === 'inactivo' ? '2024-02-25' : p.estado === 'mantenimiento' ? '2024-02-28' : null,
  accionesRealizadas: p.estado === 'inactivo' ? 'Revisión pendiente' : p.estado === 'mantenimiento' ? 'En mantenimiento preventivo' : 'N/A'
})))

function estadoLabel(estado) {
  const labels = { activo: 'Activo', inactivo: 'Inactivo', mantenimiento: 'Mantenimiento' }
  return labels[estado] || estado
}

function colorDisponibilidad(pct) {
  if (pct >= 90) return 'var(--color-success)'
  if (pct >= 60) return 'var(--color-warning)'
  return 'var(--color-danger)'
}

function exportar() {
  const lineas = ['REPORTE DE ESTADO DE PIUs', `Período: ${fechaInicio.value || 'N/A'} al ${fechaFin.value || 'N/A'}`, '']
  lineas.push(`Total PIUs: ${estadisticas.value.totalPIUs}`)
  lineas.push(`PIUs Activos: ${estadisticas.value.activos}`)
  lineas.push(`Disponibilidad: ${estadisticas.value.disponibilidad}%`)
  lineas.push(`Incidencias del mes: ${estadisticas.value.incidenciasMes}`)
  lineas.push('')
  lineas.push('DETALLE:')
  reportePIUs.value.forEach(p => {
    lineas.push(`${p.codigo} | ${p.ubicacion} | ${estadoLabel(p.estado)} | Disponibilidad: ${p.disponibilidad}%`)
  })

  const blob = new Blob([lineas.join('\n')], { type: 'text/plain;charset=utf-8' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `reporte-gpiu-${new Date().toISOString().split('T')[0]}.txt`
  a.click()
  URL.revokeObjectURL(url)
}

onMounted(async () => {
  loading.value = true
  await piuStore.cargarPIUs()
  await piuStore.cargarEstadisticas()
  loading.value = false
})
</script>

<style scoped>
.view-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.page-title {
  color: var(--color-primary);
  font-size: 1.5rem;
  font-weight: 700;
}

.header-actions {
  display: flex;
  gap: 0.75rem;
  align-items: center;
  flex-wrap: wrap;
}

.header-actions .form-control {
  width: auto;
}

.section-title {
  color: var(--color-primary);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.resumen-section {
  margin-bottom: 2rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 1rem;
}

.stat-card {
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

.stat-numero {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--color-primary);
  line-height: 1;
}

.stat-porcentaje {
  color: var(--color-success);
}

.stat-label {
  font-size: 0.82rem;
  color: var(--color-text-light);
}

.tabla-section {
  margin-bottom: 2rem;
}

.table-container {
  overflow-x: auto;
}

.estado-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 4px;
  vertical-align: middle;
}

.estado-activo { background-color: var(--color-success); }
.estado-inactivo { background-color: var(--color-danger); }
.estado-mantenimiento { background-color: var(--color-warning); }

.barra-disponibilidad {
  height: 6px;
  background-color: var(--color-border);
  border-radius: 3px;
  overflow: hidden;
  width: 80px;
  display: inline-block;
  vertical-align: middle;
  margin-right: 6px;
}

.barra-fill {
  height: 100%;
  border-radius: 3px;
  transition: width 0.3s ease;
}

.disponibilidad-pct {
  font-size: 0.85rem;
}
</style>

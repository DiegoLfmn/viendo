import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth.js'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import InformacionGeneralView from '../views/InformacionGeneralView.vue'
import EventosView from '../views/EventosView.vue'
<<<<<<< HEAD
import EventoDetalleView from '../views/EventoDetalleView.vue'
import ProcesosAdmView from '../views/ProcesosAdmView.vue'
=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
import InfoPrivadaView from '../views/InfoPrivadaView.vue'
import RutaMapaView from '../views/RutaMapaView.vue'
import DashboardAdmin from '../views/admin/DashboardAdmin.vue'
import GestionPIUsView from '../views/admin/GestionPIUsView.vue'
import MonitoreoView from '../views/admin/MonitoreoView.vue'
import ContenidoCRUDView from '../views/admin/ContenidoCRUDView.vue'
import ValidacionContenidoView from '../views/admin/ValidacionContenidoView.vue'
import ReportesView from '../views/admin/ReportesView.vue'
import GestionUsuariosView from '../views/admin/GestionUsuariosView.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/login', component: LoginView },
  { path: '/informacion-general', component: InformacionGeneralView },
  { path: '/eventos', component: EventosView },
<<<<<<< HEAD
  { path: '/eventos/:id', component: EventoDetalleView },
  { path: '/procesos-administrativos', component: ProcesosAdmView },
=======
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
  {
    path: '/informacion-privada',
    component: InfoPrivadaView,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE', 'PROFESOR', 'FUNCIONARIO'] }
  },
  { path: '/ruta/:destinoId', component: RutaMapaView },
  {
    path: '/admin',
    component: DashboardAdmin,
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR', 'OPERADOR'] }
  },
  {
    path: '/admin/pius',
    component: GestionPIUsView,
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] }
  },
  {
    path: '/admin/monitoreo',
    component: MonitoreoView,
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR', 'OPERADOR'] }
  },
  {
    path: '/admin/contenido',
    component: ContenidoCRUDView,
<<<<<<< HEAD
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR', 'OPERADOR'] }
=======
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR'] }
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
  },
  {
    path: '/admin/validacion',
    component: ValidacionContenidoView,
<<<<<<< HEAD
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR'] }
=======
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] }
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
  },
  {
    path: '/admin/reportes',
    component: ReportesView,
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR', 'GESTOR', 'OPERADOR'] }
  },
  {
    path: '/admin/usuarios',
    component: GestionUsuariosView,
    meta: { requiresAuth: true, roles: ['ADMINISTRADOR'] }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  const authStore = useAuthStore()
  authStore.checkSession()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    return next('/login')
  }

  if (to.meta.roles && authStore.isAuthenticated) {
    const userRole = authStore.userRole
    if (!to.meta.roles.includes(userRole)) {
      return next('/')
    }
  }

  next()
})

<<<<<<< HEAD
export default router
=======
export default router
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e

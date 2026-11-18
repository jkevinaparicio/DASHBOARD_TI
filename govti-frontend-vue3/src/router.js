import { createRouter, createWebHistory } from 'vue-router'
import LoginView from './views/LoginView.vue'
import DashboardView from './views/DashboardView.vue'
import ObjectivesView from './views/ObjectivesView.vue'
import IndicatorsView from './views/IndicatorsView.vue'
import RisksView from './views/RisksView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: LoginView, meta: { public: true } },
    { path: '/', component: DashboardView },
    { path: '/objetivos', component: ObjectivesView },
    { path: '/indicadores', component: IndicatorsView },
    { path: '/riesgos', component: RisksView },
    { path: '/:pathMatch(.*)*', redirect: '/' }
  ]
})
router.beforeEach(to => {
  if (!to.meta.public && !localStorage.getItem('govti_token')) return '/login'
  if (to.path === '/login' && localStorage.getItem('govti_token')) return '/'
})
export default router
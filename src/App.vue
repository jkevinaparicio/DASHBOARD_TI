<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { LayoutDashboard, Target, ChartNoAxesCombined, ShieldAlert, LogOut, Menu, X } from 'lucide-vue-next'
import { ref } from 'vue'

const route = useRoute()
const router = useRouter()
const open = ref(false)
const isLogin = computed(() => route.path === '/login')
const user = computed(() => {
  try { return JSON.parse(localStorage.getItem('govti_user') || '{}') } catch { return {} }
})
const links = [
  { label: 'Dashboard', path: '/', icon: LayoutDashboard },
  { label: 'Objetivos BSC', path: '/objetivos', icon: Target },
  { label: 'Indicadores', path: '/indicadores', icon: ChartNoAxesCombined },
  { label: 'Riesgos', path: '/riesgos', icon: ShieldAlert }
]
function logout() {
  localStorage.removeItem('govti_token')
  localStorage.removeItem('govti_user')
  router.push('/login')
}
</script>

<template>
  <div v-if="isLogin"><router-view /></div>
  <div v-else class="app-shell">
    <aside class="sidebar" :class="{ 'sidebar-open': open }">
      <div class="brand">
        <div class="brand-mark">G</div>
        <div><strong>GOVTI</strong><small>Gobierno estratégico de TI</small></div>
        <button class="icon-button mobile-close" @click="open=false"><X :size="19"/></button>
      </div>
      <div class="nav-label">MENÚ PRINCIPAL</div>
      <nav>
        <router-link v-for="item in links" :key="item.path" :to="item.path" class="nav-link" @click="open=false">
          <component :is="item.icon" :size="19"/><span>{{ item.label }}</span>
        </router-link>
      </nav>
      <div class="sidebar-bottom">
        <div class="user-avatar">{{ (user.nombre || user.username || 'U').charAt(0).toUpperCase() }}</div>
        <div class="user-info"><strong>{{ user.nombre || user.username || 'Usuario' }}</strong><small>{{ user.role || 'Usuario GOVTI' }}</small></div>
        <button class="icon-button" title="Cerrar sesión" @click="logout"><LogOut :size="18"/></button>
      </div>
    </aside>
    <div v-if="open" class="mobile-overlay" @click="open=false"></div>
    <main class="main-area">
      <header class="topbar">
        <button class="icon-button mobile-menu" @click="open=true"><Menu :size="21"/></button>
        <div><span class="topbar-overline">MILENIO PC S.A.</span><strong>Plataforma de Gobierno de TI</strong></div>
        <div class="topbar-right"><span class="status-dot"></span><span>Entorno académico</span></div>
      </header>
      <section class="page-content"><router-view /></section>
    </main>
  </div>
</template>
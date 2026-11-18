<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ShieldCheck, LockKeyhole } from 'lucide-vue-next'
import api from '../api'

const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')
async function login() {
  error.value = ''
  loading.value = true
  try {
    const { data } = await api.post('/auth/login', { username: username.value, password: password.value })
    localStorage.setItem('govti_token', data.token)
    localStorage.setItem('govti_user', JSON.stringify(data))
    router.push('/')
  } catch (e) {
    error.value = e.response?.data?.message || 'No fue posible iniciar sesión. Verifica tus credenciales y que el backend esté activo.'
  } finally { loading.value = false }
}
</script>

<template>
  <main class="login-page">
    <section class="login-brand-panel">
      <div class="login-brand"><div class="brand-mark large">G</div><div><strong>GOVTI</strong><small>Gobierno estratégico de TI</small></div></div>
      <div class="login-message">
        <span class="eyebrow">MILENIO PC S.A.</span>
        <h1>Gobernar la tecnología.<br><em>Impulsar el valor.</em></h1>
        <p>Plataforma de seguimiento estratégico basada en el modelo de Gobierno de TI de ISO/IEC 38500:2024.</p>
      </div>
      <div class="login-foot">ISO/IEC 38500:2024 · Balanced Scorecard</div>
    </section>
    <section class="login-form-panel">
      <form class="login-card" @submit.prevent="login">
        <div class="login-icon"><ShieldCheck :size="25"/></div>
        <span class="eyebrow">BIENVENIDO</span>
        <h2>Iniciar sesión</h2>
        <p class="muted">Ingresa tus credenciales para acceder a GOVTI.</p>
        <label>Usuario</label>
        <input v-model="username" required autocomplete="username" placeholder="Tu usuario"/>
        <label>Contraseña</label>
        <div class="password-wrap"><LockKeyhole :size="17"/><input v-model="password" required type="password" autocomplete="current-password" placeholder="Tu contraseña"/></div>
        <div v-if="error" class="alert error">{{ error }}</div>
        <button class="btn primary full" :disabled="loading">{{ loading ? 'Ingresando...' : 'Ingresar a la plataforma' }}</button>
        <small class="muted login-note">El acceso requiere una cuenta creada en el backend.</small>
      </form>
    </section>
  </main>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue'
import { Target, ChartNoAxesCombined, ClipboardList, ShieldAlert, ArrowUpRight, Activity } from 'lucide-vue-next'
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js'
import api from '../api'
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

const summary = ref({ totalObjetivos: 0, totalIndicadores: 0, totalMediciones: 0, totalRiesgos: 0 })
const loading = ref(true)
const error = ref('')
const bsc = [
  { name: 'Enfoque estratégico', value: 71 },
  { name: 'Traslado al BSC', value: 52 },
  { name: 'Sincronización', value: 70 },
  { name: 'Cultura de ejecución', value: 54 }
]
const chartData = computed(() => ({
  labels: bsc.map(x => x.name),
  datasets: [{ label: 'Resultado diagnóstico (%)', data: bsc.map(x => x.value), backgroundColor: ['#4263eb','#e6a23c','#269b79','#e6a23c'], borderRadius: 7, maxBarThickness: 46 }]
}))
const chartOptions = { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true, max: 100, ticks: { callback: v => `${v}%` }, grid: { color: '#edf0f4' } }, x: { grid: { display: false }, ticks: { maxRotation: 0, minRotation: 0 } } } }
const cards = computed(() => [
  { label: 'Objetivos estratégicos', value: summary.value.totalObjetivos, icon: Target, tone: 'blue', hint: 'Registrados en GOVTI' },
  { label: 'Indicadores', value: summary.value.totalIndicadores, icon: ChartNoAxesCombined, tone: 'purple', hint: 'Indicadores configurados' },
  { label: 'Mediciones', value: summary.value.totalMediciones, icon: ClipboardList, tone: 'green', hint: 'Resultados registrados' },
  { label: 'Riesgos', value: summary.value.totalRiesgos, icon: ShieldAlert, tone: 'orange', hint: 'Riesgos identificados' }
])
onMounted(async () => {
  try { const { data } = await api.get('/dashboard'); summary.value = data }
  catch { error.value = 'No se pudo consultar el dashboard. Comprueba que el backend esté activo.' }
  finally { loading.value = false }
})
</script>

<template>
  <div class="page-heading">
    <div><span class="eyebrow">VISTA GENERAL</span><h1>Dashboard estratégico</h1><p>Resumen del Gobierno de TI de Milenio PC S.A.</p></div>
    <div class="heading-chip"><Activity :size="16"/> ISO/IEC 38500:2024</div>
  </div>
  <div v-if="error" class="alert error">{{ error }}</div>
  <div class="stats-grid">
    <article v-for="card in cards" :key="card.label" class="stat-card">
      <div class="stat-top"><span>{{ card.label }}</span><div class="stat-icon" :class="card.tone"><component :is="card.icon" :size="20"/></div></div>
      <div class="stat-number">{{ loading ? '—' : card.value }}</div><small>{{ card.hint }}</small>
    </article>
  </div>
  <div class="content-grid dashboard-grid">
    <section class="panel chart-panel">
      <div class="panel-heading"><div><h3>Diagnóstico BSC</h3><p>Resultados del diagnóstico académico previo</p></div><span class="tag">0–100%</span></div>
      <div class="chart-wrap"><Bar :data="chartData" :options="chartOptions"/></div>
      <div class="chart-foot"><span>Promedio general</span><strong>61,75%</strong></div>
    </section>
    <section class="panel">
      <div class="panel-heading"><div><h3>Prioridades estratégicas</h3><p>Oportunidades de mejora identificadas</p></div></div>
      <div class="priority-list">
        <div class="priority-item"><span class="priority-number">01</span><div><strong>Traslado hacia el BSC</strong><small>Formalizar indicadores, metas, responsables y cronogramas.</small></div><b class="warning-text">52%</b></div>
        <div class="priority-item"><span class="priority-number">02</span><div><strong>Cultura de ejecución</strong><small>Mejorar seguimiento, comunicación y retroalimentación.</small></div><b class="warning-text">54%</b></div>
        <div class="priority-item"><span class="priority-number">03</span><div><strong>Gobierno de TI</strong><small>Fortalecer responsabilidades, políticas y supervisión.</small></div><b class="blue-text">Prioridad</b></div>
      </div>
      <div class="note-box"><strong>Nota:</strong> el diagnóstico BSC es información académica proporcionada previamente. Los contadores superiores se consultan desde la base de datos.</div>
    </section>
  </div>
  <section class="panel principles-panel">
    <div class="panel-heading"><div><h3>Modelo de Gobierno TI</h3><p>Cuatro tareas de ISO/IEC 38500:2024</p></div></div>
    <div class="principles-grid">
      <div><span class="principle-icon blue"><Target :size="19"/></span><strong>Involucrar</strong><small>Partes interesadas</small></div>
      <div><span class="principle-icon purple"><Activity :size="19"/></span><strong>Evaluar</strong><small>Alternativas y riesgos</small></div>
      <div><span class="principle-icon green"><ArrowUpRight :size="19"/></span><strong>Dirigir</strong><small>Políticas y decisiones</small></div>
      <div><span class="principle-icon orange"><ShieldAlert :size="19"/></span><strong>Supervisar</strong><small>Resultados y desempeño</small></div>
    </div>
  </section>
</template>
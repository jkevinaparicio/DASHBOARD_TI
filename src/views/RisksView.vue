<script setup>
import { onMounted, ref } from 'vue'
import { Plus, Trash2, X } from 'lucide-vue-next'
import api from '../api'
const items=ref([]), loading=ref(false), error=ref(''), success=ref(''), showForm=ref(false)
const empty=()=>({nombre:'',descripcion:'',categoria:'CIBERSEGURIDAD',probabilidad:'MEDIA',impacto:'ALTO',nivel:'ALTO',tratamiento:'',responsable:'',estado:'ABIERTO'})
const form=ref(empty())
async function load(){loading.value=true;try{items.value=(await api.get('/riesgos')).data}catch(e){error.value='No se pudieron cargar los riesgos.'}finally{loading.value=false}}
function create(){form.value=empty();showForm.value=true;success.value=''}
async function save(){error.value='';try{await api.post('/riesgos',form.value);showForm.value=false;success.value='Riesgo registrado.';await load()}catch(e){error.value=e.response?.data?.message||'No fue posible guardar el riesgo.'}}
async function remove(id){if(!confirm('¿Eliminar este riesgo?'))return;try{await api.delete(`/riesgos/${id}`);success.value='Riesgo eliminado.';await load()}catch(e){error.value='No se pudo eliminar el riesgo.'}}
onMounted(load)
</script>
<template>
 <div class="page-heading"><div><span class="eyebrow">GESTIÓN DE RIESGOS</span><h1>Riesgos tecnológicos</h1><p>Identifica, documenta y realiza seguimiento a los riesgos de TI.</p></div><button class="btn primary" @click="create"><Plus :size="17"/> Nuevo riesgo</button></div>
 <div v-if="error" class="alert error">{{error}}</div><div v-if="success" class="alert success">{{success}}</div>
 <section v-if="showForm" class="panel form-panel">
  <div class="panel-heading"><div><h3>Registrar riesgo</h3><p>Documenta el riesgo y su tratamiento.</p></div><button class="icon-button" @click="showForm=false"><X/></button></div>
  <form class="form-grid" @submit.prevent="save">
   <div class="field span-2"><label>Nombre del riesgo *</label><input v-model="form.nombre" required placeholder="Ej. Interrupción de servicios tecnológicos"/></div>
   <div class="field span-2"><label>Descripción</label><textarea v-model="form.descripcion" rows="2"/></div>
   <div class="field"><label>Categoría *</label><select v-model="form.categoria"><option>CIBERSEGURIDAD</option><option>CONTINUIDAD</option><option>INFRAESTRUCTURA</option><option>CUMPLIMIENTO</option><option>PROVEEDORES</option><option>GESTIÓN DE ACTIVOS</option></select></div>
   <div class="field"><label>Probabilidad</label><select v-model="form.probabilidad"><option>BAJA</option><option>MEDIA</option><option>ALTA</option></select></div>
   <div class="field"><label>Impacto</label><select v-model="form.impacto"><option>BAJO</option><option>MEDIO</option><option>ALTO</option></select></div>
   <div class="field"><label>Nivel de riesgo</label><select v-model="form.nivel"><option>BAJO</option><option>MEDIO</option><option>ALTO</option><option>CRÍTICO</option></select></div>
   <div class="field span-2"><label>Tratamiento / acción de mitigación</label><textarea v-model="form.tratamiento" rows="2"/></div>
   <div class="field"><label>Responsable</label><input v-model="form.responsable"/></div>
   <div class="field"><label>Estado</label><select v-model="form.estado"><option>ABIERTO</option><option>EN_TRATAMIENTO</option><option>MITIGADO</option><option>CERRADO</option></select></div>
   <div class="form-actions span-2"><button type="button" class="btn secondary" @click="showForm=false">Cancelar</button><button class="btn primary">Guardar riesgo</button></div>
  </form>
 </section>
 <section class="panel table-panel"><div class="panel-heading"><div><h3>Registro de riesgos</h3><p>{{items.length}} riesgos identificados</p></div></div>
  <div v-if="loading" class="empty-state">Cargando...</div><div v-else-if="!items.length" class="empty-state">No hay riesgos registrados.</div>
  <div v-else class="table-scroll"><table><thead><tr><th>Riesgo</th><th>Categoría</th><th>Probabilidad</th><th>Impacto</th><th>Nivel</th><th>Estado</th><th></th></tr></thead><tbody>
   <tr v-for="r in items" :key="r.id"><td><strong>{{r.nombre}}</strong><small class="cell-sub">{{r.tratamiento}}</small></td><td>{{r.categoria}}</td><td>{{r.probabilidad}}</td><td>{{r.impacto}}</td><td><span class="risk-pill" :class="(r.nivel||'').toLowerCase()">{{r.nivel||'Sin definir'}}</span></td><td>{{r.estado}}</td><td><button class="icon-button danger-icon" @click="remove(r.id)"><Trash2 :size="16"/></button></td></tr>
  </tbody></table></div>
 </section>
</template>
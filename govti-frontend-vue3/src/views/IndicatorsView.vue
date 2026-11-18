<script setup>
import { onMounted, ref } from 'vue'
import { Plus, Trash2, X } from 'lucide-vue-next'
import api from '../api'
const items=ref([]), objectives=ref([]), loading=ref(false), error=ref(''), success=ref(''), showForm=ref(false)
const empty=()=>({nombre:'',formula:'',unidad:'%',meta:90,frecuencia:'MENSUAL',responsable:'',objetivo:{id:''}})
const form=ref(empty())
async function load(){
 loading.value=true;error.value=''
 try{const [a,b]=await Promise.all([api.get('/indicadores'),api.get('/objetivos')]);items.value=a.data;objectives.value=b.data}
 catch(e){error.value='No se pudieron cargar los indicadores u objetivos.'}
 finally{loading.value=false}
}
function create(){form.value=empty();showForm.value=true;success.value=''}
async function save(){
 error.value='';success.value=''
 try{
  const payload={...form.value,objetivo:{id:Number(form.value.objetivo.id)},meta:Number(form.value.meta)}
  await api.post('/indicadores',payload);showForm.value=false;success.value='Indicador creado correctamente.';await load()
 }catch(e){error.value=e.response?.data?.message||'No fue posible guardar el indicador.'}
}
async function remove(id){if(!confirm('¿Eliminar este indicador?'))return;try{await api.delete(`/indicadores/${id}`);success.value='Indicador eliminado.';await load()}catch(e){error.value='No se pudo eliminar el indicador.'}}
onMounted(load)
</script>
<template>
 <div class="page-heading"><div><span class="eyebrow">MEDICIÓN DEL DESEMPEÑO</span><h1>Indicadores estratégicos</h1><p>Define fórmulas, metas y responsables de medición.</p></div><button class="btn primary" @click="create"><Plus :size="17"/> Nuevo indicador</button></div>
 <div v-if="error" class="alert error">{{error}}</div><div v-if="success" class="alert success">{{success}}</div>
 <section v-if="showForm" class="panel form-panel">
  <div class="panel-heading"><div><h3>Registrar indicador</h3><p>Asocia el indicador con un objetivo estratégico.</p></div><button class="icon-button" @click="showForm=false"><X/></button></div>
  <form class="form-grid" @submit.prevent="save">
   <div class="field span-2"><label>Nombre *</label><input v-model="form.nombre" required placeholder="Ej. Porcentaje de riesgos tratados"/></div>
   <div class="field span-2"><label>Fórmula de cálculo</label><textarea v-model="form.formula" rows="2" placeholder="Riesgos tratados / riesgos identificados * 100"/></div>
   <div class="field"><label>Objetivo relacionado *</label><select v-model="form.objetivo.id" required><option value="" disabled>Selecciona un objetivo</option><option v-for="o in objectives" :key="o.id" :value="o.id">{{o.nombre}}</option></select><small v-if="!objectives.length" class="field-help">Primero debes crear un objetivo.</small></div>
   <div class="field"><label>Responsable</label><input v-model="form.responsable" placeholder="Responsable TI"/></div>
   <div class="field"><label>Meta</label><input v-model.number="form.meta" type="number" min="0" step="0.01"/></div>
   <div class="field"><label>Unidad</label><select v-model="form.unidad"><option>%</option><option>Horas</option><option>Días</option><option>Unidades</option></select></div>
   <div class="field"><label>Frecuencia</label><select v-model="form.frecuencia"><option>MENSUAL</option><option>TRIMESTRAL</option><option>SEMESTRAL</option><option>ANUAL</option></select></div>
   <div class="form-actions span-2"><button type="button" class="btn secondary" @click="showForm=false">Cancelar</button><button class="btn primary" :disabled="!objectives.length">Guardar indicador</button></div>
  </form>
 </section>
 <section class="panel table-panel"><div class="panel-heading"><div><h3>Indicadores registrados</h3><p>{{items.length}} indicadores configurados</p></div></div>
  <div v-if="loading" class="empty-state">Cargando...</div><div v-else-if="!items.length" class="empty-state">No hay indicadores registrados.</div>
  <div v-else class="table-scroll"><table><thead><tr><th>Indicador</th><th>Objetivo</th><th>Meta</th><th>Frecuencia</th><th>Responsable</th><th></th></tr></thead><tbody>
   <tr v-for="i in items" :key="i.id"><td><strong>{{i.nombre}}</strong><small class="cell-sub">{{i.formula}}</small></td><td>{{i.objetivo?.nombre||'—'}}</td><td>{{i.meta??'—'}} {{i.unidad}}</td><td>{{i.frecuencia||'—'}}</td><td>{{i.responsable||'—'}}</td><td><button class="icon-button danger-icon" @click="remove(i.id)"><Trash2 :size="16"/></button></td></tr>
  </tbody></table></div>
 </section>
</template>
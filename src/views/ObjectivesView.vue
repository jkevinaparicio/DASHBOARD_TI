<script setup>
import { onMounted, ref } from 'vue'
import { Plus, Trash2, Pencil, X } from 'lucide-vue-next'
import api from '../api'
const items = ref([])
const loading = ref(false), error = ref(''), success = ref('')
const showForm = ref(false), editingId = ref(null)
const empty = () => ({ nombre:'', descripcion:'', perspectiva:'PROCESOS INTERNOS', responsable:'', fechaInicio:'', fechaFin:'', estado:'PENDIENTE' })
const form = ref(empty())
async function load(){ loading.value=true; error.value=''; try{items.value=(await api.get('/objetivos')).data} catch(e){error.value='No se pudieron cargar los objetivos.'} finally{loading.value=false} }
function create(){ editingId.value=null; form.value=empty(); showForm.value=true; success.value='' }
function edit(item){ editingId.value=item.id; form.value={...item}; showForm.value=true; success.value='' }
async function save(){
  error.value=''; success.value=''
  try{
    if(editingId.value) await api.put(`/objetivos/${editingId.value}`,form.value)
    else await api.post('/objetivos',form.value)
    showForm.value=false; success.value='Objetivo guardado correctamente.'; await load()
  }catch(e){error.value=e.response?.data?.message||'No fue posible guardar el objetivo.'}
}
async function remove(id){
  if(!confirm('¿Deseas eliminar este objetivo?')) return
  try{await api.delete(`/objetivos/${id}`);success.value='Objetivo eliminado.';await load()}
  catch(e){error.value='No se pudo eliminar. Verifica si tiene indicadores asociados.'}
}
onMounted(load)
</script>
<template>
  <div class="page-heading"><div><span class="eyebrow">BALANCED SCORECARD</span><h1>Objetivos estratégicos</h1><p>Administra los objetivos y sus responsables.</p></div><button class="btn primary" @click="create"><Plus :size="17"/> Nuevo objetivo</button></div>
  <div v-if="error" class="alert error">{{error}}</div><div v-if="success" class="alert success">{{success}}</div>
  <section v-if="showForm" class="panel form-panel">
    <div class="panel-heading"><div><h3>{{editingId?'Editar objetivo':'Registrar objetivo'}}</h3><p>Completa la información estratégica.</p></div><button class="icon-button" @click="showForm=false"><X/></button></div>
    <form class="form-grid" @submit.prevent="save">
      <div class="field span-2"><label>Nombre del objetivo *</label><input v-model="form.nombre" required placeholder="Ej. Fortalecer la seguridad de la información"/></div>
      <div class="field span-2"><label>Descripción</label><textarea v-model="form.descripcion" rows="3" placeholder="Describe el resultado esperado"></textarea></div>
      <div class="field"><label>Perspectiva BSC *</label><select v-model="form.perspectiva" required><option>FINANCIERA</option><option>CLIENTES</option><option>PROCESOS INTERNOS</option><option>APRENDIZAJE Y CRECIMIENTO</option></select></div>
      <div class="field"><label>Responsable</label><input v-model="form.responsable" placeholder="CIO / Responsable TI"/></div>
      <div class="field"><label>Fecha de inicio</label><input v-model="form.fechaInicio" type="date"/></div>
      <div class="field"><label>Fecha de finalización</label><input v-model="form.fechaFin" type="date"/></div>
      <div class="field"><label>Estado</label><select v-model="form.estado"><option>PENDIENTE</option><option>EN_CURSO</option><option>CUMPLIDO</option><option>ATRASADO</option></select></div>
      <div class="form-actions span-2"><button type="button" class="btn secondary" @click="showForm=false">Cancelar</button><button class="btn primary">Guardar objetivo</button></div>
    </form>
  </section>
  <section class="panel table-panel">
    <div class="panel-heading"><div><h3>Listado de objetivos</h3><p>{{items.length}} objetivos registrados</p></div></div>
    <div v-if="loading" class="empty-state">Cargando...</div>
    <div v-else-if="!items.length" class="empty-state">Aún no hay objetivos. Registra el primero.</div>
    <div v-else class="table-scroll"><table><thead><tr><th>Objetivo</th><th>Perspectiva</th><th>Responsable</th><th>Estado</th><th>Acciones</th></tr></thead><tbody>
      <tr v-for="item in items" :key="item.id"><td><strong>{{item.nombre}}</strong><small class="cell-sub">{{item.descripcion}}</small></td><td>{{item.perspectiva}}</td><td>{{item.responsable||'—'}}</td><td><span class="status-pill">{{item.estado}}</span></td><td><div class="actions"><button class="icon-button" title="Editar" @click="edit(item)"><Pencil :size="16"/></button><button class="icon-button danger-icon" title="Eliminar" @click="remove(item.id)"><Trash2 :size="16"/></button></div></td></tr>
    </tbody></table></div>
  </section>
</template>
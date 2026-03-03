<template>
  <div class="page-content">
    <div class="ruta-header">
      <button class="btn-outline" @click="router.back()">← Volver</button>
      <h1 class="page-title">Mapa Interactivo del Campus</h1>
    </div>

    <div class="card controles-mapa">
      <div class="selector-grupo">
        <label>¿En qué PIU te encuentras? (Origen):</label>
        <select v-model="piuSeleccionado" class="input-select">
          <option value="" disabled>Selecciona un PIU...</option>
          <option v-for="piu in listaPius" :key="piu.id_piu" :value="piu.id_piu">
            {{ piu.codigoPiu }}
          </option>
        </select>
      </div>

      <div class="selector-grupo">
        <label>¿A dónde vas? (Destino):</label>
        <select v-model="destinoSeleccionado" class="input-select">
          <option value="" disabled>Selecciona un destino...</option>
          
          <optgroup label="SALAS">
            <option v-for="sala in listaSalas" :key="'s-'+sala.id_sala" :value="'sala-' + sala.id_sala">
              Sala {{ sala.codigoSala }} (Piso {{ sala.pisoSala }})
            </option>
          </optgroup>
          <optgroup label = "EDIFICIOS">
            <option v-for=  "edificio in listaEdificios" :key="'e-'+edificio.id_edificio":value=" 'edificio-' + edificio.id_edificio" >
              Edificio {{ edificio.nombreEdificio }}
            </option>
          </optgroup>

          <optgroup label="PIU´S">
            <option v-for="piu in listaPius" :key="'p-'+piu.id_piu" :value="'piu-' + piu.id_piu">
              PIU {{ piu.codigoPiu }}
            </option>
          </optgroup>
        </select>
      </div>

      <button 
        class="btn-trazar" 
        @click="calcularRuta" 
        :disabled="!piuSeleccionado || !destinoSeleccionado">
        Definir Ruta
      </button>
    </div>

    <div class="card mapa-container mt-3">
      <div id="mapaReal" style="height: 600px; width: 100%; border-radius: 8px; z-index: 1;"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api.js'

import 'leaflet/dist/leaflet.css'
import L from 'leaflet'
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css'
import 'leaflet-routing-machine'

const router = useRouter()

const listaPius = ref([])
const listaSalas = ref([])
const listaEdificios = ref([]) 

const piuSeleccionado = ref("")
const destinoSeleccionado = ref("") // Guardará algo como "sala-5" o "piu-2"

let map = null;
let controlDeRuta = null;

onMounted(async () => {
  map = L.map('mapaReal').setView([-33.448, -70.682], 16);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
  }).addTo(map);

  try {
    // Pedimos los datos al backend
    const resPius = await api.get('/pius');
    listaPius.value = resPius.data;
    console.log("PIUs cargados:", listaPius.value); // <-- Útil para ver si llegan vacíos

    const resSalas = await api.get('/salas');
    listaSalas.value = resSalas.data;
    console.log("Salas cargadas:", listaSalas.value); 

    const resEdificios = await api.get('/edificios');
    listaEdificios.value = resEdificios.data;
    console.log("Edificios cargados:", listaEdificios.value); 

  } catch (error) {
    console.error("Error crítico al pedir las listas. Revisa si necesitas Iniciar Sesión:", error);
  }
});

const calcularRuta = async () => {
  try {
    if (controlDeRuta) {
      map.removeControl(controlDeRuta);
    }

    // Como el destino puede ser "sala-5" o "piu-2", lo dividimos.
    const [tipo, id] = destinoSeleccionado.value.split('-');

    // Llamamos al nuevo método que hiciste en Spring Boot
    const url = `/rutas/coordenadas?idPiuOrigen=${piuSeleccionado.value}&tipoDestino=${tipo}&idDestino=${id}`;
    const response = await api.get(url);
    const datos = response.data;

    controlDeRuta = L.Routing.control({
      waypoints: [
        L.latLng(datos.origen.lat, datos.origen.lng),
        L.latLng(datos.destino.lat, datos.destino.lng)
      ],
      routeWhileDragging: false,
      addWaypoints: false,
      language: 'es',
      router: L.Routing.osrmv1({ profile: 'foot' })
    }).addTo(map);

  } catch (error) {
    console.error("Error al calcular:", error);
    alert("Hubo un error trazando la ruta. Confirma en PgAdmin que este elemento tenga latitud y longitud.");
  }
};
</script>

<style scoped>
.ruta-header { display: flex; align-items: center; gap: 1.25rem; margin-bottom: 1.25rem; }
.page-title { color: var(--color-primary); font-size: 1.5rem; font-weight: 700; margin: 0; }
.mapa-container { padding: 10px; background-color: white; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
.controles-mapa { display: flex; flex-wrap: wrap; gap: 20px; padding: 20px; background-color: white; align-items: flex-end; }
.selector-grupo { display: flex; flex-direction: column; gap: 8px; flex: 1; min-width: 200px; }
.selector-grupo label { font-weight: bold; color: #333; }
.input-select { padding: 10px; border-radius: 6px; border: 1px solid #ccc; font-size: 1rem; }
.btn-trazar { background-color: #4CAF50; color: white; border: none; padding: 12px 24px; border-radius: 6px; font-weight: bold; cursor: pointer; transition: background-color 0.3s; }
.btn-trazar:hover { background-color: #45a049; }
.btn-trazar:disabled { background-color: #cccccc; cursor: not-allowed; }
.mt-3 { margin-top: 1rem; }
:deep(.leaflet-routing-container) { background-color: white; padding: 10px; border-radius: 8px; max-height: 400px; overflow-y: auto; color: black; }
</style>
package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.edificioEntity;
import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Entity.rutaEntity;
import com.pruebafin.cl.Entity.salaEntity;
import com.pruebafin.cl.Service.edificioService;
import com.pruebafin.cl.Service.piuService;
import com.pruebafin.cl.Service.rutaService;
import com.pruebafin.cl.Service.salaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin(origins = "*")
public class rutaController {
    private rutaService rutaSrvc;

    @Autowired
    public rutaController(rutaService rutaSrvc) {
        this.rutaSrvc = rutaSrvc;
    }


    @Autowired
    private piuService piuSrvc;

    @Autowired
    private salaService salaSrvc;

    @Autowired
    private edificioService edificioSrvc;

    @GetMapping
    public ResponseEntity<List<rutaEntity>> obtenerTodasLasRutas(){
        List<rutaEntity> rutas = rutaSrvc.obtenerAllRutas();
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<rutaEntity> obtenerRutaPorId(@PathVariable Long id){
        return rutaSrvc.obtenerRutaById(id)
                .map(rutaId-> new ResponseEntity<>(rutaId, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<rutaEntity> crearRuta(@RequestBody rutaEntity ruta){
        rutaEntity nuevaRuta = rutaSrvc.guardarRuta(ruta);
        return new ResponseEntity<>(nuevaRuta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<rutaEntity> actualizarRuta(@PathVariable Long id, @RequestBody rutaEntity ruta){
        return rutaSrvc.obtenerRutaById(id)
                .map(rutaExiste -> {
                    ruta.setId_ruta(id);
                    return new ResponseEntity<>(rutaSrvc.actualizarRuta(ruta), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRutaPorId(@PathVariable Long id){
        return rutaSrvc.obtenerRutaById(id)
                .map(ruta -> {
                    rutaSrvc.eliminarRuta(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/coordenadas")
    public ResponseEntity<?> obtenerCoordenadasRuta(
            @RequestParam Long idPiuOrigen,
            @RequestParam String tipoDestino, // Recibe "sala" o "piu"
            @RequestParam Long idDestino) {

        // 1. Buscamos el origen (Siempre es un PIU)
        piuEntity origen = piuSrvc.obtenerPiuById(idPiuOrigen).orElseThrow();
        Map<String, Double> mapOrigen = new HashMap<>();
        mapOrigen.put("lat", origen.getLatitudPiu());
        mapOrigen.put("lng", origen.getLongitudPiu());

        // 2. Buscamos el Destino dependiendo de si es "sala" o "piu"
        Map<String, Double> mapDestino = new HashMap<>();

        if ("sala".equalsIgnoreCase(tipoDestino)) {
            salaEntity destino = salaSrvc.obtenerSalaPorId(idDestino).orElseThrow();
            mapDestino.put("lat", destino.getLatitudSala());
            mapDestino.put("lng", destino.getLongitudSala());

        } else if ("piu".equalsIgnoreCase(tipoDestino)) {
            piuEntity destino = piuSrvc.obtenerPiuById(idDestino).orElseThrow();
            mapDestino.put("lat", destino.getLatitudPiu());
            mapDestino.put("lng", destino.getLongitudPiu());
        }

        else if ("edificio".equalsIgnoreCase(tipoDestino)) {
            edificioEntity destino = edificioSrvc.obtenerEdificioPorId(idDestino).orElseThrow();
            mapDestino.put("lat", destino.getLatitudEdificio());
            mapDestino.put("lng", destino.getLongitudEdificio());
        }

        // 3. Juntamos ambas y las devolvemos al mapa
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("origen", mapOrigen);
        respuesta.put("destino", mapDestino);

        return ResponseEntity.ok(respuesta);
    }


}

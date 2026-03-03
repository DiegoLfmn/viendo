package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.piuEntity;
import com.pruebafin.cl.Entity.rutaEntity;
import com.pruebafin.cl.Entity.salaEntity;
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
            @RequestParam Long idPiu,
            @RequestParam Long idSala) {

        // 1. Buscas el PIU en la base de datos (con tu piuService)
        piuEntity piu = piuSrvc.obtenerPiuById(idPiu).orElseThrow();

        // 2. Buscas la Sala en la base de datos (con tu salaService)
        salaEntity sala = salaSrvc.obtenerSalaPorId(idSala).orElseThrow();

        // 3. Creas un JSON de respuesta con las coordenadas de ambos
        Map<String, Object> respuesta = new HashMap<>();

        Map<String, Double> origen = new HashMap<>();
        origen.put("lat", piu.getLatitudPiu());
        origen.put("lng", piu.getLongitudPiu());

        Map<String, Double> destino = new HashMap<>();
        destino.put("lat", sala.getLatitudSala());
        destino.put("lng", sala.getLongitudSala());

        respuesta.put("origen", origen);
        respuesta.put("destino", destino);

        return ResponseEntity.ok(respuesta);
    }
}

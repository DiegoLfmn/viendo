package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.rutaEntity;
import com.pruebafin.cl.Service.rutaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin(origins = "*")
public class rutaController {
    private rutaService rutaSrvc;

    @Autowired
    public rutaController(rutaService rutaSrvc) {
        this.rutaSrvc = rutaSrvc;
    }

    @GetMapping
    public ResponseEntity<List<rutaEntity>> obtenerTodasLasRutas(){
        List<rutaEntity> rutas = rutaSrvc.obtenerAllRutas();
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @GetMapping("/api/rutas/{id}")
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

    @DeleteMapping
    public ResponseEntity<Void> eliminarRutaPorId(@PathVariable Long id){
        return rutaSrvc.obtenerRutaById(id)
                .map(ruta -> {
                    rutaSrvc.eliminarRuta(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

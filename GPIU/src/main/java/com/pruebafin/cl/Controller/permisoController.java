package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.permisoEntity;
import com.pruebafin.cl.Service.permisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@CrossOrigin(origins = "*")
public class permisoController {
    private permisoService permisoSrvc;

    @Autowired
    public permisoController(permisoService permisoSrvc) {
        this.permisoSrvc = permisoSrvc;
    }

    @GetMapping
    public ResponseEntity<List<permisoEntity>> obtenerTodosLosPermisos(){
        List<permisoEntity> permisos = permisoSrvc.obtenerAllPermisos();
        return new ResponseEntity<>(permisos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<permisoEntity> obtenerPermisoPorId(@PathVariable Long id){
        return permisoSrvc.obtenerPermisosPorId(id)
                .map(permiso -> new ResponseEntity<>(permiso, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<permisoEntity> crearPermiso(@RequestBody permisoEntity permiso){
        permisoEntity nuevoPermiso = permisoSrvc.guardarPermiso(permiso);
        return new ResponseEntity<>(nuevoPermiso, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<permisoEntity> actualizarPermiso(@PathVariable Long id, @RequestBody permisoEntity permiso){
        return permisoSrvc.obtenerPermisosPorId(id)
                .map(permisoExiste -> {
                    permiso.setId_permiso(id);
                    return new ResponseEntity<>(permisoSrvc.actualizarPermiso(permiso), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPermisoPorId(@PathVariable Long id){
        return permisoSrvc.obtenerPermisosPorId(id)
                .map(permiso -> {
                    permisoSrvc.eliminarPermisos(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

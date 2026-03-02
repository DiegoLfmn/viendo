package com.pruebafin.cl.Controller;

import com.pruebafin.cl.Entity.rolEntity;
import com.pruebafin.cl.Service.rolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class rolPermiso {
    private rolService rolSrvc;

    @Autowired
    public rolPermiso(rolService rolSrvc) {
        this.rolSrvc = rolSrvc;
    }

    @GetMapping
    public ResponseEntity<List<rolEntity>> obtenerTodosLosRoles(){
        List<rolEntity> roles = rolSrvc.obtenerRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<rolEntity> obtenerRolPorId(@PathVariable Long id){
        return rolSrvc.obtenerRolPorId(id)
                .map(rol -> new ResponseEntity<>(rol, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<rolEntity> crearRol(@RequestBody rolEntity rol){
        rolEntity nuevoRol = rolSrvc.guardarRol(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<rolEntity> actualizarRol(@PathVariable Long id, @RequestBody rolEntity rol){
        return rolSrvc.obtenerRolPorId(id)
                .map(rolExiste -> {
                    rol.setId_rol(id);
                    return new ResponseEntity<>(rolSrvc.actualizarRol(rol), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRolPorId(@PathVariable Long id){
        return rolSrvc.obtenerRolPorId(id)
                .map(rol -> {
                    rolSrvc.eliminarRol(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

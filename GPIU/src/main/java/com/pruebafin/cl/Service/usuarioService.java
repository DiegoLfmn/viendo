package com.pruebafin.cl.Service;
import com.pruebafin.cl.Repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pruebafin.cl.Entity.usuarioEntity;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {
    private usuarioRepository usuarioRepo;

    @Autowired
    public usuarioService(usuarioRepository usuarioRepo){
        this.usuarioRepo = usuarioRepo;
    }

    public usuarioEntity guardarUsuario(usuarioEntity usuario){
        return usuarioRepo.save(usuario);
    }

    public List<usuarioEntity> obtenerAllUsers(){
        return usuarioRepo.findAll();
    }

    public Optional<usuarioEntity> obtenerUsuarioPorId(Long id){

        return usuarioRepo.findById(id);
    }

    public usuarioEntity actualizarUsuario(usuarioEntity usuario){

        return usuarioRepo.save(usuario);
    }
    public void  eliminarUsuario(Long id){
        usuarioRepo.deleteById(id);
    }

    public Optional<usuarioEntity> obtenerPorCorreo(String correo){

        return usuarioRepo.findByCorreoUsuario(correo);
    }

    public List<usuarioEntity> obtenerPorNombreUsuario(String nombre){
        return usuarioRepo.findByNombreUsuario(nombre);
    }
}

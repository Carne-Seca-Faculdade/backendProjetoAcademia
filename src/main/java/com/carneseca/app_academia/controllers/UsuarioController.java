package com.carneseca.app_academia.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carneseca.app_academia.entities.UsuarioEntity;
import com.carneseca.app_academia.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioEntity> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@PathVariable UUID id) {
        Optional<UsuarioEntity> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioEntity createUsuario(@RequestBody UsuarioEntity usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(@PathVariable UUID id, @RequestBody UsuarioEntity usuarioDetails) {
        Optional<UsuarioEntity> existingUsuario = usuarioService.findById(id);
        if (existingUsuario.isPresent()) {
            UsuarioEntity usuario = existingUsuario.get();
            usuario.setNome(usuarioDetails.getNome());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setDataNascimento(usuarioDetails.getDataNascimento());
            usuario.setGenero(usuarioDetails.getGenero());
            usuario.setPeso(usuarioDetails.getPeso());
            usuario.setAltura(usuarioDetails.getAltura());
            usuario.setExercicios(usuarioDetails.getExercicios());
            usuario.setTreinos(usuarioDetails.getTreinos());
            UsuarioEntity updatedUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

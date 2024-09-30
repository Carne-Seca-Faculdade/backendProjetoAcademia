package com.carneseca.app_academia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.carneseca.app_academia.entities.UsuarioEntity;
import com.carneseca.app_academia.repositories.UsuarioRepository;

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void findAll_shouldReturnListOfUsuarioEntities() {
        UUID usuarioId = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(usuarioId);
        List<UsuarioEntity> expectedList = List.of(usuario);

        when(usuarioRepository.findAll()).thenReturn(expectedList);

        List<UsuarioEntity> result = usuarioService.findAll();

        assertEquals(expectedList, result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoEntitiesExist() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<UsuarioEntity> result = usuarioService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldReturnUsuarioEntityWhenIdExists() {
        UUID usuarioId = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(usuarioId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        Optional<UsuarioEntity> result = usuarioService.findById(usuarioId);

        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        UUID usuarioId = UUID.randomUUID();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        Optional<UsuarioEntity> result = usuarioService.findById(usuarioId);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnUsuarioEntity() {
        UUID usuarioId = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(usuarioId);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioEntity result = usuarioService.save(usuario);

        assertEquals(usuario, result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteById_shouldRemoveUsuarioEntity() {
        UUID usuarioId = UUID.randomUUID();

        usuarioService.deleteById(usuarioId);

        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }
}

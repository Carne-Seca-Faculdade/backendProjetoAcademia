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

import com.carneseca.app_academia.entities.DiaTreinoEntity;
import com.carneseca.app_academia.repositories.DiaTreinoRepository;

@SpringBootTest
class DiaTreinoServiceTest {

    @Autowired
    private DiaTreinoService diaTreinoService;

    @MockBean
    private DiaTreinoRepository diaTreinoRepository;

    @Test
    void findAll_shouldReturnListOfDiaTreinoEntities() {
        UUID diaTreinoId = UUID.randomUUID();
        DiaTreinoEntity diaTreino = new DiaTreinoEntity();
        diaTreino.setId(diaTreinoId);
        List<DiaTreinoEntity> expectedList = List.of(diaTreino);

        when(diaTreinoRepository.findAll()).thenReturn(expectedList);

        List<DiaTreinoEntity> result = diaTreinoService.findAll();

        assertEquals(expectedList, result);
        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnDiaTreinoEntityWhenIdExists() {
        UUID diaTreinoId = UUID.randomUUID();
        DiaTreinoEntity diaTreino = new DiaTreinoEntity();
        diaTreino.setId(diaTreinoId);

        when(diaTreinoRepository.findById(diaTreinoId)).thenReturn(Optional.of(diaTreino));

        Optional<DiaTreinoEntity> result = diaTreinoService.findById(diaTreinoId);

        assertTrue(result.isPresent());
        assertEquals(diaTreino, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        UUID diaTreinoId = UUID.randomUUID();

        when(diaTreinoRepository.findById(diaTreinoId)).thenReturn(Optional.empty());

        Optional<DiaTreinoEntity> result = diaTreinoService.findById(diaTreinoId);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnDiaTreinoEntity() {
        UUID diaTreinoId = UUID.randomUUID();
        DiaTreinoEntity diaTreino = new DiaTreinoEntity();
        diaTreino.setId(diaTreinoId);

        when(diaTreinoRepository.save(diaTreino)).thenReturn(diaTreino);

        DiaTreinoEntity result = diaTreinoService.save(diaTreino);

        assertEquals(diaTreino, result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteById_shouldRemoveDiaTreinoEntity() {
        UUID diaTreinoId = UUID.randomUUID();

        diaTreinoService.deleteById(diaTreinoId);

        verify(diaTreinoRepository, times(1)).deleteById(diaTreinoId);
    }
}

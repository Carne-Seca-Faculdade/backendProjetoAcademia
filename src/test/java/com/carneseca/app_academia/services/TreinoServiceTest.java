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

import com.carneseca.app_academia.entities.TreinoEntity;
import com.carneseca.app_academia.repositories.TreinoRepository;

@SpringBootTest
class TreinoServiceTest {

    @Autowired
    private TreinoService treinoService;

    @MockBean
    private TreinoRepository treinoRepository;

    @Test
    void findAll_shouldReturnListOfTreinoEntities() {
        UUID treinoId = UUID.randomUUID();
        TreinoEntity treino = new TreinoEntity();
        treino.setId(treinoId);
        List<TreinoEntity> expectedList = List.of(treino);

        when(treinoRepository.findAll()).thenReturn(expectedList);

        List<TreinoEntity> result = treinoService.findAll();

        assertEquals(expectedList, result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoEntitiesExist() {
        when(treinoRepository.findAll()).thenReturn(List.of());

        List<TreinoEntity> result = treinoService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldReturnTreinoEntityWhenIdExists() {
        UUID treinoId = UUID.randomUUID();
        TreinoEntity treino = new TreinoEntity();
        treino.setId(treinoId);

        when(treinoRepository.findById(treinoId)).thenReturn(Optional.of(treino));

        Optional<TreinoEntity> result = treinoService.findById(treinoId);

        assertTrue(result.isPresent());
        assertEquals(treino, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        UUID treinoId = UUID.randomUUID();

        when(treinoRepository.findById(treinoId)).thenReturn(Optional.empty());

        Optional<TreinoEntity> result = treinoService.findById(treinoId);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnTreinoEntity() {
        UUID treinoId = UUID.randomUUID();
        TreinoEntity treino = new TreinoEntity();
        treino.setId(treinoId);

        when(treinoRepository.save(treino)).thenReturn(treino);

        TreinoEntity result = treinoService.save(treino);

        assertEquals(treino, result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteById_shouldRemoveTreinoEntity() {
        UUID treinoId = UUID.randomUUID();

        treinoService.deleteById(treinoId);

        verify(treinoRepository, times(1)).deleteById(treinoId);
    }
}

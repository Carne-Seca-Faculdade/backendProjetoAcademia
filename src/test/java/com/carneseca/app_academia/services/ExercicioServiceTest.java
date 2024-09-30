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

import com.carneseca.app_academia.entities.ExercicioEntity;
import com.carneseca.app_academia.repositories.ExercicioRepository;

@SpringBootTest
class ExercicioServiceTest {

    @Autowired
    private ExercicioService exercicioService;

    @MockBean
    private ExercicioRepository exercicioRepository;

    @Test
    void findAll_shouldReturnListOfExercicioEntities() {
        UUID exercicioId = UUID.randomUUID();
        ExercicioEntity exercicio = new ExercicioEntity();
        exercicio.setId(exercicioId);
        List<ExercicioEntity> expectedList = List.of(exercicio);

        when(exercicioRepository.findAll()).thenReturn(expectedList);

        List<ExercicioEntity> result = exercicioService.findAll();

        assertEquals(expectedList, result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoEntitiesExist() {
        when(exercicioRepository.findAll()).thenReturn(List.of());

        List<ExercicioEntity> result = exercicioService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldReturnExercicioEntityWhenIdExists() {
        UUID exercicioId = UUID.randomUUID();
        ExercicioEntity exercicio = new ExercicioEntity();
        exercicio.setId(exercicioId);

        when(exercicioRepository.findById(exercicioId)).thenReturn(Optional.of(exercicio));

        Optional<ExercicioEntity> result = exercicioService.findById(exercicioId);

        assertTrue(result.isPresent());
        assertEquals(exercicio, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        UUID exercicioId = UUID.randomUUID();

        when(exercicioRepository.findById(exercicioId)).thenReturn(Optional.empty());

        Optional<ExercicioEntity> result = exercicioService.findById(exercicioId);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnExercicioEntity() {
        UUID exercicioId = UUID.randomUUID();
        ExercicioEntity exercicio = new ExercicioEntity();
        exercicio.setId(exercicioId);

        when(exercicioRepository.save(exercicio)).thenReturn(exercicio);

        ExercicioEntity result = exercicioService.save(exercicio);

        assertEquals(exercicio, result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteById_shouldRemoveExercicioEntity() {
        UUID exercicioId = UUID.randomUUID();

        exercicioService.deleteById(exercicioId);

        verify(exercicioRepository, times(1)).deleteById(exercicioId);
    }
}

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

import com.carneseca.app_academia.entities.SerieEntity;
import com.carneseca.app_academia.repositories.SerieRepository;

@SpringBootTest
class SerieServiceTest {

    @Autowired
    private SerieService serieService;

    @MockBean
    private SerieRepository serieRepository;

    @Test
    void findAll_shouldReturnListOfSerieEntities() {
        UUID serieId = UUID.randomUUID();
        SerieEntity serie = new SerieEntity();
        serie.setId(serieId);
        List<SerieEntity> expectedList = List.of(serie);

        when(serieRepository.findAll()).thenReturn(expectedList);

        List<SerieEntity> result = serieService.findAll();

        assertEquals(expectedList, result);
        assertEquals(1, result.size());
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoEntitiesExist() {
        when(serieRepository.findAll()).thenReturn(List.of());

        List<SerieEntity> result = serieService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldReturnSerieEntityWhenIdExists() {
        UUID serieId = UUID.randomUUID();
        SerieEntity serie = new SerieEntity();
        serie.setId(serieId);

        when(serieRepository.findById(serieId)).thenReturn(Optional.of(serie));

        Optional<SerieEntity> result = serieService.findById(serieId);

        assertTrue(result.isPresent());
        assertEquals(serie, result.get());
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        UUID serieId = UUID.randomUUID();

        when(serieRepository.findById(serieId)).thenReturn(Optional.empty());

        Optional<SerieEntity> result = serieService.findById(serieId);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistAndReturnSerieEntity() {
        UUID serieId = UUID.randomUUID();
        SerieEntity serie = new SerieEntity();
        serie.setId(serieId);

        when(serieRepository.save(serie)).thenReturn(serie);

        SerieEntity result = serieService.save(serie);

        assertEquals(serie, result);
        assertNotNull(result.getId());
    }

    @Test
    void deleteById_shouldRemoveSerieEntity() {
        UUID serieId = UUID.randomUUID();

        serieService.deleteById(serieId);

        verify(serieRepository, times(1)).deleteById(serieId);
    }
}

package com.carneseca.app_academia.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.UUID;

import com.carneseca.app_academia.entities.TreinoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.carneseca.app_academia.entities.SerieEntity;
import com.carneseca.app_academia.repositories.SerieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SerieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private SerieEntity serie;

    @BeforeEach
    void setUp() throws Exception {
        TreinoEntity treino = new TreinoEntity();
        treino.setNomeTreino("Treino A");

        serie = new SerieEntity();
        serie.setRepeticoes(10);
        serie.setCarga(50);
        serie.setDescansoSerie(60);
        serie.setTreino(treino);
        serieRepository.save(serie);
    }

    @Test
    void getAllSeries_deveRetornarListaDeSeries() throws Exception {
        mockMvc.perform(get("/api/series")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].treino").value("Treino A"))
                .andDo(print());
    }

    @Test
    void getSerieById_deveRetornarSerie() throws Exception {
        mockMvc.perform(get("/api/series/{id}", serie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.treino").value("Treino A"))
                .andDo(print());
    }

    @Test
    void getSerieById_deveRetornarNaoEncontradoQuandoEntidadeNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/series/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createSerie_deveCriarERetornarSerie() throws Exception {
        TreinoEntity treino = new TreinoEntity();
        treino.setNomeTreino("Treino B");

        SerieEntity novaSerie = new SerieEntity();
        novaSerie.setRepeticoes(12);
        novaSerie.setCarga(60);
        novaSerie.setDescansoSerie(90);
        novaSerie.setTreino(treino);

        mockMvc.perform(post("/api/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaSerie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.treino").value("Treino B"))
                .andDo(print());
    }

    @Test
    void updateSerie_deveAtualizarERetornarSerieAtualizada() throws Exception {
        serie.setRepeticoes(15);

        mockMvc.perform(put("/api/series/{id}", serie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repeticoes").value(15))
                .andDo(print());
    }

    @Test
    void updateSerie_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();
        SerieEntity novaSerie = new SerieEntity();
        novaSerie.setRepeticoes(10);

        mockMvc.perform(put("/api/series/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaSerie)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteSerie_deveDeletarSerie() throws Exception {
        mockMvc.perform(delete("/api/series/{id}", serie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteSerie_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/series/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

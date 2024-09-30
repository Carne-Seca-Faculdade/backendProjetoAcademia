package com.carneseca.app_academia.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Date;
import java.util.UUID;

import com.carneseca.app_academia.entities.TreinoEntity;
import com.carneseca.app_academia.repositories.TreinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TreinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private TreinoEntity treino;

    @BeforeEach
    void setUp() throws Exception {
        treino = new TreinoEntity();
        treino.setNomeTreino("Treino A");
        treino.setDescricao("Descrição do treino A");
        treino.setDuracao(60);
        treino.setCriadoEm(new Date());
        treinoRepository.save(treino);
    }

    @Test
    void getAllTreinos_deveRetornarListaDeTreinos() throws Exception {
        mockMvc.perform(get("/api/treinos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nomeTreino").value("Treino A"))
                .andDo(print());
    }

    @Test
    void getTreinoById_deveRetornarTreino() throws Exception {
        mockMvc.perform(get("/api/treinos/{id}", treino.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTreino").value("Treino A"))
                .andDo(print());
    }

    @Test
    void getTreinoById_deveRetornarNaoEncontradoQuandoEntidadeNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createTreino_deveCriarERetornarTreino() throws Exception {
        TreinoEntity novoTreino = new TreinoEntity();
        novoTreino.setNomeTreino("Treino B");
        novoTreino.setDescricao("Descrição do treino B");
        novoTreino.setDuracao(45);
        novoTreino.setCriadoEm(new Date());

        mockMvc.perform(post("/api/treinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoTreino)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTreino").value("Treino B"))
                .andDo(print());
    }

    @Test
    void updateTreino_deveAtualizarERetornarTreinoAtualizado() throws Exception {
        treino.setNomeTreino("Treino Atualizado");

        mockMvc.perform(put("/api/treinos/{id}", treino.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(treino)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTreino").value("Treino Atualizado"))
                .andDo(print());
    }

    @Test
    void updateTreino_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();
        TreinoEntity novoTreino = new TreinoEntity();
        novoTreino.setNomeTreino("Treino Desconhecido");

        mockMvc.perform(put("/api/treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoTreino)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteTreino_deveDeletarTreino() throws Exception {
        mockMvc.perform(delete("/api/treinos/{id}", treino.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteTreino_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

package com.carneseca.app_academia.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.UUID;

import com.carneseca.app_academia.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.carneseca.app_academia.entities.ExercicioEntity;
import com.carneseca.app_academia.repositories.ExercicioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ExercicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ExercicioEntity exercicio;

    @BeforeEach
    void setUp() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Usuário 1");

        exercicio = new ExercicioEntity();
        exercicio.setNomeExercicio("Flexão de Braço");
        exercicio.setTipoExercicio("Força");
        exercicio.setDescricao("Exercício para peitorais e tríceps");
        exercicio.setEquipamento("Nenhum");
        exercicio.setUsuario(usuario);
        exercicioRepository.save(exercicio);
    }

    @Test
    void getAllExercicios_deveRetornarListaDeExercicios() throws Exception {
        mockMvc.perform(get("/api/exercicios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nomeExercicio").value("Flexão de Braço"))
                .andDo(print());
    }

    @Test
    void getExercicioById_deveRetornarExercicio() throws Exception {
        mockMvc.perform(get("/api/exercicios/{id}", exercicio.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeExercicio").value("Flexão de Braço"))
                .andDo(print());
    }

    @Test
    void getExercicioById_deveRetornarNaoEncontradoQuandoEntidadeNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/exercicios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createExercicio_deveCriarERetornarExercicio() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Usuário 2");

        ExercicioEntity novoExercicio = new ExercicioEntity();
        novoExercicio.setNomeExercicio("Agachamento");
        novoExercicio.setTipoExercicio("Força");
        novoExercicio.setDescricao("Exercício para pernas");
        novoExercicio.setEquipamento("Barra");
        novoExercicio.setUsuario(usuario);

        mockMvc.perform(post("/api/exercicios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoExercicio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeExercicio").value("Agachamento"))
                .andDo(print());
    }

    @Test
    void updateExercicio_deveAtualizarERetornarExercicioAtualizado() throws Exception {
        exercicio.setNomeExercicio("Flexão de Braço Modificada");

        mockMvc.perform(put("/api/exercicios/{id}", exercicio.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exercicio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeExercicio").value("Flexão de Braço Modificada"))
                .andDo(print());
    }

    @Test
    void updateExercicio_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();
        ExercicioEntity novoExercicio = new ExercicioEntity();
        novoExercicio.setNomeExercicio("Agachamento");

        mockMvc.perform(put("/api/exercicios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoExercicio)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteExercicio_deveDeletarExercicio() throws Exception {
        mockMvc.perform(delete("/api/exercicios/{id}", exercicio.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteExercicio_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/exercicios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

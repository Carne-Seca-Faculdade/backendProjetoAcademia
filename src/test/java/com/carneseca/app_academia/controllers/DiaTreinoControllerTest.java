package com.carneseca.app_academia.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.UUID;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.carneseca.app_academia.entities.DiaTreinoEntity;
import com.carneseca.app_academia.repositories.DiaTreinoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DiaTreinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiaTreinoRepository diaTreinoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DiaTreinoEntity diaTreino;

    @BeforeEach
    void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        diaTreino = new DiaTreinoEntity();
        diaTreino.setDataInicio(sdf.parse("2023-09-25"));
        diaTreino.setDataFim(sdf.parse("2023-10-01"));
        diaTreinoRepository.save(diaTreino);
    }

    @Test
    void getAllDiaTreinos_shouldReturnListOfDiaTreinoEntities() throws Exception {
        mockMvc.perform(get("/api/dia-treinos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].dataInicio").value("2023-09-25"))
                .andDo(print());
    }

    @Test
    void getDiaTreinoById_shouldReturnDiaTreinoEntity() throws Exception {
        mockMvc.perform(get("/api/dia-treinos/{id}", diaTreino.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataInicio").value("2023-09-25"))
                .andDo(print());
    }

    @Test
    void getDiaTreinoById_shouldReturnNotFoundWhenEntityDoesNotExist() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/dia-treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createDiaTreino_shouldCreateAndReturnDiaTreinoEntity() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DiaTreinoEntity newDiaTreino = new DiaTreinoEntity();
        newDiaTreino.setDataInicio(sdf.parse("2023-10-05"));
        newDiaTreino.setDataFim(sdf.parse("2023-10-10"));

        mockMvc.perform(post("/api/dia-treinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDiaTreino)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataInicio").value("2023-10-05"))
                .andDo(print());
    }

    @Test
    void updateDiaTreino_shouldUpdateAndReturnUpdatedDiaTreino() throws Exception {
        diaTreino.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse("2023-09-30"));

        mockMvc.perform(put("/api/dia-treinos/{id}", diaTreino.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diaTreino)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataInicio").value("2023-09-30"))
                .andDo(print());
    }

    @Test
    void updateDiaTreino_shouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        UUID randomId = UUID.randomUUID();
        DiaTreinoEntity newDiaTreino = new DiaTreinoEntity();
        newDiaTreino.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-05"));

        mockMvc.perform(put("/api/dia-treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDiaTreino)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteDiaTreino_shouldDeleteDiaTreino() throws Exception {
        mockMvc.perform(delete("/api/dia-treinos/{id}", diaTreino.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteDiaTreino_shouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/dia-treinos/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

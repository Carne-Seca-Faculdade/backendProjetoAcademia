package com.carneseca.app_academia.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Date;
import java.util.UUID;

import com.carneseca.app_academia.entities.UsuarioEntity;
import com.carneseca.app_academia.repositories.UsuarioRepository;
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
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new UsuarioEntity();
        usuario.setNome("Usuário A");
        usuario.setEmail("usuario.a@exemplo.com");
        usuario.setSenha("senha123");
        usuario.setDataNascimento(new Date());
        usuario.setGenero("Masculino");
        usuario.setPeso(70.0f);
        usuario.setAltura(1.75f);
        usuarioRepository.save(usuario);
    }

    @Test
    void getAllUsuarios_deveRetornarListaDeUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("Usuário A"))
                .andDo(print());
    }

    @Test
    void getUsuarioById_deveRetornarUsuario() throws Exception {
        mockMvc.perform(get("/api/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Usuário A"))
                .andDo(print());
    }

    @Test
    void getUsuarioById_deveRetornarNaoEncontradoQuandoEntidadeNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/usuarios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createUsuario_deveCriarERetornarUsuario() throws Exception {
        UsuarioEntity novoUsuario = new UsuarioEntity();
        novoUsuario.setNome("Usuário B");
        novoUsuario.setEmail("usuario.b@exemplo.com");
        novoUsuario.setSenha("senha456");
        novoUsuario.setDataNascimento(new Date());
        novoUsuario.setGenero("Feminino");
        novoUsuario.setPeso(60.0f);
        novoUsuario.setAltura(1.65f);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Usuário B"))
                .andDo(print());
    }

    @Test
    void updateUsuario_deveAtualizarERetornarUsuarioAtualizado() throws Exception {
        usuario.setNome("Usuário Atualizado");

        mockMvc.perform(put("/api/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Usuário Atualizado"))
                .andDo(print());
    }

    @Test
    void updateUsuario_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();
        UsuarioEntity novoUsuario = new UsuarioEntity();
        novoUsuario.setNome("Usuário Desconhecido");

        mockMvc.perform(put("/api/usuarios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoUsuario)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteUsuario_deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteUsuario_deveRetornarNaoEncontradoQuandoIdNaoExiste() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/usuarios/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

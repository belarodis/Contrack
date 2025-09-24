package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Pessoa pessoa;

    @BeforeEach
    void setup() {
        pessoaRepository.deleteAll();
        pessoa = pessoaRepository.save(new Pessoa(null, "Carlos", null, null));
    }

    @Test
    void deveCriarContratoViaPost() throws Exception {
        ContratoCreateDTO dto = new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa.getId());

        mockMvc.perform(post("/contratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.horasSemana").value(40));
    }

    @Test
    void naoDeveCriarContratoHorasMaioresQue40() throws Exception {
        ContratoCreateDTO dto = new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 45, 50.0, pessoa.getId());

        mockMvc.perform(post("/contratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarContratoPorId() throws Exception {
        // Criar contrato primeiro
        String json = objectMapper.writeValueAsString(new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa.getId()));
        String response = mockMvc.perform(post("/contratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();

        Long contratoId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/contratos/" + contratoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(contratoId));
    }
}
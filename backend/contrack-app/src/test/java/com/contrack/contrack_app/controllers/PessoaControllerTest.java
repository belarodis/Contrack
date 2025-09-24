package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private IAlocacaoRepository alocacaoRepository;

    @Autowired
    private IContratoRepository contratoRepository;

    @BeforeEach
    void limparBanco() {
        alocacaoRepository.deleteAll();
        contratoRepository.deleteAll();
        pessoaRepository.deleteAll();
    }

    @Test
    void deveBuscarPessoasViaGET() throws Exception {
        pessoaRepository.save(new Pessoa(null, "Ana", null, null));
        pessoaRepository.save(new Pessoa(null, "Maria", null, null));

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveBuscarPessoaPorIdViaGET() throws Exception {
        Pessoa p = pessoaRepository.save(new Pessoa(null, "Lucas", null, null));

        mockMvc.perform(get("/pessoas/" + p.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Lucas"));
    }

    @Test
    void deveCriarPessoaViaPOST() throws Exception {
        mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new com.contrack.contrack_app.dto.create.PessoaCreateDTO("Julia"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Julia"));
    }
}

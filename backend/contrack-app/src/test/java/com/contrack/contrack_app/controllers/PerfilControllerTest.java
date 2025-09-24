package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.repositories.interfaces.IPerfilRepository;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPerfilRepository perfilRepository;

    @Autowired
    private IAlocacaoRepository alocacaoRepository;

    @BeforeEach
    void limparBanco() {
        // Deletar dependências primeiro
        alocacaoRepository.deleteAll();
        // Depois deletar perfis
        perfilRepository.deleteAll();
    }

    @Test
    void deveBuscarPerfisViaGET() throws Exception {
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.DEV));
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.QA));

        mockMvc.perform(get("/perfis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveBuscarPerfilPorIdViaGET() throws Exception {
        Perfil p = perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.GERENTE));

        mockMvc.perform(get("/perfis/" + p.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("GERENTE"));
    }
}

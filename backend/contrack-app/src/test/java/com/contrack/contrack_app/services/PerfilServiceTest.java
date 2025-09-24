package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.view.PerfilViewDTO;
import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.repositories.interfaces.IPerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PerfilServiceTest {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private IPerfilRepository perfilRepository;

    @BeforeEach
    void limparBanco() {
        perfilRepository.deleteAll();
    }

    @Test
    void deveBuscarPerfilPorId() {
        Perfil p = perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.SECURITY));
        Optional<Perfil> encontrado = perfilService.buscarPerfilPorId(p.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTipo()).isEqualTo(Perfil.TipoPerfil.SECURITY);
    }

    @Test
    void deveListarTodosOsPerfis() {
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.DEV));
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.GERENTE));

        List<PerfilViewDTO> perfis = perfilService.buscarPerfis();
        assertThat(perfis).hasSize(2);
    }
}

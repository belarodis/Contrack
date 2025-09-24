package com.contrack.contrack_app.repositories;

import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.repositories.interfaces.IPerfilRepository;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PerfilRepositoryTest {

    @Autowired
    private IPerfilRepository perfilRepository;

    @Autowired
    private IAlocacaoRepository alocacaoRepository;

    @BeforeEach
    void limparBanco() {
        // Primeiro limpar dependências
        alocacaoRepository.deleteAll();
        // Depois limpar perfis
        perfilRepository.deleteAll();
    }

    @Test
    void deveSalvarPerfil() {
        Perfil p = new Perfil();
        p.setTipo(Perfil.TipoPerfil.DEV);
        Perfil salva = perfilRepository.save(p);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getTipo()).isEqualTo(Perfil.TipoPerfil.DEV);
    }

    @Test
    void deveBuscarTodosOsPerfis() {
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.GERENTE));
        perfilRepository.save(new Perfil(null, Perfil.TipoPerfil.QA));

        List<Perfil> perfis = perfilRepository.findAll();
        assertThat(perfis).hasSize(2);
    }
}

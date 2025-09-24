package com.contrack.contrack_app.repositories;

import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PessoaRepositoryTest {

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private IAlocacaoRepository alocacaoRepository;

    @Autowired
    private IContratoRepository contratoRepository;

    @BeforeEach
    void limparBanco() {
        // Limpa dependências primeiro
        alocacaoRepository.deleteAll();
        contratoRepository.deleteAll();
        // Depois limpa a tabela principal
        pessoaRepository.deleteAll();
    }

    @Test
    void deveSalvarPessoa() {
        Pessoa p = new Pessoa();
        p.setNome("Lucas");
        Pessoa salva = pessoaRepository.save(p);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Lucas");
    }

    @Test
    void deveBuscarTodasAsPessoas() {
        pessoaRepository.save(new Pessoa(null, "Ana", null, null));
        pessoaRepository.save(new Pessoa(null, "Maria", null, null));

        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(2);
    }
}
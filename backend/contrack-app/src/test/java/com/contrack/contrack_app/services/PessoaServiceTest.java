package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
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
class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

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
    void deveBuscarPessoaPorId() {
        Pessoa p = pessoaRepository.save(new Pessoa(null, "Lucas", null, null));
        Optional<Pessoa> encontrado = pessoaService.buscarPessoaPorId(p.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Lucas");
    }

    @Test
    void deveListarTodasAsPessoas() {
        pessoaRepository.save(new Pessoa(null, "Ana", null, null));
        pessoaRepository.save(new Pessoa(null, "Maria", null, null));

        List<PessoaViewDTO> pessoas = pessoaService.buscarPessoas();
        assertThat(pessoas).hasSize(2);
    }

    @Test
    void deveCriarPessoa() {
        PessoaCreateDTO dto = new PessoaCreateDTO("Julia");
        PessoaViewDTO nova = pessoaService.criarPessoa(dto);

        assertThat(nova.id()).isNotNull();
        assertThat(nova.nome()).isEqualTo("Julia");
    }
}

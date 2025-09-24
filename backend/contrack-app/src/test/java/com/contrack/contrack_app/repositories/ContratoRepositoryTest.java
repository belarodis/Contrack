package com.contrack.contrack_app.repositories;

import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ContratoRepositoryTest {

    @Autowired
    private IContratoRepository contratoRepository;

    @Autowired
    private IPessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @BeforeEach
    void setup() {
        contratoRepository.deleteAll();
        pessoaRepository.deleteAll();
        pessoa = pessoaRepository.save(new Pessoa(null, "Lucas", null, null));
    }

    @Test
    void deveSalvarContrato() {
        Contrato c = new Contrato(null, LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa);
        Contrato salvo = contratoRepository.save(c);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getHorasSemana()).isEqualTo(40);
        assertThat(salvo.getPessoa()).isEqualTo(pessoa);
    }

    @Test
    void deveBuscarContratosPorPessoaOrdenadosPorDataFim() {
        contratoRepository.save(new Contrato(null, LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa));
        contratoRepository.save(new Contrato(null, LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(7), 20, 30.0, pessoa));

        List<Contrato> contratos = contratoRepository.findByPessoaOrderByDataFimDesc(pessoa);
        assertThat(contratos).hasSize(2);
        assertThat(contratos.get(0).getDataFim()).isAfter(contratos.get(1).getDataFim());
    }
}

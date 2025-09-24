package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class ContratoServiceTest {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private IContratoRepository contratoRepository;

    @Autowired
    private IPessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @BeforeEach
    void setup() {
        contratoRepository.deleteAll();
        pessoaRepository.deleteAll();
        pessoa = pessoaRepository.save(new Pessoa(null, "Ana", null, null));
    }

    @Test
    void deveCriarContrato() {
        ContratoCreateDTO dto = new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa.getId());
        var contratoDto = contratoService.criarContrato(dto);

        assertThat(contratoDto.id()).isNotNull();
        assertThat(contratoDto.horasSemana()).isEqualTo(40);
    }

    @Test
    void naoDevePermitirHorasSemanaMaioresQue40() {
        ContratoCreateDTO dto = new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 45, 50.0, pessoa.getId());

        assertThrows(IllegalArgumentException.class, () -> contratoService.criarContrato(dto));
    }

    @Test
    void naoDevePermitirContratosSobrepostos() {
        // Primeiro contrato
        contratoService.criarContrato(new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa.getId()));

        // Segundo contrato sobreposto
        ContratoCreateDTO dto = new ContratoCreateDTO(LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(7), 20, 30.0, pessoa.getId());

        assertThrows(IllegalArgumentException.class, () -> contratoService.criarContrato(dto));
    }

    @Test
    void deveBuscarTodosOsContratos() {
        contratoService.criarContrato(new ContratoCreateDTO(LocalDate.now(), LocalDate.now().plusMonths(6), 40, 50.0, pessoa.getId()));
        contratoService.criarContrato(new ContratoCreateDTO(LocalDate.now().plusMonths(7), LocalDate.now().plusMonths(12), 20, 30.0, pessoa.getId()));

        List<?> contratos = contratoService.buscarContratos();
        assertThat(contratos).hasSize(2);
    }
}

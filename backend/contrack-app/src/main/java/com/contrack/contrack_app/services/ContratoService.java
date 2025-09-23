package com.contrack.contrack_app.services;

import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final IContratoRepository contratoRepository;

    public ContratoService(IContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public Optional<Contrato> BuscarContratoPorId(Long idContrato){
        return contratoRepository.findById(idContrato);
    }

    public List<Contrato> BuscarContratos() {
        return contratoRepository.findAll();
    }

    public Contrato criarContrato(Contrato novoContrato) {
        if (novoContrato.getHorasSemana() > 40) {
            throw new IllegalArgumentException("O número de horas semanais no contrato não pode ser maior que 40.");
        }

        List<Contrato> contratosExistentes = contratoRepository
                .findByPessoaOrderByDataFimDesc(novoContrato.getPessoa());
        for (Contrato contratoExistente : contratosExistentes) {
            // Verifica se as datas dos contratos se sobrepõem
            boolean sobrepoe = novoContrato.getDataFim().isAfter(contratoExistente.getDataInicio()) &&
                    novoContrato.getDataInicio().isBefore(contratoExistente.getDataFim());

            if (sobrepoe) {
                throw new IllegalArgumentException("O novo contrato se sobrepõe a um contrato existente.");
            }
        }

        return contratoRepository.save(novoContrato);
    }

    public Optional<Contrato> getContratoAtivo(Pessoa pessoa) {
        List<Contrato> contratos = contratoRepository.findByPessoaOrderByDataFimDesc(pessoa);

        // encontra o contrato mais recente que ainda está ativo.
        return contratos.stream()
                .filter(contrato -> !LocalDate.now().isBefore(contrato.getDataInicio()) &&
                        !LocalDate.now().isAfter(contrato.getDataFim()))
                .findFirst();
    }
}
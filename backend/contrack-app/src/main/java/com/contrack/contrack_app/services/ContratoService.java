package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.mapper.ContratoMapper;
import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IContratoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    private final IContratoRepository contratoRepository;
    private final PessoaService pessoaService;
    private final ContratoMapper contratoMapper;

    public ContratoService(IContratoRepository contratoRepository, PessoaService pessoaService, ContratoMapper contratoMapper) {
        this.contratoRepository = contratoRepository;
        this.pessoaService = pessoaService;
        this.contratoMapper = contratoMapper;
    }

    public Optional<Contrato> buscarContratoPorId(Long idContrato) {
        return contratoRepository.findById(idContrato);
    }

    // Novo método para buscar um contrato por ID e retorná-lo como DTO com status
    public Optional<ContratoViewDTO> buscarContratoPorIdComStatus(Long idContrato) {
        return contratoRepository.findById(idContrato)
                .map(contratoMapper::toDto);
    }
    
    // ... os outros métodos do ContratoService (buscarContratos, criarContrato, etc.)
    // ... permanecem inalterados como na versão anterior.
    public List<ContratoViewDTO> buscarContratos() {
         return contratoRepository.findAll()
                .stream()
                .map(contratoMapper::toDto)
                .collect(Collectors.toList());
    }

    public ContratoViewDTO criarContrato(ContratoCreateDTO dto) {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(dto.pessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        if (dto.horasSemana() > 40) {
            throw new IllegalArgumentException("O número de horas semanais no contrato não pode ser maior que 40.");
        }

        List<Contrato> contratosExistentes = contratoRepository.findByPessoaOrderByDataFimDesc(pessoa);
        for (Contrato contratoExistente : contratosExistentes) {
            boolean sobrepoe = dto.dataFim().isAfter(contratoExistente.getDataInicio()) &&
                    dto.dataInicio().isBefore(contratoExistente.getDataFim());
            if (sobrepoe) {
                throw new IllegalArgumentException("O novo contrato se sobrepõe a um contrato existente.");
            }
        }
        
        Contrato novoContrato = contratoMapper.toEntity(dto);
        novoContrato.setPessoa(pessoa);
        Contrato contratoSalvo = contratoRepository.save(novoContrato);
        return contratoMapper.toDto(contratoSalvo);
    }

    public Optional<Contrato> getContratoAtivo(Pessoa pessoa) {
        List<Contrato> contratos = contratoRepository.findByPessoaOrderByDataFimDesc(pessoa);
        return contratos.stream()
                .filter(contrato -> !LocalDate.now().isBefore(contrato.getDataInicio()) &&
                                    !LocalDate.now().isAfter(contrato.getDataFim()))
                .findFirst();
    }
}
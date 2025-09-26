package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.ProjetoCreateDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.exceptions.ResourceNotFoundException;
import com.contrack.contrack_app.mapper.ProjetoMapper;
import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import com.contrack.contrack_app.repositories.interfaces.IProjetoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    private final IProjetoRepository projetoRepository;
    private final IAlocacaoRepository alocacaoRepository;
    private final ContratoService contratoService;
    private final ProjetoMapper projetoMapper;

    // Construtor sem a dependência cíclica
    public ProjetoService(IProjetoRepository projetoRepository, IAlocacaoRepository alocacaoRepository, ContratoService contratoService, ProjetoMapper projetoMapper) {
        this.projetoRepository = projetoRepository;
        this.alocacaoRepository = alocacaoRepository;
        this.contratoService = contratoService;
        this.projetoMapper = projetoMapper;
    }

    public ProjetoViewDTO criarProjeto(ProjetoCreateDTO dto) {
        Projeto projeto = projetoMapper.toEntity(dto);
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoMapper.toDto(projetoSalvo);
    }

    public Optional<Projeto> buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public List<ProjetoViewDTO> buscarProjetos() {
        return projetoRepository.findAll()
                .stream()
                .map(projetoMapper::toDto)
                .collect(Collectors.toList());
    }

    public double calcularCustoTotal(Projeto projeto) {
        double custoTotal = 0.0;
        Iterable<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);
        for (Alocacao alocacao : alocacoes) {
            double salarioHora = contratoService.getContratoAtivo(alocacao.getPessoa())
                    .map(Contrato::getSalarioHora)
                    .orElse(0.0);
            custoTotal += salarioHora * alocacao.getHorasSemana();
        }
        return custoTotal;
    }

    public boolean isProjetoAtivo(Projeto projeto) {
        LocalDate hoje = LocalDate.now();
        return !hoje.isAfter(projeto.getDataFim());
    }

    public Optional<ProjetoViewDTO> buscarProjetoPorIdComStatus(Long id) {
        return projetoRepository.findById(id)
                .map(projetoMapper::toDto);
    }
    
    // Método auxiliar para buscar por ID, lançando exceção se não encontrar (usado pelo Controller)
    public Projeto buscarProjetoPorIdOrThrow(Long id) {
        return projetoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto", id));
    }
}
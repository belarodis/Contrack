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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // Buscar TODOS os contratos válidos durante o período do projeto
        List<Contrato> contratos = contratoService.getContratosNosPeriodo(
                alocacao.getPessoa(), projeto.getDataInicio(), projeto.getDataFim());

        for (Contrato contrato : contratos) {
            // Calcular período de interseção entre contrato e projeto
            LocalDate contratoInicio = contrato.getDataInicio().isAfter(projeto.getDataInicio()) ? 
                                      contrato.getDataInicio() : projeto.getDataInicio();
            LocalDate contratoFim = contrato.getDataFim().isBefore(projeto.getDataFim()) ? 
                                   contrato.getDataFim() : projeto.getDataFim();

            if (!contratoInicio.isAfter(contratoFim)) {
                long diasValidos = ChronoUnit.DAYS.between(contratoInicio, contratoFim) + 1;
                double semanasValidas = diasValidos / 7.0;

                double horasUteis = alocacao.getHorasSemana() * (5.0 / 7.0);
                custoTotal += contrato.getSalarioHora() * horasUteis * semanasValidas;
            }
        }
    }

    return custoTotal;
}

public double calcularCustoPorPeriodo(Projeto projeto, LocalDate periodoInicio, LocalDate periodoFim) {
    double custoTotal = 0.0;
    Iterable<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);

    // Verificar período válido do projeto
    LocalDate inicioReal = periodoInicio.isBefore(projeto.getDataInicio()) ? projeto.getDataInicio() : periodoInicio;
    LocalDate fimReal = periodoFim.isAfter(projeto.getDataFim()) ? projeto.getDataFim() : periodoFim;

    if (inicioReal.isAfter(fimReal)) {
        return 0.0;
    }

    for (Alocacao alocacao : alocacoes) {
        // Buscar TODOS os contratos válidos durante o período
        List<Contrato> contratos = contratoService.getContratosNosPeriodo(
                alocacao.getPessoa(), inicioReal, fimReal);

        for (Contrato contrato : contratos) {
            // Calcular período de interseção entre contrato, projeto e período solicitado
            LocalDate contratoInicio = contrato.getDataInicio();
            LocalDate contratoFim = contrato.getDataFim();

            // Pegar a maior data de início e menor data de fim
            LocalDate inicioValido = Stream.of(inicioReal, contratoInicio)
                    .max(LocalDate::compareTo).get();
            LocalDate fimValido = Stream.of(fimReal, contratoFim)
                    .min(LocalDate::compareTo).get();

            if (!inicioValido.isAfter(fimValido)) {
                long diasValidos = ChronoUnit.DAYS.between(inicioValido, fimValido) + 1;
                double semanasValidas = diasValidos / 7.0;

                double horasUteis = alocacao.getHorasSemana() * (5.0 / 7.0);
                custoTotal += contrato.getSalarioHora() * horasUteis * semanasValidas;
            }
        }
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
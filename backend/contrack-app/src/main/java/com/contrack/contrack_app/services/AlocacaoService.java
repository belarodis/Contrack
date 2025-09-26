package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.exceptions.InvalidDataException;
import com.contrack.contrack_app.exceptions.ResourceConflictException;
import com.contrack.contrack_app.exceptions.ResourceNotFoundException;
import com.contrack.contrack_app.mapper.AlocacaoMapper;
import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AlocacaoService {

    private final IAlocacaoRepository alocacaoRepository;
    private final PessoaService pessoaService;
    private final ProjetoService projetoService;
    private final PerfilService perfilService;
    private final AlocacaoMapper alocacaoMapper;
    private final ContratoService contratoService;

    public AlocacaoService(
            IAlocacaoRepository alocacaoRepository,
            PessoaService pessoaService,
            ProjetoService projetoService,
            PerfilService perfilService,
            AlocacaoMapper alocacaoMapper,
            ContratoService contratoService) {
        this.alocacaoRepository = alocacaoRepository;
        this.pessoaService = pessoaService;
        this.projetoService = projetoService;
        this.perfilService = perfilService;
        this.alocacaoMapper = alocacaoMapper;
        this.contratoService = contratoService;
    }

    public List<AlocacaoViewDTO> buscarAlocacoes() {
        return alocacaoRepository.findAll()
                .stream()
                .map(alocacaoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Alocacao> buscarAlocacaoPorId(Long id) {
        return alocacaoRepository.findById(id);
    }

    public List<AlocacaoViewDTO> buscarAlocacoesPorProjetoId(Long projetoId) {
        Projeto projeto = projetoService.buscarProjetoPorId(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto", projetoId));

        return alocacaoRepository.findByProjeto(projeto)
                .stream()
                .map(alocacaoMapper::toDto)
                .collect(Collectors.toList());
    }

    public AlocacaoViewDTO criarAlocacao(AlocacaoCreateDTO dto) {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(dto.pessoaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa", dto.pessoaId()));
        Projeto projeto = projetoService.buscarProjetoPorId(dto.projetoId())
                .orElseThrow(() -> new ResourceNotFoundException("Projeto", dto.projetoId()));
        Perfil perfil = perfilService.buscarPerfilPorId(dto.perfilId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil", dto.perfilId()));

        Alocacao novaAlocacao = new Alocacao();
        novaAlocacao.setPessoa(pessoa);
        novaAlocacao.setProjeto(projeto);
        novaAlocacao.setPerfil(perfil);
        novaAlocacao.setHorasSemana(dto.horasSemana());

        // REGRA 1: Uma pessoa não pode ser alocada no mesmo projeto.
        List<Alocacao> alocacoesNoProjeto =
                alocacaoRepository.findByPessoaAndProjeto(novaAlocacao.getPessoa(), novaAlocacao.getProjeto());
        if (!alocacoesNoProjeto.isEmpty()) {
            throw new ResourceConflictException("Essa pessoa já está alocada neste projeto.");
        }

        // REGRA 2: Um projeto deve ter no MÁXIMO um gerente.
        if (perfil.getTipo() == Perfil.TipoPerfil.gerente) {
            long numGerentesExistentes = alocacaoRepository.findByProjeto(projeto).stream()
                    .filter(aloc -> aloc.getPerfil().getTipo() == Perfil.TipoPerfil.gerente)
                    .count();

            if (numGerentesExistentes >= 1) {
                throw new ResourceConflictException("O projeto já possui um Gerente alocado. Limite de um Gerente por projeto.");
            }
        }

        // =======================================================
        // REGRA 3 & 4: Validação Temporal de Capacidade (Contrato/40h)
        // =======================================================
        final int LIMITE_HORAS_SISTEMA = 40;
        final int limiteEfetivo;
        final String limiteOrigem;

        // 1. Busca o Contrato Ativo vigente no período do projeto
        Optional<Contrato> contratoOpt = contratoService.getContratoAtivoDurantePeriodo(
                pessoa, projeto.getDataInicio(), projeto.getDataFim());

        if (contratoOpt.isEmpty()) {
            throw new InvalidDataException("Não foi encontrado um contrato ativo para a pessoa no período do projeto. Alocação impossível.");
        }

        int limiteContratual = contratoOpt.get().getHorasSemana();

        if (limiteContratual < LIMITE_HORAS_SISTEMA) {
            limiteEfetivo = limiteContratual;
            limiteOrigem = "Contratual (" + limiteContratual + "h)";
        } else {
            limiteEfetivo = LIMITE_HORAS_SISTEMA;
            limiteOrigem = "Sistema (40h)";
        }

        // 2. Define o período do novo projeto
        LocalDate novoProjetoInicio = projeto.getDataInicio();
        LocalDate novoProjetoFim = projeto.getDataFim();

        // 3. Busca TODAS as alocações que consomem capacidade (Ativo ou Incompleto)
        List<Alocacao> alocacoesAtivasExistentes = alocacaoRepository.findByPessoa(pessoa)
                .stream()
                .filter(aloc -> {
                    Optional<ProjetoViewDTO> projetoDtoOpt =
                            projetoService.buscarProjetoPorIdComStatus(aloc.getProjeto().getId());
                    if (projetoDtoOpt.isEmpty()) return false;

                    String status = projetoDtoOpt.get().status();
                    return "Ativo".equals(status) || "Incompleto".equals(status);
                })
                .collect(Collectors.toList());

        // 4. Determina o período global que precisa ser verificado
        LocalDate periodoGlobalInicio = novoProjetoInicio;
        LocalDate periodoGlobalFim = novoProjetoFim;

        if (!alocacoesAtivasExistentes.isEmpty()) {
            LocalDate minExistingDate = alocacoesAtivasExistentes.stream()
                    .map(aloc -> aloc.getProjeto().getDataInicio())
                    .min(LocalDate::compareTo)
                    .get();

            LocalDate maxExistingDate = alocacoesAtivasExistentes.stream()
                    .map(aloc -> aloc.getProjeto().getDataFim())
                    .max(LocalDate::compareTo)
                    .get();

            periodoGlobalInicio = Stream.of(minExistingDate, novoProjetoInicio).min(LocalDate::compareTo).get();
            periodoGlobalFim = Stream.of(maxExistingDate, novoProjetoFim).max(LocalDate::compareTo).get();
        }

        // 5. Itera sobre cada dia útil no período global para checar sobrecarga
        try {
            Stream.iterate(periodoGlobalInicio, data -> data.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(periodoGlobalInicio, periodoGlobalFim) + 1)
                    .filter(data -> data.getDayOfWeek() != DayOfWeek.SATURDAY &&
                                    data.getDayOfWeek() != DayOfWeek.SUNDAY)
                    .forEach(diaUtil -> {
                        int horasTotalNoDia = novaAlocacao.getHorasSemana();

                        for (Alocacao aloc : alocacoesAtivasExistentes) {
                            Projeto projExistente = aloc.getProjeto();

                            boolean projetoVigenteNoDia =
                                    !diaUtil.isBefore(projExistente.getDataInicio()) &&
                                    !diaUtil.isAfter(projExistente.getDataFim());

                            if (projetoVigenteNoDia) {
                                horasTotalNoDia += aloc.getHorasSemana();
                            }
                        }

                        if (horasTotalNoDia > limiteEfetivo) {
                            throw new ResourceConflictException(
                                    "A alocação excede o limite de " + limiteEfetivo +
                                            " horas semanais (" + limiteOrigem + "). Conflito de " + horasTotalNoDia +
                                            " horas encontrado no dia: " + diaUtil +
                                            ". (Apenas projetos Ativos/Incompletos são contados.)"
                            );
                        }
                    });
        } catch (ResourceConflictException e) {
            throw new InvalidDataException(e.getMessage());
        }

        Alocacao alocacaoSalva = alocacaoRepository.save(novaAlocacao);
        return alocacaoMapper.toDto(alocacaoSalva);
    }

    public boolean verificarComposicaoTime(Projeto projeto) {
        List<Alocacao> alocacoesDoProjeto = alocacaoRepository.findByProjeto(projeto);
        boolean temGerente = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.gerente);
        boolean temDev = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.dev);
        boolean temQa = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.qa);
        long numGerentes = alocacoesDoProjeto.stream()
                .filter(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.gerente)
                .count();
        return (temGerente && temDev && temQa && numGerentes == 1);
    }
}

package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.exceptions.InvalidDataException;
import com.contrack.contrack_app.exceptions.ResourceConflictException;
import com.contrack.contrack_app.exceptions.ResourceNotFoundException;
import com.contrack.contrack_app.mapper.AlocacaoMapper;
import com.contrack.contrack_app.models.Alocacao;
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

    public AlocacaoService(IAlocacaoRepository alocacaoRepository, PessoaService pessoaService, ProjetoService projetoService, PerfilService perfilService, AlocacaoMapper alocacaoMapper) {
        this.alocacaoRepository = alocacaoRepository;
        this.pessoaService = pessoaService;
        this.projetoService = projetoService;
        this.perfilService = perfilService;
        this.alocacaoMapper = alocacaoMapper;
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
        List<Alocacao> alocacoesNoProjeto = alocacaoRepository.findByPessoaAndProjeto(novaAlocacao.getPessoa(), novaAlocacao.getProjeto());
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

        // REGRA 3: Validação Temporal de Capacidade (40 horas)
        
        // 1. Define o período do novo projeto
        LocalDate novoProjetoInicio = projeto.getDataInicio();
        LocalDate novoProjetoFim = projeto.getDataFim();

        // 2. Busca TODAS as alocações que consomem capacidade (Ativo ou Incompleto)
        List<Alocacao> alocacoesAtivasExistentes = alocacaoRepository.findByPessoa(pessoa)
            .stream()
            .filter(aloc -> {
                Optional<ProjetoViewDTO> projetoDtoOpt = projetoService.buscarProjetoPorIdComStatus(aloc.getProjeto().getId());
                if (!projetoDtoOpt.isPresent()) return false;
                
                String status = projetoDtoOpt.get().status();
                
                // AJUSTE: Apenas Ativo e Incompleto consomem o limite de 40h.
                // "Em Espera" NÃO É considerado um compromisso de capacidade.
                return "Ativo".equals(status) || "Incompleto".equals(status); 
            })
            .collect(Collectors.toList());

        // 3. Determina o período global que precisa ser verificado (o maior período de interesse)
        LocalDate periodoGlobalInicio = novoProjetoInicio;
        LocalDate periodoGlobalFim = novoProjetoFim;

        if (!alocacoesAtivasExistentes.isEmpty()) {
            // Encontra a data de início mais cedo e a data de fim mais tarde
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

        // 4. Itera sobre CADA DIA ÚTIL no período global para checar sobrecarga
        try {
            Stream.iterate(periodoGlobalInicio, data -> data.plusDays(1))
                .limit(ChronoUnit.DAYS.between(periodoGlobalInicio, periodoGlobalFim) + 1)
                // Filtra SÓ dias úteis (Segunda a Sexta)
                .filter(data -> data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY)
                .forEach(diaUtil -> {
                    // Soma as horas alocadas para este DIA ÚTIL
                    int horasTotalNoDia = novaAlocacao.getHorasSemana(); // Começa com a nova alocação

                    for (Alocacao aloc : alocacoesAtivasExistentes) {
                        Projeto projExistente = aloc.getProjeto();
                        
                        // Verifica se o projeto existente é vigente neste dia útil
                        boolean projetoVigenteNoDia = !diaUtil.isBefore(projExistente.getDataInicio()) &&
                                                    !diaUtil.isAfter(projExistente.getDataFim());

                        if (projetoVigenteNoDia) {
                            horasTotalNoDia += aloc.getHorasSemana();
                        }
                    }

                    if (horasTotalNoDia > 40) {
                        throw new ResourceConflictException(
                            "A alocação excede o limite de 40 horas semanais em um conflito temporal. " +
                            "Conflito de " + horasTotalNoDia + " horas encontrado no dia: " + diaUtil + 
                            ". (Apenas projetos Ativos/Incompletos são contados.)"
                        );
                    }
                });
        } catch (ResourceConflictException e) {
            // Captura a exceção lançada dentro do forEach e a relança como InvalidDataException
            throw new InvalidDataException(e.getMessage());
        }

        
        Alocacao alocacaoSalva = alocacaoRepository.save(novaAlocacao);
        return alocacaoMapper.toDto(alocacaoSalva);
    }
    
    public boolean verificarComposicaoTime(Projeto projeto) {
        List<Alocacao> alocacoesDoProjeto = alocacaoRepository.findByProjeto(projeto);
        boolean temGerente = alocacoesDoProjeto.stream().anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.gerente);
        boolean temDev = alocacoesDoProjeto.stream().anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.dev);
        boolean temQa = alocacoesDoProjeto.stream().anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.qa);
        long numGerentes = alocacoesDoProjeto.stream().filter(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.gerente).count();
        return (temGerente && temDev && temQa && numGerentes == 1);
    }
}
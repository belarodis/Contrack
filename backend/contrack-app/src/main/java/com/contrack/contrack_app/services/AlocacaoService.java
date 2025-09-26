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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // ... (Métodos buscarAlocacoes, buscarAlocacaoPorId, buscarAlocacoesPorProjetoId - SEM ALTERAÇÃO) ...
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
                // Lança 404
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
            // Conta quantos gerentes já existem neste projeto
            long numGerentesExistentes = alocacaoRepository.findByProjeto(projeto).stream()
                .filter(aloc -> aloc.getPerfil().getTipo() == Perfil.TipoPerfil.gerente)
                .count();

            if (numGerentesExistentes >= 1) {
                throw new ResourceConflictException("O projeto já possui um Gerente alocado. Limite de um Gerente por projeto.");
            }
        }

        // REGRA 3: Limite de 40 horas semanais em projetos ativos ou incompletos.
        int horasAtuaisAlocadasEmProjetosAtivos = alocacaoRepository.findByPessoa(pessoa)
                .stream()
                .filter(aloc -> {
                    Optional<ProjetoViewDTO> projetoDtoOpt = projetoService.buscarProjetoPorIdComStatus(aloc.getProjeto().getId());
                    if (!projetoDtoOpt.isPresent()) return false;
                    
                    String status = projetoDtoOpt.get().status();
                    return "Ativo".equals(status) || "Incompleto".equals(status);
                })
                .mapToInt(Alocacao::getHorasSemana)
                .sum();

        if ((horasAtuaisAlocadasEmProjetosAtivos + novaAlocacao.getHorasSemana()) > 40) {
            throw new InvalidDataException(
                "O total de horas semanais para esta pessoa excederá o limite de 40 horas, considerando projetos ativos/incompletos. Horas atuais: " + 
                horasAtuaisAlocadasEmProjetosAtivos
            );
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
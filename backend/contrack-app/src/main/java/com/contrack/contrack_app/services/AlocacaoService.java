package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
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

    public Optional<Alocacao> buscarAlocacaoPorId(Long id) {
        return alocacaoRepository.findById(id);
    }

    public List<AlocacaoViewDTO> buscarAlocacoes() {
        return alocacaoRepository.findAll()
                .stream()
                .map(alocacaoMapper::toDto)
                .collect(Collectors.toList());
    }

    public AlocacaoViewDTO criarAlocacao(AlocacaoCreateDTO dto) {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(dto.pessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));
        Projeto projeto = projetoService.buscarProjetoPorId(dto.projetoId())
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
        Perfil perfil = perfilService.buscarPerfilPorId(dto.perfilId())
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado."));

        // Cria a entidade para a validação
        Alocacao novaAlocacao = new Alocacao();
        novaAlocacao.setPessoa(pessoa);
        novaAlocacao.setProjeto(projeto);
        novaAlocacao.setPerfil(perfil);
        novaAlocacao.setHorasSemana(dto.horasSemana());

        // Validações
        List<Alocacao> alocacoesNoProjeto = alocacaoRepository.findByPessoaAndProjeto(novaAlocacao.getPessoa(), novaAlocacao.getProjeto());
        if (!alocacoesNoProjeto.isEmpty()) {
            throw new IllegalArgumentException("Essa pessoa já está alocada neste projeto.");
        }

        int horasAtuaisAlocadas = alocacaoRepository.findByPessoa(novaAlocacao.getPessoa())
                .stream()
                .mapToInt(Alocacao::getHorasSemana)
                .sum();

        if ((horasAtuaisAlocadas + novaAlocacao.getHorasSemana()) > 40) {
            throw new IllegalArgumentException("O total de horas semanais para esta pessoa excederá 40.");
        }

        Alocacao alocacaoSalva = alocacaoRepository.save(novaAlocacao);
        return alocacaoMapper.toDto(alocacaoSalva);
    }

    public boolean verificarComposicaoTime(Projeto projeto) {
        List<Alocacao> alocacoesDoProjeto = alocacaoRepository.findByProjeto(projeto);

        boolean temGerente = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.GERENTE);

        boolean temDev = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.DEV);

        boolean temQa = alocacoesDoProjeto.stream()
                .anyMatch(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.QA);

        long numGerentes = alocacoesDoProjeto.stream()
                .filter(alocacao -> alocacao.getPerfil().getTipo() == Perfil.TipoPerfil.GERENTE)
                .count();

        return (temGerente && temDev && temQa && numGerentes == 1);
    }
}
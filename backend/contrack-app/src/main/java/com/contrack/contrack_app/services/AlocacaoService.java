package com.contrack.contrack_app.services;

import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlocacaoService {

    private final IAlocacaoRepository alocacaoRepository;

    public AlocacaoService(IAlocacaoRepository alocacaoRepository) {
        this.alocacaoRepository = alocacaoRepository;
    }

    public Alocacao criarAlocacao(Alocacao novaAlocacao) {
        // uma pessoa não pode ter mais de um perfil no mesmo projeto
        List<Alocacao> alocacoesNoProjeto = alocacaoRepository.findByPessoaAndProjeto(novaAlocacao.getPessoa(), novaAlocacao.getProjeto());
        if (!alocacoesNoProjeto.isEmpty()) {
            throw new IllegalArgumentException("Essa pessoa já está alocada neste projeto.");
        }

        // o total de horas semanais não pode ultrapassar 40
        int horasAtuaisAlocadas = alocacaoRepository.findByPessoa(novaAlocacao.getPessoa())
                .stream()
                .mapToInt(Alocacao::getHorasSemana)
                .sum();

        if ((horasAtuaisAlocadas + novaAlocacao.getHorasSemana()) > 40) {
            throw new IllegalArgumentException("O total de horas semanais para esta pessoa excederá 40.");
        }

        return alocacaoRepository.save(novaAlocacao);
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
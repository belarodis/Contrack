package com.contrack.contrack_app.repositories.interfaces;

import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IAlocacaoRepository extends JpaRepository<Alocacao, Long> {
    /* Busca todas as alocações de uma pessoa em um projeto específico.
     * Usado para a regra de "um perfil por projeto".
     */
    List<Alocacao> findByPessoaAndProjeto(Pessoa pessoa, Projeto projeto);

    /**
     * Busca todas as alocações de uma pessoa em todos os projetos.
     * Usado para a regra do "limite de 40 horas".
     */
    List<Alocacao> findByPessoa(Pessoa pessoa);

    /**
     * Busca todas as alocações para um projeto específico.
     * Usado para verificar a composição do time (Gerente, Dev, QA).
     */
    List<Alocacao> findByProjeto(Projeto projeto);
}

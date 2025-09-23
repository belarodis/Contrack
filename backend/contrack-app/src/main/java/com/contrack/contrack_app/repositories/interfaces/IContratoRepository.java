package com.contrack.contrack_app.repositories.interfaces;

import com.contrack.contrack_app.models.Contrato;
import com.contrack.contrack_app.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContratoRepository extends JpaRepository<Contrato, Long> {
    /**
     * Busca todos os contratos de uma pessoa, ordenados pela data de fim de forma decrescente.
     * Isso é usado pelo service para encontrar o contrato ativo mais recente.
     */
    List<Contrato> findByPessoaOrderByDataFimDesc(Pessoa pessoa);
}

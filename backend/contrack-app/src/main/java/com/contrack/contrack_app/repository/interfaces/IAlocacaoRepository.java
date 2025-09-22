package com.contrack.contrack_app.repository.interfaces;

import com.contrack.contrack_app.models.Alocacao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAlocacaoRepository extends JpaRepository<Alocacao, Integer> {
}

package com.contrack.contrack_app.repository.interfaces;

import com.contrack.contrack_app.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjetoRepository extends JpaRepository<Projeto, Integer> {
}

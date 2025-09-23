package com.contrack.contrack_app.repositories.interfaces;

import com.contrack.contrack_app.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPessoaRepository extends JpaRepository<Pessoa, Long> {
}

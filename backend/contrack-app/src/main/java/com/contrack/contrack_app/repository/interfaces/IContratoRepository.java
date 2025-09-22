package com.contrack.contrack_app.repository.interfaces;

import com.contrack.contrack_app.models.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
}

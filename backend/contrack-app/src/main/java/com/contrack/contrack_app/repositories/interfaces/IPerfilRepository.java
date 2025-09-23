package com.contrack.contrack_app.repositories.interfaces;

import com.contrack.contrack_app.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
}

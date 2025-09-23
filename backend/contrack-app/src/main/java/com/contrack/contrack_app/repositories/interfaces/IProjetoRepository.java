package com.contrack.contrack_app.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contrack.contrack_app.models.Projeto;

public interface IProjetoRepository extends JpaRepository<Projeto, Long> {}

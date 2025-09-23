package com.contrack.contrack_app.services;

import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.repositories.interfaces.IPerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    private final IPerfilRepository perfilRepository;

    public PerfilService(IPerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Optional<Perfil> buscarPerfilPorId(Long id) {
        return perfilRepository.findById(id);
    }
}
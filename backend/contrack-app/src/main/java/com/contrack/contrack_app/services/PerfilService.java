package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.view.PerfilViewDTO;
import com.contrack.contrack_app.mapper.PerfilMapper;
import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.repositories.interfaces.IPerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    private final IPerfilRepository perfilRepository;
    private final PerfilMapper perfilMapper;

    public PerfilService(IPerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    public Optional<Perfil> buscarPerfilPorId(Long id) {
        return perfilRepository.findById(id);
    }

    public List<PerfilViewDTO> buscarPerfis() {
        return perfilRepository.findAll()
                .stream()
                .map(perfilMapper::toDto)
                .collect(Collectors.toList());
    }
}
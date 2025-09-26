package com.contrack.contrack_app.controllers;


import com.contrack.contrack_app.dto.view.PerfilViewDTO;
import com.contrack.contrack_app.mapper.PerfilMapper;
import com.contrack.contrack_app.services.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;
    private final PerfilMapper perfilMapper;

    public PerfilController(PerfilService perfilService, PerfilMapper perfilMapper) {
        this.perfilService = perfilService;
        this.perfilMapper = perfilMapper;
    }

    @GetMapping
    public ResponseEntity<List<PerfilViewDTO>> buscarPerfis() {
        List<PerfilViewDTO> perfis = perfilService.buscarPerfis();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilViewDTO> buscarPerfilPorId(@PathVariable Long id) {
        PerfilViewDTO perfil = perfilMapper.toDto(perfilService.buscarPerfilPorIdOrThrow(id));
        return ResponseEntity.ok(perfil);
    }
}
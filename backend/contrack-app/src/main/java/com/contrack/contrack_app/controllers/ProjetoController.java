package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.ProjetoCreateDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.mapper.ProjetoMapper;
import com.contrack.contrack_app.services.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final ProjetoMapper projetoMapper;

    public ProjetoController(ProjetoService projetoService, ProjetoMapper projetoMapper) {
        this.projetoService = projetoService;
        this.projetoMapper = projetoMapper;
    }

    @PostMapping
    public ResponseEntity<ProjetoViewDTO> criarProjeto(@RequestBody ProjetoCreateDTO dto) {
        ProjetoViewDTO novoProjeto = projetoService.criarProjeto(dto);
        return ResponseEntity.ok(novoProjeto);
    }

    @GetMapping
    public ResponseEntity<List<ProjetoViewDTO>> buscarProjetos() {
        List<ProjetoViewDTO> projetos = projetoService.buscarProjetos();
        return ResponseEntity.ok(projetos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoViewDTO> buscarProjetoPorId(@PathVariable Long id) {
        return projetoService.buscarProjetoPorId(id)
                .map(projetoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/custo-total/{id}")
    public ResponseEntity<Double> calcularCustoTotal(@PathVariable Long id) {
        try {
            double custo = projetoService.calcularCustoTotal(
                projetoService.buscarProjetoPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."))
            );
            return ResponseEntity.ok(custo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
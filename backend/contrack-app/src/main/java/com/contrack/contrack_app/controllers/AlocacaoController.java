// src/main/java/com/contrack/contrack_app/controllers/AlocacaoController.java

package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.mapper.AlocacaoMapper;
import com.contrack.contrack_app.services.AlocacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/alocacoes")
public class AlocacaoController {

    private final AlocacaoService alocacaoService;
    private final AlocacaoMapper alocacaoMapper;

    public AlocacaoController(AlocacaoService alocacaoService, AlocacaoMapper alocacaoMapper) {
        this.alocacaoService = alocacaoService;
        this.alocacaoMapper = alocacaoMapper;
    }

    // Endpoint para criar uma nova alocação
    @PostMapping
    public ResponseEntity<AlocacaoViewDTO> criarAlocacao(@RequestBody AlocacaoCreateDTO dto) {
        try {
            AlocacaoViewDTO novaAlocacao = alocacaoService.criarAlocacao(dto);
            return ResponseEntity.ok(novaAlocacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint para buscar todas as alocações
    @GetMapping
    public ResponseEntity<List<AlocacaoViewDTO>> buscarAlocacoes() {
        List<AlocacaoViewDTO> alocacoes = alocacaoService.buscarAlocacoes();
        return ResponseEntity.ok(alocacoes);
    }
    
    // Endpoint para buscar alocação por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlocacaoViewDTO> buscarAlocacaoPorId(@PathVariable Long id) {
        return alocacaoService.buscarAlocacaoPorId(id)
                .map(alocacaoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/projetos/{projetoId}")
    public ResponseEntity<List<AlocacaoViewDTO>> buscarAlocacoesPorProjeto(@PathVariable Long projetoId) {
        try {
            List<AlocacaoViewDTO> alocacoes = alocacaoService.buscarAlocacoesPorProjetoId(projetoId);
            return ResponseEntity.ok(alocacoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.mapper.AlocacaoMapper;
import com.contrack.contrack_app.services.AlocacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alocacoes")
public class AlocacaoController {

    private final AlocacaoService alocacaoService;
    private final AlocacaoMapper alocacaoMapper;

    public AlocacaoController(AlocacaoService alocacaoService, AlocacaoMapper alocacaoMapper) {
        this.alocacaoService = alocacaoService;
        this.alocacaoMapper = alocacaoMapper;
    }

    @PostMapping
    public ResponseEntity<AlocacaoViewDTO> criarAlocacao(@RequestBody AlocacaoCreateDTO dto) {
        try {
            AlocacaoViewDTO novaAlocacao = alocacaoService.criarAlocacao(dto);
            return ResponseEntity.ok(novaAlocacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AlocacaoViewDTO>> buscarAlocacoes() {
        List<AlocacaoViewDTO> alocacoes = alocacaoService.buscarAlocacoes();
        return ResponseEntity.ok(alocacoes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlocacaoViewDTO> buscarAlocacaoPorId(@PathVariable Long id) {
        return alocacaoService.buscarAlocacaoPorId(id)
                .map(alocacaoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
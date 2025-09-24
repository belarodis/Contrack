package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.services.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ResponseEntity<ContratoViewDTO> criarContrato(@RequestBody ContratoCreateDTO dto) {
        try {
            ContratoViewDTO novoContrato = contratoService.criarContrato(dto);
            return ResponseEntity.ok(novoContrato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ContratoViewDTO>> buscarContratos() {
        List<ContratoViewDTO> contratos = contratoService.buscarContratos();
        return ResponseEntity.ok(contratos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContratoViewDTO> buscarContratoPorId(@PathVariable Long id) {
        return contratoService.buscarContratoPorIdComStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
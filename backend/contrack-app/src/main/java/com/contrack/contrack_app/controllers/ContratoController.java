package com.contrack.contrack_app.controllers;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.services.ContratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ResponseEntity<ContratoViewDTO> criarContrato(@RequestBody ContratoCreateDTO dto) {
        // Exceções (404, 409, 400) são tratadas pelo GlobalExceptionHandler
        ContratoViewDTO novoContrato = contratoService.criarContrato(dto);
        return ResponseEntity.ok(novoContrato);
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
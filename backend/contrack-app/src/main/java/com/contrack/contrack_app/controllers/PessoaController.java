package com.contrack.contrack_app.controllers;


import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.mapper.PessoaMapper;
import com.contrack.contrack_app.services.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    public PessoaController(PessoaService pessoaService, PessoaMapper pessoaMapper) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapper;
    }

    @PostMapping
    public ResponseEntity<PessoaViewDTO> criarPessoa(@RequestBody PessoaCreateDTO dto) {
        PessoaViewDTO novaPessoa = pessoaService.criarPessoa(dto);
        return ResponseEntity.ok(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaViewDTO>> buscarPessoas() {
        List<PessoaViewDTO> pessoas = pessoaService.buscarPessoas();
        return ResponseEntity.ok(pessoas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PessoaViewDTO> buscarPessoaPorId(@PathVariable Long id) {
        return pessoaService.buscarPessoaPorId(id)
                .map(pessoaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
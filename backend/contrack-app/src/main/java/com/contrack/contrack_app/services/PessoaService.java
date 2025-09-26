package com.contrack.contrack_app.services;

import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.exceptions.ResourceNotFoundException; 
import com.contrack.contrack_app.mapper.PessoaMapper;
import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final IPessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public PessoaService(IPessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    public PessoaViewDTO criarPessoa(PessoaCreateDTO dto) {
        Pessoa pessoa = pessoaMapper.toEntity(dto);
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoaSalva);
    }

    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }
    
    public Pessoa buscarPessoaPorIdOrThrow(Long id) {
        return pessoaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa", id));
    }

    public List<PessoaViewDTO> buscarPessoas() {
        return pessoaRepository.findAll()
                .stream()
                .map(pessoaMapper::toDto)
                .sorted(Comparator
                        .comparing(PessoaViewDTO::disponivel).reversed() // ativos primeiro
                        .thenComparing(PessoaViewDTO::nome)) //dps ordena tudo
                .collect(Collectors.toList());
    }
}
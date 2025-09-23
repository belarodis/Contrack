package com.contrack.contrack_app.services;

import com.contrack.contrack_app.models.Pessoa;
import com.contrack.contrack_app.repositories.interfaces.IPessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    private final IPessoaRepository pessoaRepository;

    public PessoaService(IPessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }
}
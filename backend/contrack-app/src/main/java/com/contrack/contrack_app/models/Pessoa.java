package com.contrack.contrack_app.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Pessoa {

    public Pessoa() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alocacao> alocacoes;

    public Pessoa(Long id, String nome, List<Contrato> contratos, List<Alocacao> alocacoes) {
        this.id = id;
        this.nome = nome;
        this.contratos = contratos;
        this.alocacoes = alocacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Alocacao> getAlocacoes() {
        return alocacoes;
    }

    public void setAlocacoes(List<Alocacao> alocacoes) {
        this.alocacoes = alocacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id) && Objects.equals(nome, pessoa.nome) && Objects.equals(contratos, pessoa.contratos) && Objects.equals(alocacoes, pessoa.alocacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, contratos, alocacoes);
    }
}

package com.contrack.contrack_app.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity

public class Alocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int horasSemana;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    public Alocacao(){}

    public Alocacao(Long id, int horasSemana, Pessoa pessoa, Projeto projeto) {
        this.id = id;
        this.horasSemana = horasSemana;
        this.pessoa = pessoa;
        this.projeto = projeto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alocacao alocacao = (Alocacao) o;
        return horasSemana == alocacao.horasSemana && Objects.equals(id, alocacao.id) && Objects.equals(pessoa, alocacao.pessoa) && Objects.equals(projeto, alocacao.projeto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, horasSemana, pessoa, projeto);
    }
}

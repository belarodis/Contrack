package com.contrack.contrack_app.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private int horasSemana;

    private double salarioHora;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;


    public Contrato() {}
    public Contrato(Long id, LocalDate dataInicio, LocalDate dataFim, int horasSemana, double salarioHora, Pessoa pessoa) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horasSemana = horasSemana;
        this.salarioHora = salarioHora;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

    public double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return horasSemana == contrato.horasSemana && Double.compare(salarioHora, contrato.salarioHora) == 0 && Objects.equals(id, contrato.id) && Objects.equals(dataInicio, contrato.dataInicio) && Objects.equals(dataFim, contrato.dataFim) && Objects.equals(pessoa, contrato.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataInicio, dataFim, horasSemana, salarioHora, pessoa);
    }
}

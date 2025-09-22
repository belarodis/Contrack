package com.contrack.contrack_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}

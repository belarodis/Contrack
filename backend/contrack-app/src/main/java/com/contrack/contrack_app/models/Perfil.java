package com.contrack.contrack_app.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;

    public enum TipoPerfil {
        GERENTE,
        DEV,
        QA
    }
}

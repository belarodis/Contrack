package com.contrack.contrack_app.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity

public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;

    public enum TipoPerfil {
        GERENTE,
        DEV,
        QA,
        SECURITY
    }

    public Perfil() {}

    public Perfil(Long id, TipoPerfil tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public TipoPerfil getTipo() {
        return tipo;
    }

    public void setTipo(TipoPerfil tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id) && tipo == perfil.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo);
    }
}

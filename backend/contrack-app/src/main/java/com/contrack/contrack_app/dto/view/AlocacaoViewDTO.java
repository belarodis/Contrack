package com.contrack.contrack_app.dto.view;

public record AlocacaoViewDTO(
    Long id,
    Integer horasSemana,
    Long pessoaId,
    String nomePessoa, 
    Long projetoId,
    String nomeProjeto,
    Long perfilId,
    String tipoPerfil 
) {}
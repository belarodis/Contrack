package com.contrack.contrack_app.dto.view;

import java.time.LocalDate;

public record ProjetoViewDTO(Long id, String nome, LocalDate dataInicio, LocalDate dataFim, String descricao, String status) {
}

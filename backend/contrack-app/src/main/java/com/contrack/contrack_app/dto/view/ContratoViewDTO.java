package com.contrack.contrack_app.dto.view;

import java.time.LocalDate;

public record ContratoViewDTO(Long id, LocalDate dataInicio, LocalDate dataFim,  Integer horasSemana,  Double salarioHora,  Long pessoaId,     String status
) {
}

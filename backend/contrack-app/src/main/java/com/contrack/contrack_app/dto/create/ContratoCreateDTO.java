package com.contrack.contrack_app.dto.create;

import java.time.LocalDate;

public record ContratoCreateDTO(LocalDate dataInicio, LocalDate dataFim, Integer horasSemana, Double salarioHora, Long pessoaId) {
}

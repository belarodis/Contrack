package com.contrack.contrack_app.dto.create;

import java.time.LocalDate;

public record ProjetoCreateDTO (String nome, LocalDate dataInicio, LocalDate dataFim, String descricao){
}

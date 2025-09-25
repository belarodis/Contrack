package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Contrato;
import org.mapstruct.*;
import java.time.LocalDate;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class})
public interface ContratoMapper {

    @Mapping(target = "pessoaId", source = "pessoa.id")
    @Mapping(target = "status", expression = "java(calcularStatus(entity))")
    ContratoViewDTO toDto(Contrato entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "pessoaId", qualifiedByName = "pessoaFromId")
    Contrato toEntity(ContratoCreateDTO dto);

    default String calcularStatus(Contrato contrato) {
        LocalDate hoje = LocalDate.now();
        if (hoje.isAfter(contrato.getDataInicio()) && (contrato.getDataFim() == null || hoje.isBefore(contrato.getDataFim()) || hoje.isEqual(contrato.getDataFim()))) {
            return "Ativo";
        } else {
            return "Inativo";
        }
    }
}
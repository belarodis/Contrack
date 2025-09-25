package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Contrato;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class})
public interface ContratoMapper {

    @Mapping(target = "pessoaId", source = "pessoa.id")
    ContratoViewDTO toDto(Contrato entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "pessoaId", qualifiedByName = "pessoaFromId")
    Contrato toEntity(ContratoCreateDTO dto);
}
package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.ContratoCreateDTO;
import com.contrack.contrack_app.dto.view.ContratoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Contrato;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class, PerfilMapper.class})
public interface ContratoMapper {

    // VIEW
    @Mapping(target = "pessoaId", source = "pessoa.id")
    @Mapping(target = "perfilId", source = "perfil.id")
    ContratoViewDTO toDto(Contrato entity);

    // CREATE -> ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "pessoaId", qualifiedByName = "pessoaFromId")
    @Mapping(target = "perfil", source = "perfilId", qualifiedByName = "perfilFromId")
    Contrato toEntity(ContratoCreateDTO dto);
}
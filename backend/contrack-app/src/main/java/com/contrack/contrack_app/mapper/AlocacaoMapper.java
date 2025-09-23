package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Alocacao;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class, ProjetoMapper.class})
public interface AlocacaoMapper {

    // VIEW
    @Mapping(target = "pessoaId", source = "pessoa.id")
    @Mapping(target = "projetoId", source = "projeto.id")
    AlocacaoViewDTO toDto(Alocacao entity);

    // CREATE -> ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "pessoaId", qualifiedByName = "pessoaFromId")
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoFromId")
    Alocacao toEntity(AlocacaoCreateDTO dto);
}
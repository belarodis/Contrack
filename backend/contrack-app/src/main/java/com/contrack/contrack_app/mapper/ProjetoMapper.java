package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.ProjetoCreateDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Projeto;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface ProjetoMapper {

    ProjetoViewDTO toDto(Projeto entity);

    @Mapping(target = "id", ignore = true)
    Projeto toEntity(ProjetoCreateDTO dto);

    @Named("projetoFromId")
    default Projeto fromId(Long id) {
        if (id == null) return null;
        Projeto p = new Projeto();
        p.setId(id);
        return p;
    }
}
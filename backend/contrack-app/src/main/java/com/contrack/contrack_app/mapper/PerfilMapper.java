package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.PerfilCreateDTO;
import com.contrack.contrack_app.dto.view.PerfilViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Perfil;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface PerfilMapper {

    // VIEW
    @Mapping(target = "tipo", expression = "java(entity.getTipo())")
    PerfilViewDTO toDto(Perfil entity);

    // CREATE -> ENTITY
    @Mapping(target = "id", ignore = true)
    Perfil toEntity(PerfilCreateDTO dto);

    @Named("perfilFromId")
    default Perfil fromId(Long id) {
        if (id == null) return null;
        Perfil p = new Perfil();
        p.setId(id);
        return p;
    }
}
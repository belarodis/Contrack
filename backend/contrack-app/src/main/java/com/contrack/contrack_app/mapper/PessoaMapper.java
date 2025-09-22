package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Pessoa;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface PessoaMapper {

    // VIEW
    PessoaViewDTO toDto(Pessoa entity);

    // CREATE -> ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contratos", ignore = true)
    @Mapping(target = "alocacoes", ignore = true)
    Pessoa toEntity(PessoaCreateDTO dto);

    // helper
    @Named("pessoaFromId")
    default Pessoa fromId(Long id) {
        if (id == null) return null;
        Pessoa p = new Pessoa();
        p.setId(id);
        return p;
    }
}
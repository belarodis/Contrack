package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Pessoa;

import java.time.LocalDate;

import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class)
public interface PessoaMapper {

    // VIEW
    @Mapping(target = "disponivel", expression = "java(verificarDisponibilidade(entity))")
    PessoaViewDTO toDto(Pessoa entity);

    // CREATE -> ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contratos", ignore = true)
    @Mapping(target = "alocacoes", ignore = true)
    Pessoa toEntity(PessoaCreateDTO dto);

    // helper
    @Named("pessoaFromId")
    default Pessoa fromId(Long id) {
        if (id == null)
            return null;
        Pessoa p = new Pessoa();
        p.setId(id);
        return p;
    }
    
    default Boolean verificarDisponibilidade(Pessoa entity) {
    if (entity == null || entity.getContratos() == null) return true; // sem contratos = disponível

    LocalDate hoje = LocalDate.now();

    boolean temAtivo = entity.getContratos().stream()
                 .anyMatch(c -> !hoje.isBefore(c.getDataInicio()) && !hoje.isAfter(c.getDataFim()));

    return !temAtivo;
    }
}
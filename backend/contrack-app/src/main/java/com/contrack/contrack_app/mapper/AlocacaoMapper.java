// src/main/java/com/contrack/contrack_app/mapper/AlocacaoMapper.java

package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.AlocacaoCreateDTO;
import com.contrack.contrack_app.dto.view.AlocacaoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Alocacao;
import org.mapstruct.*;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class, ProjetoMapper.class, PerfilMapper.class})
public interface AlocacaoMapper {

    @Mapping(target = "pessoaId", source = "pessoa.id")
    @Mapping(target = "nomePessoa", source = "pessoa.nome") 
    @Mapping(target = "projetoId", source = "projeto.id")
    @Mapping(target = "nomeProjeto", source = "projeto.nome") 
    @Mapping(target = "perfilId", source = "perfil.id")
    @Mapping(target = "tipoPerfil", source = "perfil.tipo") 
    AlocacaoViewDTO toDto(Alocacao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pessoa", source = "pessoaId", qualifiedByName = "pessoaFromId")
    @Mapping(target = "projeto", source = "projetoId", qualifiedByName = "projetoFromId")
    @Mapping(target = "perfil", source = "perfilId", qualifiedByName = "perfilFromId")
    Alocacao toEntity(AlocacaoCreateDTO dto);
}
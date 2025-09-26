// src/main/java/com/contrack/contrack_app/mapper/ProjetoMapper.java

package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.ProjetoCreateDTO;
import com.contrack.contrack_app.dto.view.ProjetoViewDTO;
import com.contrack.contrack_app.mapper.config.MapStructCentralConfig;
import com.contrack.contrack_app.models.Alocacao;
import com.contrack.contrack_app.models.Perfil;
import com.contrack.contrack_app.models.Projeto;
import com.contrack.contrack_app.repositories.interfaces.IAlocacaoRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Mapper(config = MapStructCentralConfig.class, uses = {PessoaMapper.class})
public abstract class ProjetoMapper {

    @Autowired
    private IAlocacaoRepository alocacaoRepository;

    @Mapping(target = "status", expression = "java(determinarStatus(entity))")
    public abstract ProjetoViewDTO toDto(Projeto entity);

    @Mapping(target = "id", ignore = true)
    public abstract Projeto toEntity(ProjetoCreateDTO dto);

    @Named("projetoFromId")
    public Projeto fromId(Long id) {
        if (id == null) return null;
        Projeto p = new Projeto();
        p.setId(id);
        return p;
    }

    protected String determinarStatus(Projeto projeto) {
        LocalDate hoje = LocalDate.now();

        // 1. Finalizado: se a data de hoje já passou da data de fim do projeto.
        if (hoje.isAfter(projeto.getDataFim())) {
            return "Finalizado";
        }

        // 2. Em espera: se a data de hoje ainda não chegou na data de início.
        if (hoje.isBefore(projeto.getDataInicio())) {
            return "Em espera";
        }

        // Para os status Incompleto e Ativo, precisamos verificar as alocações.
        List<Alocacao> alocacoes = alocacaoRepository.findByProjeto(projeto);

        // Contar alocações por perfil
        long gerenteCount = alocacoes.stream()
            .filter(a -> a.getPerfil().getTipo() == Perfil.TipoPerfil.gerente)
            .count();
        long devCount = alocacoes.stream()
            .filter(a -> a.getPerfil().getTipo() == Perfil.TipoPerfil.dev)
            .count();
        long qaCount = alocacoes.stream()
            .filter(a -> a.getPerfil().getTipo() == Perfil.TipoPerfil.qa)
            .count();

        // Regras de negócio do exercício:
        // - Cada projeto deve ter exatamente 1 gerente;
        // - Cada projeto deve ter pelo menos: 1 desenvolvedor (Dev), 1 QA.
        boolean temGerente = gerenteCount == 1;
        boolean temDev = devCount >= 1;
        boolean temQA = qaCount >= 1;

        // 3. Incompleto: se o projeto ainda não atende aos requisitos de alocação.
        if (!temGerente || !temDev || !temQA) {
            return "Incompleto";
        }

        // 4. Ativo: se o projeto está dentro do prazo e atende a todos os requisitos de alocação.
        return "Ativo";
    }
}
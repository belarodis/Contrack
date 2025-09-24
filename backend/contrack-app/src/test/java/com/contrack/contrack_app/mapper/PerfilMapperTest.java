package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.PerfilCreateDTO;
import com.contrack.contrack_app.dto.view.PerfilViewDTO;
import com.contrack.contrack_app.models.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PerfilMapperTest {

    @Autowired
    private PerfilMapper mapper;

    @Test
    void deveMapearPerfilParaDTO() {
        Perfil p = new Perfil();
        p.setId(1L);
        p.setTipo(Perfil.TipoPerfil.SECURITY);

        PerfilViewDTO dto = mapper.toDto(p);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.tipo()).isEqualTo(Perfil.TipoPerfil.SECURITY);
    }

    @Test
    void deveMapearDTOParaPerfil() {
        PerfilCreateDTO dto = new PerfilCreateDTO(Perfil.TipoPerfil.DEV);
        Perfil entity = mapper.toEntity(dto);

        assertThat(entity.getTipo()).isEqualTo(Perfil.TipoPerfil.DEV);
        assertThat(entity.getId()).isNull();
    }
}

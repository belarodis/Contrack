package com.contrack.contrack_app.mapper;

import com.contrack.contrack_app.dto.create.PessoaCreateDTO;
import com.contrack.contrack_app.dto.view.PessoaViewDTO;
import com.contrack.contrack_app.models.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PessoaMapperTest {

    @Autowired
    private PessoaMapper mapper;

    @Test
    void deveMapearPessoaParaDTO() {
        Pessoa p = new Pessoa();
        p.setId(1L);
        p.setNome("Lucas");

        PessoaViewDTO dto = mapper.toDto(p);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.nome()).isEqualTo("Lucas");
    }

    @Test
    void deveMapearDTOParaPessoa() {
        PessoaCreateDTO dto = new PessoaCreateDTO("Julia");
        Pessoa entity = mapper.toEntity(dto);

        assertThat(entity.getNome()).isEqualTo("Julia");
        assertThat(entity.getId()).isNull(); // ID ignorado pelo mapper
    }
}

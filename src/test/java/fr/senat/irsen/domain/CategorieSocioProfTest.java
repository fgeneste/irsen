package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieSocioProfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieSocioProf.class);
        CategorieSocioProf categorieSocioProf1 = new CategorieSocioProf();
        categorieSocioProf1.setId(1L);
        CategorieSocioProf categorieSocioProf2 = new CategorieSocioProf();
        categorieSocioProf2.setId(categorieSocioProf1.getId());
        assertThat(categorieSocioProf1).isEqualTo(categorieSocioProf2);
        categorieSocioProf2.setId(2L);
        assertThat(categorieSocioProf1).isNotEqualTo(categorieSocioProf2);
        categorieSocioProf1.setId(null);
        assertThat(categorieSocioProf1).isNotEqualTo(categorieSocioProf2);
    }
}

package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DecorationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Decoration.class);
        Decoration decoration1 = new Decoration();
        decoration1.setId(1L);
        Decoration decoration2 = new Decoration();
        decoration2.setId(decoration1.getId());
        assertThat(decoration1).isEqualTo(decoration2);
        decoration2.setId(2L);
        assertThat(decoration1).isNotEqualTo(decoration2);
        decoration1.setId(null);
        assertThat(decoration1).isNotEqualTo(decoration2);
    }
}

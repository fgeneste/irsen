package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SenateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Senateur.class);
        Senateur senateur1 = new Senateur();
        senateur1.setId(1L);
        Senateur senateur2 = new Senateur();
        senateur2.setId(senateur1.getId());
        assertThat(senateur1).isEqualTo(senateur2);
        senateur2.setId(2L);
        assertThat(senateur1).isNotEqualTo(senateur2);
        senateur1.setId(null);
        assertThat(senateur1).isNotEqualTo(senateur2);
    }
}

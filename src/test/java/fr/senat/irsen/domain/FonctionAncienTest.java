package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FonctionAncienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FonctionAncien.class);
        FonctionAncien fonctionAncien1 = new FonctionAncien();
        fonctionAncien1.setId(1L);
        FonctionAncien fonctionAncien2 = new FonctionAncien();
        fonctionAncien2.setId(fonctionAncien1.getId());
        assertThat(fonctionAncien1).isEqualTo(fonctionAncien2);
        fonctionAncien2.setId(2L);
        assertThat(fonctionAncien1).isNotEqualTo(fonctionAncien2);
        fonctionAncien1.setId(null);
        assertThat(fonctionAncien1).isNotEqualTo(fonctionAncien2);
    }
}

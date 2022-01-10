package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MandatAncienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandatAncien.class);
        MandatAncien mandatAncien1 = new MandatAncien();
        mandatAncien1.setId(1L);
        MandatAncien mandatAncien2 = new MandatAncien();
        mandatAncien2.setId(mandatAncien1.getId());
        assertThat(mandatAncien1).isEqualTo(mandatAncien2);
        mandatAncien2.setId(2L);
        assertThat(mandatAncien1).isNotEqualTo(mandatAncien2);
        mandatAncien1.setId(null);
        assertThat(mandatAncien1).isNotEqualTo(mandatAncien2);
    }
}

package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MandatEnCoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandatEnCours.class);
        MandatEnCours mandatEnCours1 = new MandatEnCours();
        mandatEnCours1.setId(1L);
        MandatEnCours mandatEnCours2 = new MandatEnCours();
        mandatEnCours2.setId(mandatEnCours1.getId());
        assertThat(mandatEnCours1).isEqualTo(mandatEnCours2);
        mandatEnCours2.setId(2L);
        assertThat(mandatEnCours1).isNotEqualTo(mandatEnCours2);
        mandatEnCours1.setId(null);
        assertThat(mandatEnCours1).isNotEqualTo(mandatEnCours2);
    }
}

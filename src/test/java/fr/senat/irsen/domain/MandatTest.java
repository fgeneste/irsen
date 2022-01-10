package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MandatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mandat.class);
        Mandat mandat1 = new Mandat();
        mandat1.setId(1L);
        Mandat mandat2 = new Mandat();
        mandat2.setId(mandat1.getId());
        assertThat(mandat1).isEqualTo(mandat2);
        mandat2.setId(2L);
        assertThat(mandat1).isNotEqualTo(mandat2);
        mandat1.setId(null);
        assertThat(mandat1).isNotEqualTo(mandat2);
    }
}

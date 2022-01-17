package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdressesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresses.class);
        Adresses adresses1 = new Adresses();
        adresses1.setId(1L);
        Adresses adresses2 = new Adresses();
        adresses2.setId(adresses1.getId());
        assertThat(adresses1).isEqualTo(adresses2);
        adresses2.setId(2L);
        assertThat(adresses1).isNotEqualTo(adresses2);
        adresses1.setId(null);
        assertThat(adresses1).isNotEqualTo(adresses2);
    }
}

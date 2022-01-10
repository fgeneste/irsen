package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresseFiscaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresseFiscale.class);
        AdresseFiscale adresseFiscale1 = new AdresseFiscale();
        adresseFiscale1.setId(1L);
        AdresseFiscale adresseFiscale2 = new AdresseFiscale();
        adresseFiscale2.setId(adresseFiscale1.getId());
        assertThat(adresseFiscale1).isEqualTo(adresseFiscale2);
        adresseFiscale2.setId(2L);
        assertThat(adresseFiscale1).isNotEqualTo(adresseFiscale2);
        adresseFiscale1.setId(null);
        assertThat(adresseFiscale1).isNotEqualTo(adresseFiscale2);
    }
}

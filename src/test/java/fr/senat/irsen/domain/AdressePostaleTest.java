package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdressePostaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdressePostale.class);
        AdressePostale adressePostale1 = new AdressePostale();
        adressePostale1.setId(1L);
        AdressePostale adressePostale2 = new AdressePostale();
        adressePostale2.setId(adressePostale1.getId());
        assertThat(adressePostale1).isEqualTo(adressePostale2);
        adressePostale2.setId(2L);
        assertThat(adressePostale1).isNotEqualTo(adressePostale2);
        adressePostale1.setId(null);
        assertThat(adressePostale1).isNotEqualTo(adressePostale2);
    }
}

package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdressePostale2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdressePostale2.class);
        AdressePostale2 adressePostale21 = new AdressePostale2();
        adressePostale21.setId(1L);
        AdressePostale2 adressePostale22 = new AdressePostale2();
        adressePostale22.setId(adressePostale21.getId());
        assertThat(adressePostale21).isEqualTo(adressePostale22);
        adressePostale22.setId(2L);
        assertThat(adressePostale21).isNotEqualTo(adressePostale22);
        adressePostale21.setId(null);
        assertThat(adressePostale21).isNotEqualTo(adressePostale22);
    }
}

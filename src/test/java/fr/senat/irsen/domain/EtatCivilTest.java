package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtatCivilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatCivil.class);
        EtatCivil etatCivil1 = new EtatCivil();
        etatCivil1.setId(1L);
        EtatCivil etatCivil2 = new EtatCivil();
        etatCivil2.setId(etatCivil1.getId());
        assertThat(etatCivil1).isEqualTo(etatCivil2);
        etatCivil2.setId(2L);
        assertThat(etatCivil1).isNotEqualTo(etatCivil2);
        etatCivil1.setId(null);
        assertThat(etatCivil1).isNotEqualTo(etatCivil2);
    }
}

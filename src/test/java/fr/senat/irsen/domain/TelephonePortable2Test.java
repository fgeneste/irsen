package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelephonePortable2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephonePortable2.class);
        TelephonePortable2 telephonePortable21 = new TelephonePortable2();
        telephonePortable21.setId(1L);
        TelephonePortable2 telephonePortable22 = new TelephonePortable2();
        telephonePortable22.setId(telephonePortable21.getId());
        assertThat(telephonePortable21).isEqualTo(telephonePortable22);
        telephonePortable22.setId(2L);
        assertThat(telephonePortable21).isNotEqualTo(telephonePortable22);
        telephonePortable21.setId(null);
        assertThat(telephonePortable21).isNotEqualTo(telephonePortable22);
    }
}

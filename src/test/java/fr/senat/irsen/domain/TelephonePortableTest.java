package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelephonePortableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephonePortable.class);
        TelephonePortable telephonePortable1 = new TelephonePortable();
        telephonePortable1.setId(1L);
        TelephonePortable telephonePortable2 = new TelephonePortable();
        telephonePortable2.setId(telephonePortable1.getId());
        assertThat(telephonePortable1).isEqualTo(telephonePortable2);
        telephonePortable2.setId(2L);
        assertThat(telephonePortable1).isNotEqualTo(telephonePortable2);
        telephonePortable1.setId(null);
        assertThat(telephonePortable1).isNotEqualTo(telephonePortable2);
    }
}

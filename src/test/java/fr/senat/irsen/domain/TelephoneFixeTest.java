package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelephoneFixeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephoneFixe.class);
        TelephoneFixe telephoneFixe1 = new TelephoneFixe();
        telephoneFixe1.setId(1L);
        TelephoneFixe telephoneFixe2 = new TelephoneFixe();
        telephoneFixe2.setId(telephoneFixe1.getId());
        assertThat(telephoneFixe1).isEqualTo(telephoneFixe2);
        telephoneFixe2.setId(2L);
        assertThat(telephoneFixe1).isNotEqualTo(telephoneFixe2);
        telephoneFixe1.setId(null);
        assertThat(telephoneFixe1).isNotEqualTo(telephoneFixe2);
    }
}

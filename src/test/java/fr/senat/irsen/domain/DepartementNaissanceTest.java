package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartementNaissanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartementNaissance.class);
        DepartementNaissance departementNaissance1 = new DepartementNaissance();
        departementNaissance1.setId(1L);
        DepartementNaissance departementNaissance2 = new DepartementNaissance();
        departementNaissance2.setId(departementNaissance1.getId());
        assertThat(departementNaissance1).isEqualTo(departementNaissance2);
        departementNaissance2.setId(2L);
        assertThat(departementNaissance1).isNotEqualTo(departementNaissance2);
        departementNaissance1.setId(null);
        assertThat(departementNaissance1).isNotEqualTo(departementNaissance2);
    }
}

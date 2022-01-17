package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaysNaissanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaysNaissance.class);
        PaysNaissance paysNaissance1 = new PaysNaissance();
        paysNaissance1.setId(1L);
        PaysNaissance paysNaissance2 = new PaysNaissance();
        paysNaissance2.setId(paysNaissance1.getId());
        assertThat(paysNaissance1).isEqualTo(paysNaissance2);
        paysNaissance2.setId(2L);
        assertThat(paysNaissance1).isNotEqualTo(paysNaissance2);
        paysNaissance1.setId(null);
        assertThat(paysNaissance1).isNotEqualTo(paysNaissance2);
    }
}

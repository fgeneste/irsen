package fr.senat.irsen.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.senat.irsen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FonctionEnCoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FonctionEnCours.class);
        FonctionEnCours fonctionEnCours1 = new FonctionEnCours();
        fonctionEnCours1.setId(1L);
        FonctionEnCours fonctionEnCours2 = new FonctionEnCours();
        fonctionEnCours2.setId(fonctionEnCours1.getId());
        assertThat(fonctionEnCours1).isEqualTo(fonctionEnCours2);
        fonctionEnCours2.setId(2L);
        assertThat(fonctionEnCours1).isNotEqualTo(fonctionEnCours2);
        fonctionEnCours1.setId(null);
        assertThat(fonctionEnCours1).isNotEqualTo(fonctionEnCours2);
    }
}

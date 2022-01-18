package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.AdressePostale;
import fr.senat.irsen.repository.AdressePostaleRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdressePostaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdressePostaleResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_BISTER = "AAAAAAAAAA";
    private static final String UPDATED_BISTER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT_1 = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_VOIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_VOIE = "BBBBBBBBBB";

    private static final String DEFAULT_VOIE = "AAAAAAAAAA";
    private static final String UPDATED_VOIE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AFFICHAGE_INTERNET = false;
    private static final Boolean UPDATED_AFFICHAGE_INTERNET = true;

    private static final Boolean DEFAULT_AFFICHAGE_INTRANET = false;
    private static final Boolean UPDATED_AFFICHAGE_INTRANET = true;

    private static final String ENTITY_API_URL = "/api/adresse-postales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdressePostaleRepository adressePostaleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdressePostaleMockMvc;

    private AdressePostale adressePostale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdressePostale createEntity(EntityManager em) {
        AdressePostale adressePostale = new AdressePostale()
            .numero(DEFAULT_NUMERO)
            .bister(DEFAULT_BISTER)
            .complement1(DEFAULT_COMPLEMENT_1)
            .complement2(DEFAULT_COMPLEMENT_2)
            .typeVoie(DEFAULT_TYPE_VOIE)
            .voie(DEFAULT_VOIE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .affichageInternet(DEFAULT_AFFICHAGE_INTERNET)
            .affichageIntranet(DEFAULT_AFFICHAGE_INTRANET);
        return adressePostale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdressePostale createUpdatedEntity(EntityManager em) {
        AdressePostale adressePostale = new AdressePostale()
            .numero(UPDATED_NUMERO)
            .bister(UPDATED_BISTER)
            .complement1(UPDATED_COMPLEMENT_1)
            .complement2(UPDATED_COMPLEMENT_2)
            .typeVoie(UPDATED_TYPE_VOIE)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .affichageInternet(UPDATED_AFFICHAGE_INTERNET)
            .affichageIntranet(UPDATED_AFFICHAGE_INTRANET);
        return adressePostale;
    }

    @BeforeEach
    public void initTest() {
        adressePostale = createEntity(em);
    }

    @Test
    @Transactional
    void createAdressePostale() throws Exception {
        int databaseSizeBeforeCreate = adressePostaleRepository.findAll().size();
        // Create the AdressePostale
        restAdressePostaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isCreated());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeCreate + 1);
        AdressePostale testAdressePostale = adressePostaleList.get(adressePostaleList.size() - 1);
        assertThat(testAdressePostale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdressePostale.getBister()).isEqualTo(DEFAULT_BISTER);
        assertThat(testAdressePostale.getComplement1()).isEqualTo(DEFAULT_COMPLEMENT_1);
        assertThat(testAdressePostale.getComplement2()).isEqualTo(DEFAULT_COMPLEMENT_2);
        assertThat(testAdressePostale.getTypeVoie()).isEqualTo(DEFAULT_TYPE_VOIE);
        assertThat(testAdressePostale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdressePostale.getAffichageInternet()).isEqualTo(DEFAULT_AFFICHAGE_INTERNET);
        assertThat(testAdressePostale.getAffichageIntranet()).isEqualTo(DEFAULT_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void createAdressePostaleWithExistingId() throws Exception {
        // Create the AdressePostale with an existing ID
        adressePostale.setId(1L);

        int databaseSizeBeforeCreate = adressePostaleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdressePostaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdressePostales() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        // Get all the adressePostaleList
        restAdressePostaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adressePostale.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bister").value(hasItem(DEFAULT_BISTER)))
            .andExpect(jsonPath("$.[*].complement1").value(hasItem(DEFAULT_COMPLEMENT_1)))
            .andExpect(jsonPath("$.[*].complement2").value(hasItem(DEFAULT_COMPLEMENT_2)))
            .andExpect(jsonPath("$.[*].typeVoie").value(hasItem(DEFAULT_TYPE_VOIE)))
            .andExpect(jsonPath("$.[*].voie").value(hasItem(DEFAULT_VOIE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].affichageInternet").value(hasItem(DEFAULT_AFFICHAGE_INTERNET.booleanValue())))
            .andExpect(jsonPath("$.[*].affichageIntranet").value(hasItem(DEFAULT_AFFICHAGE_INTRANET.booleanValue())));
    }

    @Test
    @Transactional
    void getAdressePostale() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        // Get the adressePostale
        restAdressePostaleMockMvc
            .perform(get(ENTITY_API_URL_ID, adressePostale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adressePostale.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bister").value(DEFAULT_BISTER))
            .andExpect(jsonPath("$.complement1").value(DEFAULT_COMPLEMENT_1))
            .andExpect(jsonPath("$.complement2").value(DEFAULT_COMPLEMENT_2))
            .andExpect(jsonPath("$.typeVoie").value(DEFAULT_TYPE_VOIE))
            .andExpect(jsonPath("$.voie").value(DEFAULT_VOIE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.affichageInternet").value(DEFAULT_AFFICHAGE_INTERNET.booleanValue()))
            .andExpect(jsonPath("$.affichageIntranet").value(DEFAULT_AFFICHAGE_INTRANET.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdressePostale() throws Exception {
        // Get the adressePostale
        restAdressePostaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdressePostale() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();

        // Update the adressePostale
        AdressePostale updatedAdressePostale = adressePostaleRepository.findById(adressePostale.getId()).get();
        // Disconnect from session so that the updates on updatedAdressePostale are not directly saved in db
        em.detach(updatedAdressePostale);
        updatedAdressePostale
            .numero(UPDATED_NUMERO)
            .bister(UPDATED_BISTER)
            .complement1(UPDATED_COMPLEMENT_1)
            .complement2(UPDATED_COMPLEMENT_2)
            .typeVoie(UPDATED_TYPE_VOIE)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .affichageInternet(UPDATED_AFFICHAGE_INTERNET)
            .affichageIntranet(UPDATED_AFFICHAGE_INTRANET);

        restAdressePostaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdressePostale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdressePostale))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
        AdressePostale testAdressePostale = adressePostaleList.get(adressePostaleList.size() - 1);
        assertThat(testAdressePostale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale.getBister()).isEqualTo(UPDATED_BISTER);
        assertThat(testAdressePostale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdressePostale.getComplement2()).isEqualTo(UPDATED_COMPLEMENT_2);
        assertThat(testAdressePostale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdressePostale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdressePostale.getAffichageIntranet()).isEqualTo(UPDATED_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void putNonExistingAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adressePostale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdressePostaleWithPatch() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();

        // Update the adressePostale using partial update
        AdressePostale partialUpdatedAdressePostale = new AdressePostale();
        partialUpdatedAdressePostale.setId(adressePostale.getId());

        partialUpdatedAdressePostale
            .complement1(UPDATED_COMPLEMENT_1)
            .complement2(UPDATED_COMPLEMENT_2)
            .typeVoie(UPDATED_TYPE_VOIE)
            .affichageInternet(UPDATED_AFFICHAGE_INTERNET);

        restAdressePostaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdressePostale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdressePostale))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
        AdressePostale testAdressePostale = adressePostaleList.get(adressePostaleList.size() - 1);
        assertThat(testAdressePostale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdressePostale.getBister()).isEqualTo(DEFAULT_BISTER);
        assertThat(testAdressePostale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdressePostale.getComplement2()).isEqualTo(UPDATED_COMPLEMENT_2);
        assertThat(testAdressePostale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdressePostale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdressePostale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdressePostale.getAffichageIntranet()).isEqualTo(DEFAULT_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void fullUpdateAdressePostaleWithPatch() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();

        // Update the adressePostale using partial update
        AdressePostale partialUpdatedAdressePostale = new AdressePostale();
        partialUpdatedAdressePostale.setId(adressePostale.getId());

        partialUpdatedAdressePostale
            .numero(UPDATED_NUMERO)
            .bister(UPDATED_BISTER)
            .complement1(UPDATED_COMPLEMENT_1)
            .complement2(UPDATED_COMPLEMENT_2)
            .typeVoie(UPDATED_TYPE_VOIE)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .affichageInternet(UPDATED_AFFICHAGE_INTERNET)
            .affichageIntranet(UPDATED_AFFICHAGE_INTRANET);

        restAdressePostaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdressePostale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdressePostale))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
        AdressePostale testAdressePostale = adressePostaleList.get(adressePostaleList.size() - 1);
        assertThat(testAdressePostale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale.getBister()).isEqualTo(UPDATED_BISTER);
        assertThat(testAdressePostale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdressePostale.getComplement2()).isEqualTo(UPDATED_COMPLEMENT_2);
        assertThat(testAdressePostale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdressePostale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdressePostale.getAffichageIntranet()).isEqualTo(UPDATED_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void patchNonExistingAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adressePostale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdressePostale() throws Exception {
        int databaseSizeBeforeUpdate = adressePostaleRepository.findAll().size();
        adressePostale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostaleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adressePostale))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdressePostale in the database
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdressePostale() throws Exception {
        // Initialize the database
        adressePostaleRepository.saveAndFlush(adressePostale);

        int databaseSizeBeforeDelete = adressePostaleRepository.findAll().size();

        // Delete the adressePostale
        restAdressePostaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, adressePostale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdressePostale> adressePostaleList = adressePostaleRepository.findAll();
        assertThat(adressePostaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

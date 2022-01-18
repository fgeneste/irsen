package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.AdresseFiscale;
import fr.senat.irsen.repository.AdresseFiscaleRepository;
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
 * Integration tests for the {@link AdresseFiscaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresseFiscaleResourceIT {

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

    private static final String ENTITY_API_URL = "/api/adresse-fiscales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdresseFiscaleRepository adresseFiscaleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseFiscaleMockMvc;

    private AdresseFiscale adresseFiscale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdresseFiscale createEntity(EntityManager em) {
        AdresseFiscale adresseFiscale = new AdresseFiscale()
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
        return adresseFiscale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdresseFiscale createUpdatedEntity(EntityManager em) {
        AdresseFiscale adresseFiscale = new AdresseFiscale()
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
        return adresseFiscale;
    }

    @BeforeEach
    public void initTest() {
        adresseFiscale = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresseFiscale() throws Exception {
        int databaseSizeBeforeCreate = adresseFiscaleRepository.findAll().size();
        // Create the AdresseFiscale
        restAdresseFiscaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isCreated());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeCreate + 1);
        AdresseFiscale testAdresseFiscale = adresseFiscaleList.get(adresseFiscaleList.size() - 1);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdresseFiscale.getBister()).isEqualTo(DEFAULT_BISTER);
        assertThat(testAdresseFiscale.getComplement1()).isEqualTo(DEFAULT_COMPLEMENT_1);
        assertThat(testAdresseFiscale.getComplement2()).isEqualTo(DEFAULT_COMPLEMENT_2);
        assertThat(testAdresseFiscale.getTypeVoie()).isEqualTo(DEFAULT_TYPE_VOIE);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdresseFiscale.getAffichageInternet()).isEqualTo(DEFAULT_AFFICHAGE_INTERNET);
        assertThat(testAdresseFiscale.getAffichageIntranet()).isEqualTo(DEFAULT_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void createAdresseFiscaleWithExistingId() throws Exception {
        // Create the AdresseFiscale with an existing ID
        adresseFiscale.setId(1L);

        int databaseSizeBeforeCreate = adresseFiscaleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseFiscaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresseFiscales() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        // Get all the adresseFiscaleList
        restAdresseFiscaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresseFiscale.getId().intValue())))
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
    void getAdresseFiscale() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        // Get the adresseFiscale
        restAdresseFiscaleMockMvc
            .perform(get(ENTITY_API_URL_ID, adresseFiscale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresseFiscale.getId().intValue()))
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
    void getNonExistingAdresseFiscale() throws Exception {
        // Get the adresseFiscale
        restAdresseFiscaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdresseFiscale() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();

        // Update the adresseFiscale
        AdresseFiscale updatedAdresseFiscale = adresseFiscaleRepository.findById(adresseFiscale.getId()).get();
        // Disconnect from session so that the updates on updatedAdresseFiscale are not directly saved in db
        em.detach(updatedAdresseFiscale);
        updatedAdresseFiscale
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

        restAdresseFiscaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresseFiscale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdresseFiscale))
            )
            .andExpect(status().isOk());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
        AdresseFiscale testAdresseFiscale = adresseFiscaleList.get(adresseFiscaleList.size() - 1);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresseFiscale.getBister()).isEqualTo(UPDATED_BISTER);
        assertThat(testAdresseFiscale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdresseFiscale.getComplement2()).isEqualTo(UPDATED_COMPLEMENT_2);
        assertThat(testAdresseFiscale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresseFiscale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdresseFiscale.getAffichageIntranet()).isEqualTo(UPDATED_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void putNonExistingAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresseFiscale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresseFiscale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresseFiscaleWithPatch() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();

        // Update the adresseFiscale using partial update
        AdresseFiscale partialUpdatedAdresseFiscale = new AdresseFiscale();
        partialUpdatedAdresseFiscale.setId(adresseFiscale.getId());

        partialUpdatedAdresseFiscale
            .bister(UPDATED_BISTER)
            .complement1(UPDATED_COMPLEMENT_1)
            .typeVoie(UPDATED_TYPE_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .affichageInternet(UPDATED_AFFICHAGE_INTERNET);

        restAdresseFiscaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseFiscale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresseFiscale))
            )
            .andExpect(status().isOk());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
        AdresseFiscale testAdresseFiscale = adresseFiscaleList.get(adresseFiscaleList.size() - 1);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdresseFiscale.getBister()).isEqualTo(UPDATED_BISTER);
        assertThat(testAdresseFiscale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdresseFiscale.getComplement2()).isEqualTo(DEFAULT_COMPLEMENT_2);
        assertThat(testAdresseFiscale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresseFiscale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdresseFiscale.getAffichageIntranet()).isEqualTo(DEFAULT_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void fullUpdateAdresseFiscaleWithPatch() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();

        // Update the adresseFiscale using partial update
        AdresseFiscale partialUpdatedAdresseFiscale = new AdresseFiscale();
        partialUpdatedAdresseFiscale.setId(adresseFiscale.getId());

        partialUpdatedAdresseFiscale
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

        restAdresseFiscaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresseFiscale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresseFiscale))
            )
            .andExpect(status().isOk());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
        AdresseFiscale testAdresseFiscale = adresseFiscaleList.get(adresseFiscaleList.size() - 1);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresseFiscale.getBister()).isEqualTo(UPDATED_BISTER);
        assertThat(testAdresseFiscale.getComplement1()).isEqualTo(UPDATED_COMPLEMENT_1);
        assertThat(testAdresseFiscale.getComplement2()).isEqualTo(UPDATED_COMPLEMENT_2);
        assertThat(testAdresseFiscale.getTypeVoie()).isEqualTo(UPDATED_TYPE_VOIE);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresseFiscale.getAffichageInternet()).isEqualTo(UPDATED_AFFICHAGE_INTERNET);
        assertThat(testAdresseFiscale.getAffichageIntranet()).isEqualTo(UPDATED_AFFICHAGE_INTRANET);
    }

    @Test
    @Transactional
    void patchNonExistingAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresseFiscale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresseFiscale() throws Exception {
        int databaseSizeBeforeUpdate = adresseFiscaleRepository.findAll().size();
        adresseFiscale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseFiscaleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adresseFiscale))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdresseFiscale in the database
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresseFiscale() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        int databaseSizeBeforeDelete = adresseFiscaleRepository.findAll().size();

        // Delete the adresseFiscale
        restAdresseFiscaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresseFiscale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdresseFiscale> adresseFiscaleList = adresseFiscaleRepository.findAll();
        assertThat(adresseFiscaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

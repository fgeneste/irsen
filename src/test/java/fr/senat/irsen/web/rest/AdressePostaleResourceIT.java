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

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_VOIE = "AAAAAAAAAA";
    private static final String UPDATED_VOIE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MODE_MANUEL = false;
    private static final Boolean UPDATED_MODE_MANUEL = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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
            .label(DEFAULT_LABEL)
            .numero(DEFAULT_NUMERO)
            .voie(DEFAULT_VOIE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .localisation(DEFAULT_LOCALISATION)
            .modeManuel(DEFAULT_MODE_MANUEL)
            .type(DEFAULT_TYPE);
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);
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
        assertThat(testAdressePostale.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdressePostale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdressePostale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdressePostale.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testAdressePostale.getModeManuel()).isEqualTo(DEFAULT_MODE_MANUEL);
        assertThat(testAdressePostale.getType()).isEqualTo(DEFAULT_TYPE);
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
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].voie").value(hasItem(DEFAULT_VOIE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION)))
            .andExpect(jsonPath("$.[*].modeManuel").value(hasItem(DEFAULT_MODE_MANUEL.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
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
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.voie").value(DEFAULT_VOIE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION))
            .andExpect(jsonPath("$.modeManuel").value(DEFAULT_MODE_MANUEL.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

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
        assertThat(testAdressePostale.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdressePostale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdressePostale.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdressePostale.getType()).isEqualTo(UPDATED_TYPE);
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

        partialUpdatedAdressePostale.voie(UPDATED_VOIE).codePostal(UPDATED_CODE_POSTAL).ville(UPDATED_VILLE);

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
        assertThat(testAdressePostale.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdressePostale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdressePostale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdressePostale.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testAdressePostale.getModeManuel()).isEqualTo(DEFAULT_MODE_MANUEL);
        assertThat(testAdressePostale.getType()).isEqualTo(DEFAULT_TYPE);
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

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
        assertThat(testAdressePostale.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdressePostale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdressePostale.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdressePostale.getType()).isEqualTo(UPDATED_TYPE);
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

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
            .label(DEFAULT_LABEL)
            .numero(DEFAULT_NUMERO)
            .voie(DEFAULT_VOIE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .localisation(DEFAULT_LOCALISATION)
            .modeManuel(DEFAULT_MODE_MANUEL)
            .type(DEFAULT_TYPE);
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);
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
        assertThat(testAdresseFiscale.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdresseFiscale.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testAdresseFiscale.getModeManuel()).isEqualTo(DEFAULT_MODE_MANUEL);
        assertThat(testAdresseFiscale.getType()).isEqualTo(DEFAULT_TYPE);
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
    void getAdresseFiscale() throws Exception {
        // Initialize the database
        adresseFiscaleRepository.saveAndFlush(adresseFiscale);

        // Get the adresseFiscale
        restAdresseFiscaleMockMvc
            .perform(get(ENTITY_API_URL_ID, adresseFiscale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresseFiscale.getId().intValue()))
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

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
        assertThat(testAdresseFiscale.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresseFiscale.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdresseFiscale.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdresseFiscale.getType()).isEqualTo(UPDATED_TYPE);
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
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .ville(UPDATED_VILLE)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

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
        assertThat(testAdresseFiscale.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdresseFiscale.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdresseFiscale.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdresseFiscale.getType()).isEqualTo(UPDATED_TYPE);
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
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

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
        assertThat(testAdresseFiscale.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdresseFiscale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdresseFiscale.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdresseFiscale.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdresseFiscale.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresseFiscale.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresseFiscale.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdresseFiscale.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdresseFiscale.getType()).isEqualTo(UPDATED_TYPE);
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

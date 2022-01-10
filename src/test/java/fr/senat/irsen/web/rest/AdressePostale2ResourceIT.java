package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.AdressePostale2;
import fr.senat.irsen.repository.AdressePostale2Repository;
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
 * Integration tests for the {@link AdressePostale2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdressePostale2ResourceIT {

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

    private static final String ENTITY_API_URL = "/api/adresse-postale-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdressePostale2Repository adressePostale2Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdressePostale2MockMvc;

    private AdressePostale2 adressePostale2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdressePostale2 createEntity(EntityManager em) {
        AdressePostale2 adressePostale2 = new AdressePostale2()
            .label(DEFAULT_LABEL)
            .numero(DEFAULT_NUMERO)
            .voie(DEFAULT_VOIE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .localisation(DEFAULT_LOCALISATION)
            .modeManuel(DEFAULT_MODE_MANUEL)
            .type(DEFAULT_TYPE);
        return adressePostale2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdressePostale2 createUpdatedEntity(EntityManager em) {
        AdressePostale2 adressePostale2 = new AdressePostale2()
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);
        return adressePostale2;
    }

    @BeforeEach
    public void initTest() {
        adressePostale2 = createEntity(em);
    }

    @Test
    @Transactional
    void createAdressePostale2() throws Exception {
        int databaseSizeBeforeCreate = adressePostale2Repository.findAll().size();
        // Create the AdressePostale2
        restAdressePostale2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isCreated());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeCreate + 1);
        AdressePostale2 testAdressePostale2 = adressePostale2List.get(adressePostale2List.size() - 1);
        assertThat(testAdressePostale2.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdressePostale2.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAdressePostale2.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdressePostale2.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAdressePostale2.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdressePostale2.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdressePostale2.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testAdressePostale2.getModeManuel()).isEqualTo(DEFAULT_MODE_MANUEL);
        assertThat(testAdressePostale2.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createAdressePostale2WithExistingId() throws Exception {
        // Create the AdressePostale2 with an existing ID
        adressePostale2.setId(1L);

        int databaseSizeBeforeCreate = adressePostale2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdressePostale2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdressePostale2s() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        // Get all the adressePostale2List
        restAdressePostale2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adressePostale2.getId().intValue())))
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
    void getAdressePostale2() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        // Get the adressePostale2
        restAdressePostale2MockMvc
            .perform(get(ENTITY_API_URL_ID, adressePostale2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adressePostale2.getId().intValue()))
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
    void getNonExistingAdressePostale2() throws Exception {
        // Get the adressePostale2
        restAdressePostale2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdressePostale2() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();

        // Update the adressePostale2
        AdressePostale2 updatedAdressePostale2 = adressePostale2Repository.findById(adressePostale2.getId()).get();
        // Disconnect from session so that the updates on updatedAdressePostale2 are not directly saved in db
        em.detach(updatedAdressePostale2);
        updatedAdressePostale2
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

        restAdressePostale2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdressePostale2.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdressePostale2))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
        AdressePostale2 testAdressePostale2 = adressePostale2List.get(adressePostale2List.size() - 1);
        assertThat(testAdressePostale2.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdressePostale2.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale2.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale2.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale2.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale2.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale2.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdressePostale2.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdressePostale2.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, adressePostale2.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdressePostale2WithPatch() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();

        // Update the adressePostale2 using partial update
        AdressePostale2 partialUpdatedAdressePostale2 = new AdressePostale2();
        partialUpdatedAdressePostale2.setId(adressePostale2.getId());

        partialUpdatedAdressePostale2
            .numero(UPDATED_NUMERO)
            .codePostal(UPDATED_CODE_POSTAL)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION);

        restAdressePostale2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdressePostale2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdressePostale2))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
        AdressePostale2 testAdressePostale2 = adressePostale2List.get(adressePostale2List.size() - 1);
        assertThat(testAdressePostale2.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAdressePostale2.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale2.getVoie()).isEqualTo(DEFAULT_VOIE);
        assertThat(testAdressePostale2.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale2.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdressePostale2.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale2.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdressePostale2.getModeManuel()).isEqualTo(DEFAULT_MODE_MANUEL);
        assertThat(testAdressePostale2.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAdressePostale2WithPatch() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();

        // Update the adressePostale2 using partial update
        AdressePostale2 partialUpdatedAdressePostale2 = new AdressePostale2();
        partialUpdatedAdressePostale2.setId(adressePostale2.getId());

        partialUpdatedAdressePostale2
            .label(UPDATED_LABEL)
            .numero(UPDATED_NUMERO)
            .voie(UPDATED_VOIE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .localisation(UPDATED_LOCALISATION)
            .modeManuel(UPDATED_MODE_MANUEL)
            .type(UPDATED_TYPE);

        restAdressePostale2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdressePostale2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdressePostale2))
            )
            .andExpect(status().isOk());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
        AdressePostale2 testAdressePostale2 = adressePostale2List.get(adressePostale2List.size() - 1);
        assertThat(testAdressePostale2.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAdressePostale2.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAdressePostale2.getVoie()).isEqualTo(UPDATED_VOIE);
        assertThat(testAdressePostale2.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAdressePostale2.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdressePostale2.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdressePostale2.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testAdressePostale2.getModeManuel()).isEqualTo(UPDATED_MODE_MANUEL);
        assertThat(testAdressePostale2.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adressePostale2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdressePostale2() throws Exception {
        int databaseSizeBeforeUpdate = adressePostale2Repository.findAll().size();
        adressePostale2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressePostale2MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressePostale2))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdressePostale2 in the database
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdressePostale2() throws Exception {
        // Initialize the database
        adressePostale2Repository.saveAndFlush(adressePostale2);

        int databaseSizeBeforeDelete = adressePostale2Repository.findAll().size();

        // Delete the adressePostale2
        restAdressePostale2MockMvc
            .perform(delete(ENTITY_API_URL_ID, adressePostale2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdressePostale2> adressePostale2List = adressePostale2Repository.findAll();
        assertThat(adressePostale2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}

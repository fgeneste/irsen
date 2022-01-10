package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.PaysNaissance;
import fr.senat.irsen.repository.PaysNaissanceRepository;
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
 * Integration tests for the {@link PaysNaissanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaysNaissanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL = false;
    private static final Boolean UPDATED_AVEC_CONSEIL_DEPARTEMENTAL = true;

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pays-naissances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaysNaissanceRepository paysNaissanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaysNaissanceMockMvc;

    private PaysNaissance paysNaissance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaysNaissance createEntity(EntityManager em) {
        PaysNaissance paysNaissance = new PaysNaissance()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .avecConseilDepartemental(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(DEFAULT_ARTICLE);
        return paysNaissance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaysNaissance createUpdatedEntity(EntityManager em) {
        PaysNaissance paysNaissance = new PaysNaissance()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE);
        return paysNaissance;
    }

    @BeforeEach
    public void initTest() {
        paysNaissance = createEntity(em);
    }

    @Test
    @Transactional
    void createPaysNaissance() throws Exception {
        int databaseSizeBeforeCreate = paysNaissanceRepository.findAll().size();
        // Create the PaysNaissance
        restPaysNaissanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paysNaissance)))
            .andExpect(status().isCreated());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeCreate + 1);
        PaysNaissance testPaysNaissance = paysNaissanceList.get(paysNaissanceList.size() - 1);
        assertThat(testPaysNaissance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPaysNaissance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPaysNaissance.getAvecConseilDepartemental()).isEqualTo(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testPaysNaissance.getArticle()).isEqualTo(DEFAULT_ARTICLE);
    }

    @Test
    @Transactional
    void createPaysNaissanceWithExistingId() throws Exception {
        // Create the PaysNaissance with an existing ID
        paysNaissance.setId(1L);

        int databaseSizeBeforeCreate = paysNaissanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaysNaissanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paysNaissance)))
            .andExpect(status().isBadRequest());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaysNaissances() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        // Get all the paysNaissanceList
        restPaysNaissanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paysNaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].avecConseilDepartemental").value(hasItem(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL.booleanValue())))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)));
    }

    @Test
    @Transactional
    void getPaysNaissance() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        // Get the paysNaissance
        restPaysNaissanceMockMvc
            .perform(get(ENTITY_API_URL_ID, paysNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paysNaissance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.avecConseilDepartemental").value(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL.booleanValue()))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE));
    }

    @Test
    @Transactional
    void getNonExistingPaysNaissance() throws Exception {
        // Get the paysNaissance
        restPaysNaissanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaysNaissance() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();

        // Update the paysNaissance
        PaysNaissance updatedPaysNaissance = paysNaissanceRepository.findById(paysNaissance.getId()).get();
        // Disconnect from session so that the updates on updatedPaysNaissance are not directly saved in db
        em.detach(updatedPaysNaissance);
        updatedPaysNaissance
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE);

        restPaysNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaysNaissance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaysNaissance))
            )
            .andExpect(status().isOk());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
        PaysNaissance testPaysNaissance = paysNaissanceList.get(paysNaissanceList.size() - 1);
        assertThat(testPaysNaissance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaysNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPaysNaissance.getAvecConseilDepartemental()).isEqualTo(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testPaysNaissance.getArticle()).isEqualTo(UPDATED_ARTICLE);
    }

    @Test
    @Transactional
    void putNonExistingPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paysNaissance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paysNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paysNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paysNaissance)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaysNaissanceWithPatch() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();

        // Update the paysNaissance using partial update
        PaysNaissance partialUpdatedPaysNaissance = new PaysNaissance();
        partialUpdatedPaysNaissance.setId(paysNaissance.getId());

        partialUpdatedPaysNaissance.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restPaysNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaysNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaysNaissance))
            )
            .andExpect(status().isOk());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
        PaysNaissance testPaysNaissance = paysNaissanceList.get(paysNaissanceList.size() - 1);
        assertThat(testPaysNaissance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaysNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPaysNaissance.getAvecConseilDepartemental()).isEqualTo(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testPaysNaissance.getArticle()).isEqualTo(DEFAULT_ARTICLE);
    }

    @Test
    @Transactional
    void fullUpdatePaysNaissanceWithPatch() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();

        // Update the paysNaissance using partial update
        PaysNaissance partialUpdatedPaysNaissance = new PaysNaissance();
        partialUpdatedPaysNaissance.setId(paysNaissance.getId());

        partialUpdatedPaysNaissance
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE);

        restPaysNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaysNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaysNaissance))
            )
            .andExpect(status().isOk());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
        PaysNaissance testPaysNaissance = paysNaissanceList.get(paysNaissanceList.size() - 1);
        assertThat(testPaysNaissance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaysNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPaysNaissance.getAvecConseilDepartemental()).isEqualTo(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testPaysNaissance.getArticle()).isEqualTo(UPDATED_ARTICLE);
    }

    @Test
    @Transactional
    void patchNonExistingPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paysNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paysNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paysNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaysNaissance() throws Exception {
        int databaseSizeBeforeUpdate = paysNaissanceRepository.findAll().size();
        paysNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaysNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paysNaissance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaysNaissance in the database
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaysNaissance() throws Exception {
        // Initialize the database
        paysNaissanceRepository.saveAndFlush(paysNaissance);

        int databaseSizeBeforeDelete = paysNaissanceRepository.findAll().size();

        // Delete the paysNaissance
        restPaysNaissanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, paysNaissance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaysNaissance> paysNaissanceList = paysNaissanceRepository.findAll();
        assertThat(paysNaissanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

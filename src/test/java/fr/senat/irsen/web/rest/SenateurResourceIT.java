package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.Senateur;
import fr.senat.irsen.repository.SenateurRepository;
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
 * Integration tests for the {@link SenateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SenateurResourceIT {

    private static final String ENTITY_API_URL = "/api/senateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SenateurRepository senateurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSenateurMockMvc;

    private Senateur senateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Senateur createEntity(EntityManager em) {
        Senateur senateur = new Senateur();
        return senateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Senateur createUpdatedEntity(EntityManager em) {
        Senateur senateur = new Senateur();
        return senateur;
    }

    @BeforeEach
    public void initTest() {
        senateur = createEntity(em);
    }

    @Test
    @Transactional
    void createSenateur() throws Exception {
        int databaseSizeBeforeCreate = senateurRepository.findAll().size();
        // Create the Senateur
        restSenateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(senateur)))
            .andExpect(status().isCreated());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeCreate + 1);
        Senateur testSenateur = senateurList.get(senateurList.size() - 1);
    }

    @Test
    @Transactional
    void createSenateurWithExistingId() throws Exception {
        // Create the Senateur with an existing ID
        senateur.setId(1L);

        int databaseSizeBeforeCreate = senateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSenateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(senateur)))
            .andExpect(status().isBadRequest());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSenateurs() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        // Get all the senateurList
        restSenateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(senateur.getId().intValue())));
    }

    @Test
    @Transactional
    void getSenateur() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        // Get the senateur
        restSenateurMockMvc
            .perform(get(ENTITY_API_URL_ID, senateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(senateur.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSenateur() throws Exception {
        // Get the senateur
        restSenateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSenateur() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();

        // Update the senateur
        Senateur updatedSenateur = senateurRepository.findById(senateur.getId()).get();
        // Disconnect from session so that the updates on updatedSenateur are not directly saved in db
        em.detach(updatedSenateur);

        restSenateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSenateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSenateur))
            )
            .andExpect(status().isOk());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
        Senateur testSenateur = senateurList.get(senateurList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, senateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(senateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(senateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(senateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSenateurWithPatch() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();

        // Update the senateur using partial update
        Senateur partialUpdatedSenateur = new Senateur();
        partialUpdatedSenateur.setId(senateur.getId());

        restSenateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSenateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSenateur))
            )
            .andExpect(status().isOk());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
        Senateur testSenateur = senateurList.get(senateurList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateSenateurWithPatch() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();

        // Update the senateur using partial update
        Senateur partialUpdatedSenateur = new Senateur();
        partialUpdatedSenateur.setId(senateur.getId());

        restSenateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSenateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSenateur))
            )
            .andExpect(status().isOk());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
        Senateur testSenateur = senateurList.get(senateurList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, senateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(senateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(senateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSenateur() throws Exception {
        int databaseSizeBeforeUpdate = senateurRepository.findAll().size();
        senateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSenateurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(senateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Senateur in the database
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSenateur() throws Exception {
        // Initialize the database
        senateurRepository.saveAndFlush(senateur);

        int databaseSizeBeforeDelete = senateurRepository.findAll().size();

        // Delete the senateur
        restSenateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, senateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Senateur> senateurList = senateurRepository.findAll();
        assertThat(senateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

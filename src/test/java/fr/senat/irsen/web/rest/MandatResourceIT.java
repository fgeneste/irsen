package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.Mandat;
import fr.senat.irsen.repository.MandatRepository;
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
 * Integration tests for the {@link MandatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MandatResourceIT {

    private static final String ENTITY_API_URL = "/api/mandats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MandatRepository mandatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMandatMockMvc;

    private Mandat mandat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mandat createEntity(EntityManager em) {
        Mandat mandat = new Mandat();
        return mandat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mandat createUpdatedEntity(EntityManager em) {
        Mandat mandat = new Mandat();
        return mandat;
    }

    @BeforeEach
    public void initTest() {
        mandat = createEntity(em);
    }

    @Test
    @Transactional
    void createMandat() throws Exception {
        int databaseSizeBeforeCreate = mandatRepository.findAll().size();
        // Create the Mandat
        restMandatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandat)))
            .andExpect(status().isCreated());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeCreate + 1);
        Mandat testMandat = mandatList.get(mandatList.size() - 1);
    }

    @Test
    @Transactional
    void createMandatWithExistingId() throws Exception {
        // Create the Mandat with an existing ID
        mandat.setId(1L);

        int databaseSizeBeforeCreate = mandatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandat)))
            .andExpect(status().isBadRequest());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMandats() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        // Get all the mandatList
        restMandatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandat.getId().intValue())));
    }

    @Test
    @Transactional
    void getMandat() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        // Get the mandat
        restMandatMockMvc
            .perform(get(ENTITY_API_URL_ID, mandat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mandat.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMandat() throws Exception {
        // Get the mandat
        restMandatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMandat() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();

        // Update the mandat
        Mandat updatedMandat = mandatRepository.findById(mandat.getId()).get();
        // Disconnect from session so that the updates on updatedMandat are not directly saved in db
        em.detach(updatedMandat);

        restMandatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMandat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMandat))
            )
            .andExpect(status().isOk());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
        Mandat testMandat = mandatList.get(mandatList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mandat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMandatWithPatch() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();

        // Update the mandat using partial update
        Mandat partialUpdatedMandat = new Mandat();
        partialUpdatedMandat.setId(mandat.getId());

        restMandatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandat))
            )
            .andExpect(status().isOk());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
        Mandat testMandat = mandatList.get(mandatList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateMandatWithPatch() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();

        // Update the mandat using partial update
        Mandat partialUpdatedMandat = new Mandat();
        partialUpdatedMandat.setId(mandat.getId());

        restMandatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandat))
            )
            .andExpect(status().isOk());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
        Mandat testMandat = mandatList.get(mandatList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mandat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMandat() throws Exception {
        int databaseSizeBeforeUpdate = mandatRepository.findAll().size();
        mandat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mandat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mandat in the database
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMandat() throws Exception {
        // Initialize the database
        mandatRepository.saveAndFlush(mandat);

        int databaseSizeBeforeDelete = mandatRepository.findAll().size();

        // Delete the mandat
        restMandatMockMvc
            .perform(delete(ENTITY_API_URL_ID, mandat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mandat> mandatList = mandatRepository.findAll();
        assertThat(mandatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

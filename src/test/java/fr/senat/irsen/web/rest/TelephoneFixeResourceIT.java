package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.TelephoneFixe;
import fr.senat.irsen.repository.TelephoneFixeRepository;
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
 * Integration tests for the {@link TelephoneFixeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelephoneFixeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telephone-fixes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelephoneFixeRepository telephoneFixeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelephoneFixeMockMvc;

    private TelephoneFixe telephoneFixe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephoneFixe createEntity(EntityManager em) {
        TelephoneFixe telephoneFixe = new TelephoneFixe().type(DEFAULT_TYPE).numero(DEFAULT_NUMERO);
        return telephoneFixe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephoneFixe createUpdatedEntity(EntityManager em) {
        TelephoneFixe telephoneFixe = new TelephoneFixe().type(UPDATED_TYPE).numero(UPDATED_NUMERO);
        return telephoneFixe;
    }

    @BeforeEach
    public void initTest() {
        telephoneFixe = createEntity(em);
    }

    @Test
    @Transactional
    void createTelephoneFixe() throws Exception {
        int databaseSizeBeforeCreate = telephoneFixeRepository.findAll().size();
        // Create the TelephoneFixe
        restTelephoneFixeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephoneFixe)))
            .andExpect(status().isCreated());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeCreate + 1);
        TelephoneFixe testTelephoneFixe = telephoneFixeList.get(telephoneFixeList.size() - 1);
        assertThat(testTelephoneFixe.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephoneFixe.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void createTelephoneFixeWithExistingId() throws Exception {
        // Create the TelephoneFixe with an existing ID
        telephoneFixe.setId(1L);

        int databaseSizeBeforeCreate = telephoneFixeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephoneFixeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephoneFixe)))
            .andExpect(status().isBadRequest());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelephoneFixes() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        // Get all the telephoneFixeList
        restTelephoneFixeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephoneFixe.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    void getTelephoneFixe() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        // Get the telephoneFixe
        restTelephoneFixeMockMvc
            .perform(get(ENTITY_API_URL_ID, telephoneFixe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telephoneFixe.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    void getNonExistingTelephoneFixe() throws Exception {
        // Get the telephoneFixe
        restTelephoneFixeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTelephoneFixe() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();

        // Update the telephoneFixe
        TelephoneFixe updatedTelephoneFixe = telephoneFixeRepository.findById(telephoneFixe.getId()).get();
        // Disconnect from session so that the updates on updatedTelephoneFixe are not directly saved in db
        em.detach(updatedTelephoneFixe);
        updatedTelephoneFixe.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephoneFixeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelephoneFixe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTelephoneFixe))
            )
            .andExpect(status().isOk());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
        TelephoneFixe testTelephoneFixe = telephoneFixeList.get(telephoneFixeList.size() - 1);
        assertThat(testTelephoneFixe.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephoneFixe.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void putNonExistingTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telephoneFixe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephoneFixe))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephoneFixe))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephoneFixe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelephoneFixeWithPatch() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();

        // Update the telephoneFixe using partial update
        TelephoneFixe partialUpdatedTelephoneFixe = new TelephoneFixe();
        partialUpdatedTelephoneFixe.setId(telephoneFixe.getId());

        partialUpdatedTelephoneFixe.type(UPDATED_TYPE);

        restTelephoneFixeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephoneFixe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephoneFixe))
            )
            .andExpect(status().isOk());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
        TelephoneFixe testTelephoneFixe = telephoneFixeList.get(telephoneFixeList.size() - 1);
        assertThat(testTelephoneFixe.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephoneFixe.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void fullUpdateTelephoneFixeWithPatch() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();

        // Update the telephoneFixe using partial update
        TelephoneFixe partialUpdatedTelephoneFixe = new TelephoneFixe();
        partialUpdatedTelephoneFixe.setId(telephoneFixe.getId());

        partialUpdatedTelephoneFixe.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephoneFixeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephoneFixe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephoneFixe))
            )
            .andExpect(status().isOk());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
        TelephoneFixe testTelephoneFixe = telephoneFixeList.get(telephoneFixeList.size() - 1);
        assertThat(testTelephoneFixe.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephoneFixe.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void patchNonExistingTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telephoneFixe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephoneFixe))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephoneFixe))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelephoneFixe() throws Exception {
        int databaseSizeBeforeUpdate = telephoneFixeRepository.findAll().size();
        telephoneFixe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneFixeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(telephoneFixe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephoneFixe in the database
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelephoneFixe() throws Exception {
        // Initialize the database
        telephoneFixeRepository.saveAndFlush(telephoneFixe);

        int databaseSizeBeforeDelete = telephoneFixeRepository.findAll().size();

        // Delete the telephoneFixe
        restTelephoneFixeMockMvc
            .perform(delete(ENTITY_API_URL_ID, telephoneFixe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelephoneFixe> telephoneFixeList = telephoneFixeRepository.findAll();
        assertThat(telephoneFixeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

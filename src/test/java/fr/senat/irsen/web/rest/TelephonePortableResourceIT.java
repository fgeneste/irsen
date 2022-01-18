package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.TelephonePortable;
import fr.senat.irsen.repository.TelephonePortableRepository;
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
 * Integration tests for the {@link TelephonePortableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelephonePortableResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telephone-portables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelephonePortableRepository telephonePortableRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelephonePortableMockMvc;

    private TelephonePortable telephonePortable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephonePortable createEntity(EntityManager em) {
        TelephonePortable telephonePortable = new TelephonePortable().type(DEFAULT_TYPE).numero(DEFAULT_NUMERO);
        return telephonePortable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephonePortable createUpdatedEntity(EntityManager em) {
        TelephonePortable telephonePortable = new TelephonePortable().type(UPDATED_TYPE).numero(UPDATED_NUMERO);
        return telephonePortable;
    }

    @BeforeEach
    public void initTest() {
        telephonePortable = createEntity(em);
    }

    @Test
    @Transactional
    void createTelephonePortable() throws Exception {
        int databaseSizeBeforeCreate = telephonePortableRepository.findAll().size();
        // Create the TelephonePortable
        restTelephonePortableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isCreated());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeCreate + 1);
        TelephonePortable testTelephonePortable = telephonePortableList.get(telephonePortableList.size() - 1);
        assertThat(testTelephonePortable.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephonePortable.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void createTelephonePortableWithExistingId() throws Exception {
        // Create the TelephonePortable with an existing ID
        telephonePortable.setId(1L);

        int databaseSizeBeforeCreate = telephonePortableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephonePortableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelephonePortables() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        // Get all the telephonePortableList
        restTelephonePortableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephonePortable.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    void getTelephonePortable() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        // Get the telephonePortable
        restTelephonePortableMockMvc
            .perform(get(ENTITY_API_URL_ID, telephonePortable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telephonePortable.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    void getNonExistingTelephonePortable() throws Exception {
        // Get the telephonePortable
        restTelephonePortableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTelephonePortable() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();

        // Update the telephonePortable
        TelephonePortable updatedTelephonePortable = telephonePortableRepository.findById(telephonePortable.getId()).get();
        // Disconnect from session so that the updates on updatedTelephonePortable are not directly saved in db
        em.detach(updatedTelephonePortable);
        updatedTelephonePortable.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephonePortableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelephonePortable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTelephonePortable))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable testTelephonePortable = telephonePortableList.get(telephonePortableList.size() - 1);
        assertThat(testTelephonePortable.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephonePortable.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void putNonExistingTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telephonePortable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelephonePortableWithPatch() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();

        // Update the telephonePortable using partial update
        TelephonePortable partialUpdatedTelephonePortable = new TelephonePortable();
        partialUpdatedTelephonePortable.setId(telephonePortable.getId());

        partialUpdatedTelephonePortable.type(UPDATED_TYPE);

        restTelephonePortableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephonePortable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephonePortable))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable testTelephonePortable = telephonePortableList.get(telephonePortableList.size() - 1);
        assertThat(testTelephonePortable.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephonePortable.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void fullUpdateTelephonePortableWithPatch() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();

        // Update the telephonePortable using partial update
        TelephonePortable partialUpdatedTelephonePortable = new TelephonePortable();
        partialUpdatedTelephonePortable.setId(telephonePortable.getId());

        partialUpdatedTelephonePortable.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephonePortableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephonePortable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephonePortable))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable testTelephonePortable = telephonePortableList.get(telephonePortableList.size() - 1);
        assertThat(testTelephonePortable.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephonePortable.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void patchNonExistingTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telephonePortable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelephonePortable() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortableRepository.findAll().size();
        telephonePortable.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephonePortable in the database
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelephonePortable() throws Exception {
        // Initialize the database
        telephonePortableRepository.saveAndFlush(telephonePortable);

        int databaseSizeBeforeDelete = telephonePortableRepository.findAll().size();

        // Delete the telephonePortable
        restTelephonePortableMockMvc
            .perform(delete(ENTITY_API_URL_ID, telephonePortable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelephonePortable> telephonePortableList = telephonePortableRepository.findAll();
        assertThat(telephonePortableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

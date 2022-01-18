package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.TelephonePortable2;
import fr.senat.irsen.repository.TelephonePortable2Repository;
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
 * Integration tests for the {@link TelephonePortable2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelephonePortable2ResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telephone-portable-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelephonePortable2Repository telephonePortable2Repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelephonePortable2MockMvc;

    private TelephonePortable2 telephonePortable2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephonePortable2 createEntity(EntityManager em) {
        TelephonePortable2 telephonePortable2 = new TelephonePortable2().type(DEFAULT_TYPE).numero(DEFAULT_NUMERO);
        return telephonePortable2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephonePortable2 createUpdatedEntity(EntityManager em) {
        TelephonePortable2 telephonePortable2 = new TelephonePortable2().type(UPDATED_TYPE).numero(UPDATED_NUMERO);
        return telephonePortable2;
    }

    @BeforeEach
    public void initTest() {
        telephonePortable2 = createEntity(em);
    }

    @Test
    @Transactional
    void createTelephonePortable2() throws Exception {
        int databaseSizeBeforeCreate = telephonePortable2Repository.findAll().size();
        // Create the TelephonePortable2
        restTelephonePortable2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isCreated());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeCreate + 1);
        TelephonePortable2 testTelephonePortable2 = telephonePortable2List.get(telephonePortable2List.size() - 1);
        assertThat(testTelephonePortable2.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephonePortable2.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void createTelephonePortable2WithExistingId() throws Exception {
        // Create the TelephonePortable2 with an existing ID
        telephonePortable2.setId(1L);

        int databaseSizeBeforeCreate = telephonePortable2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephonePortable2MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelephonePortable2s() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        // Get all the telephonePortable2List
        restTelephonePortable2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephonePortable2.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    void getTelephonePortable2() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        // Get the telephonePortable2
        restTelephonePortable2MockMvc
            .perform(get(ENTITY_API_URL_ID, telephonePortable2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telephonePortable2.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    void getNonExistingTelephonePortable2() throws Exception {
        // Get the telephonePortable2
        restTelephonePortable2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTelephonePortable2() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();

        // Update the telephonePortable2
        TelephonePortable2 updatedTelephonePortable2 = telephonePortable2Repository.findById(telephonePortable2.getId()).get();
        // Disconnect from session so that the updates on updatedTelephonePortable2 are not directly saved in db
        em.detach(updatedTelephonePortable2);
        updatedTelephonePortable2.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephonePortable2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTelephonePortable2.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTelephonePortable2))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable2 testTelephonePortable2 = telephonePortable2List.get(telephonePortable2List.size() - 1);
        assertThat(testTelephonePortable2.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephonePortable2.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void putNonExistingTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, telephonePortable2.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelephonePortable2WithPatch() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();

        // Update the telephonePortable2 using partial update
        TelephonePortable2 partialUpdatedTelephonePortable2 = new TelephonePortable2();
        partialUpdatedTelephonePortable2.setId(telephonePortable2.getId());

        partialUpdatedTelephonePortable2.numero(UPDATED_NUMERO);

        restTelephonePortable2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephonePortable2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephonePortable2))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable2 testTelephonePortable2 = telephonePortable2List.get(telephonePortable2List.size() - 1);
        assertThat(testTelephonePortable2.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephonePortable2.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void fullUpdateTelephonePortable2WithPatch() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();

        // Update the telephonePortable2 using partial update
        TelephonePortable2 partialUpdatedTelephonePortable2 = new TelephonePortable2();
        partialUpdatedTelephonePortable2.setId(telephonePortable2.getId());

        partialUpdatedTelephonePortable2.type(UPDATED_TYPE).numero(UPDATED_NUMERO);

        restTelephonePortable2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephonePortable2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelephonePortable2))
            )
            .andExpect(status().isOk());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
        TelephonePortable2 testTelephonePortable2 = telephonePortable2List.get(telephonePortable2List.size() - 1);
        assertThat(testTelephonePortable2.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephonePortable2.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void patchNonExistingTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telephonePortable2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelephonePortable2() throws Exception {
        int databaseSizeBeforeUpdate = telephonePortable2Repository.findAll().size();
        telephonePortable2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephonePortable2MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telephonePortable2))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelephonePortable2 in the database
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelephonePortable2() throws Exception {
        // Initialize the database
        telephonePortable2Repository.saveAndFlush(telephonePortable2);

        int databaseSizeBeforeDelete = telephonePortable2Repository.findAll().size();

        // Delete the telephonePortable2
        restTelephonePortable2MockMvc
            .perform(delete(ENTITY_API_URL_ID, telephonePortable2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelephonePortable2> telephonePortable2List = telephonePortable2Repository.findAll();
        assertThat(telephonePortable2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.Adresses;
import fr.senat.irsen.repository.AdressesRepository;
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
 * Integration tests for the {@link AdressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdressesResourceIT {

    private static final String ENTITY_API_URL = "/api/adresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdressesRepository adressesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdressesMockMvc;

    private Adresses adresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresses createEntity(EntityManager em) {
        Adresses adresses = new Adresses();
        return adresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresses createUpdatedEntity(EntityManager em) {
        Adresses adresses = new Adresses();
        return adresses;
    }

    @BeforeEach
    public void initTest() {
        adresses = createEntity(em);
    }

    @Test
    @Transactional
    void createAdresses() throws Exception {
        int databaseSizeBeforeCreate = adressesRepository.findAll().size();
        // Create the Adresses
        restAdressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresses)))
            .andExpect(status().isCreated());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeCreate + 1);
        Adresses testAdresses = adressesList.get(adressesList.size() - 1);
    }

    @Test
    @Transactional
    void createAdressesWithExistingId() throws Exception {
        // Create the Adresses with an existing ID
        adresses.setId(1L);

        int databaseSizeBeforeCreate = adressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresses)))
            .andExpect(status().isBadRequest());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresses() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        // Get all the adressesList
        restAdressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresses.getId().intValue())));
    }

    @Test
    @Transactional
    void getAdresses() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        // Get the adresses
        restAdressesMockMvc
            .perform(get(ENTITY_API_URL_ID, adresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresses.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdresses() throws Exception {
        // Get the adresses
        restAdressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdresses() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();

        // Update the adresses
        Adresses updatedAdresses = adressesRepository.findById(adresses.getId()).get();
        // Disconnect from session so that the updates on updatedAdresses are not directly saved in db
        em.detach(updatedAdresses);

        restAdressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdresses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdresses))
            )
            .andExpect(status().isOk());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
        Adresses testAdresses = adressesList.get(adressesList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adresses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdressesWithPatch() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();

        // Update the adresses using partial update
        Adresses partialUpdatedAdresses = new Adresses();
        partialUpdatedAdresses.setId(adresses.getId());

        restAdressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresses))
            )
            .andExpect(status().isOk());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
        Adresses testAdresses = adressesList.get(adressesList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAdressesWithPatch() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();

        // Update the adresses using partial update
        Adresses partialUpdatedAdresses = new Adresses();
        partialUpdatedAdresses.setId(adresses.getId());

        restAdressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdresses))
            )
            .andExpect(status().isOk());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
        Adresses testAdresses = adressesList.get(adressesList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adresses))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresses() throws Exception {
        int databaseSizeBeforeUpdate = adressesRepository.findAll().size();
        adresses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adresses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresses in the database
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresses() throws Exception {
        // Initialize the database
        adressesRepository.saveAndFlush(adresses);

        int databaseSizeBeforeDelete = adressesRepository.findAll().size();

        // Delete the adresses
        restAdressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adresses> adressesList = adressesRepository.findAll();
        assertThat(adressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

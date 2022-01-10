package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.FonctionEnCours;
import fr.senat.irsen.repository.FonctionEnCoursRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FonctionEnCoursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FonctionEnCoursResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/fonction-en-cours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FonctionEnCoursRepository fonctionEnCoursRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFonctionEnCoursMockMvc;

    private FonctionEnCours fonctionEnCours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FonctionEnCours createEntity(EntityManager em) {
        FonctionEnCours fonctionEnCours = new FonctionEnCours()
            .libelle(DEFAULT_LIBELLE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return fonctionEnCours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FonctionEnCours createUpdatedEntity(EntityManager em) {
        FonctionEnCours fonctionEnCours = new FonctionEnCours()
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        return fonctionEnCours;
    }

    @BeforeEach
    public void initTest() {
        fonctionEnCours = createEntity(em);
    }

    @Test
    @Transactional
    void createFonctionEnCours() throws Exception {
        int databaseSizeBeforeCreate = fonctionEnCoursRepository.findAll().size();
        // Create the FonctionEnCours
        restFonctionEnCoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isCreated());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeCreate + 1);
        FonctionEnCours testFonctionEnCours = fonctionEnCoursList.get(fonctionEnCoursList.size() - 1);
        assertThat(testFonctionEnCours.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFonctionEnCours.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testFonctionEnCours.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createFonctionEnCoursWithExistingId() throws Exception {
        // Create the FonctionEnCours with an existing ID
        fonctionEnCours.setId(1L);

        int databaseSizeBeforeCreate = fonctionEnCoursRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionEnCoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFonctionEnCours() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        // Get all the fonctionEnCoursList
        restFonctionEnCoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonctionEnCours.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    void getFonctionEnCours() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        // Get the fonctionEnCours
        restFonctionEnCoursMockMvc
            .perform(get(ENTITY_API_URL_ID, fonctionEnCours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fonctionEnCours.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFonctionEnCours() throws Exception {
        // Get the fonctionEnCours
        restFonctionEnCoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFonctionEnCours() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();

        // Update the fonctionEnCours
        FonctionEnCours updatedFonctionEnCours = fonctionEnCoursRepository.findById(fonctionEnCours.getId()).get();
        // Disconnect from session so that the updates on updatedFonctionEnCours are not directly saved in db
        em.detach(updatedFonctionEnCours);
        updatedFonctionEnCours.libelle(UPDATED_LIBELLE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restFonctionEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFonctionEnCours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFonctionEnCours))
            )
            .andExpect(status().isOk());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
        FonctionEnCours testFonctionEnCours = fonctionEnCoursList.get(fonctionEnCoursList.size() - 1);
        assertThat(testFonctionEnCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionEnCours.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFonctionEnCours.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fonctionEnCours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFonctionEnCoursWithPatch() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();

        // Update the fonctionEnCours using partial update
        FonctionEnCours partialUpdatedFonctionEnCours = new FonctionEnCours();
        partialUpdatedFonctionEnCours.setId(fonctionEnCours.getId());

        partialUpdatedFonctionEnCours.libelle(UPDATED_LIBELLE);

        restFonctionEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonctionEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonctionEnCours))
            )
            .andExpect(status().isOk());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
        FonctionEnCours testFonctionEnCours = fonctionEnCoursList.get(fonctionEnCoursList.size() - 1);
        assertThat(testFonctionEnCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionEnCours.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testFonctionEnCours.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateFonctionEnCoursWithPatch() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();

        // Update the fonctionEnCours using partial update
        FonctionEnCours partialUpdatedFonctionEnCours = new FonctionEnCours();
        partialUpdatedFonctionEnCours.setId(fonctionEnCours.getId());

        partialUpdatedFonctionEnCours.libelle(UPDATED_LIBELLE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restFonctionEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonctionEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonctionEnCours))
            )
            .andExpect(status().isOk());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
        FonctionEnCours testFonctionEnCours = fonctionEnCoursList.get(fonctionEnCoursList.size() - 1);
        assertThat(testFonctionEnCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionEnCours.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFonctionEnCours.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fonctionEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFonctionEnCours() throws Exception {
        int databaseSizeBeforeUpdate = fonctionEnCoursRepository.findAll().size();
        fonctionEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionEnCours))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FonctionEnCours in the database
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFonctionEnCours() throws Exception {
        // Initialize the database
        fonctionEnCoursRepository.saveAndFlush(fonctionEnCours);

        int databaseSizeBeforeDelete = fonctionEnCoursRepository.findAll().size();

        // Delete the fonctionEnCours
        restFonctionEnCoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, fonctionEnCours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FonctionEnCours> fonctionEnCoursList = fonctionEnCoursRepository.findAll();
        assertThat(fonctionEnCoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

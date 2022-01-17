package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.FonctionAncien;
import fr.senat.irsen.repository.FonctionAncienRepository;
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
 * Integration tests for the {@link FonctionAncienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FonctionAncienResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fonction-anciens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FonctionAncienRepository fonctionAncienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFonctionAncienMockMvc;

    private FonctionAncien fonctionAncien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FonctionAncien createEntity(EntityManager em) {
        FonctionAncien fonctionAncien = new FonctionAncien()
            .libelle(DEFAULT_LIBELLE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return fonctionAncien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FonctionAncien createUpdatedEntity(EntityManager em) {
        FonctionAncien fonctionAncien = new FonctionAncien()
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        return fonctionAncien;
    }

    @BeforeEach
    public void initTest() {
        fonctionAncien = createEntity(em);
    }

    @Test
    @Transactional
    void createFonctionAncien() throws Exception {
        int databaseSizeBeforeCreate = fonctionAncienRepository.findAll().size();
        // Create the FonctionAncien
        restFonctionAncienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isCreated());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeCreate + 1);
        FonctionAncien testFonctionAncien = fonctionAncienList.get(fonctionAncienList.size() - 1);
        assertThat(testFonctionAncien.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFonctionAncien.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testFonctionAncien.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createFonctionAncienWithExistingId() throws Exception {
        // Create the FonctionAncien with an existing ID
        fonctionAncien.setId(1L);

        int databaseSizeBeforeCreate = fonctionAncienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionAncienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFonctionAnciens() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        // Get all the fonctionAncienList
        restFonctionAncienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonctionAncien.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)));
    }

    @Test
    @Transactional
    void getFonctionAncien() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        // Get the fonctionAncien
        restFonctionAncienMockMvc
            .perform(get(ENTITY_API_URL_ID, fonctionAncien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fonctionAncien.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN));
    }

    @Test
    @Transactional
    void getNonExistingFonctionAncien() throws Exception {
        // Get the fonctionAncien
        restFonctionAncienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFonctionAncien() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();

        // Update the fonctionAncien
        FonctionAncien updatedFonctionAncien = fonctionAncienRepository.findById(fonctionAncien.getId()).get();
        // Disconnect from session so that the updates on updatedFonctionAncien are not directly saved in db
        em.detach(updatedFonctionAncien);
        updatedFonctionAncien.libelle(UPDATED_LIBELLE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restFonctionAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFonctionAncien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFonctionAncien))
            )
            .andExpect(status().isOk());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
        FonctionAncien testFonctionAncien = fonctionAncienList.get(fonctionAncienList.size() - 1);
        assertThat(testFonctionAncien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFonctionAncien.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fonctionAncien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fonctionAncien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFonctionAncienWithPatch() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();

        // Update the fonctionAncien using partial update
        FonctionAncien partialUpdatedFonctionAncien = new FonctionAncien();
        partialUpdatedFonctionAncien.setId(fonctionAncien.getId());

        partialUpdatedFonctionAncien.libelle(UPDATED_LIBELLE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restFonctionAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonctionAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonctionAncien))
            )
            .andExpect(status().isOk());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
        FonctionAncien testFonctionAncien = fonctionAncienList.get(fonctionAncienList.size() - 1);
        assertThat(testFonctionAncien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFonctionAncien.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateFonctionAncienWithPatch() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();

        // Update the fonctionAncien using partial update
        FonctionAncien partialUpdatedFonctionAncien = new FonctionAncien();
        partialUpdatedFonctionAncien.setId(fonctionAncien.getId());

        partialUpdatedFonctionAncien.libelle(UPDATED_LIBELLE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restFonctionAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFonctionAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFonctionAncien))
            )
            .andExpect(status().isOk());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
        FonctionAncien testFonctionAncien = fonctionAncienList.get(fonctionAncienList.size() - 1);
        assertThat(testFonctionAncien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFonctionAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFonctionAncien.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fonctionAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFonctionAncien() throws Exception {
        int databaseSizeBeforeUpdate = fonctionAncienRepository.findAll().size();
        fonctionAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFonctionAncienMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fonctionAncien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FonctionAncien in the database
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFonctionAncien() throws Exception {
        // Initialize the database
        fonctionAncienRepository.saveAndFlush(fonctionAncien);

        int databaseSizeBeforeDelete = fonctionAncienRepository.findAll().size();

        // Delete the fonctionAncien
        restFonctionAncienMockMvc
            .perform(delete(ENTITY_API_URL_ID, fonctionAncien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FonctionAncien> fonctionAncienList = fonctionAncienRepository.findAll();
        assertThat(fonctionAncienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

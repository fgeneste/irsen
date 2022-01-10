package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.MandatAncien;
import fr.senat.irsen.repository.MandatAncienRepository;
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
 * Integration tests for the {@link MandatAncienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MandatAncienResourceIT {

    private static final Long DEFAULT_ID_TYPE = 1L;
    private static final Long UPDATED_ID_TYPE = 2L;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CIRCONSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CIRCONSCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_AFFICHAGE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_AFFICHAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mandat-anciens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MandatAncienRepository mandatAncienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMandatAncienMockMvc;

    private MandatAncien mandatAncien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandatAncien createEntity(EntityManager em) {
        MandatAncien mandatAncien = new MandatAncien()
            .idType(DEFAULT_ID_TYPE)
            .libelle(DEFAULT_LIBELLE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .circonscription(DEFAULT_CIRCONSCRIPTION)
            .libelleAffichage(DEFAULT_LIBELLE_AFFICHAGE);
        return mandatAncien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandatAncien createUpdatedEntity(EntityManager em) {
        MandatAncien mandatAncien = new MandatAncien()
            .idType(UPDATED_ID_TYPE)
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);
        return mandatAncien;
    }

    @BeforeEach
    public void initTest() {
        mandatAncien = createEntity(em);
    }

    @Test
    @Transactional
    void createMandatAncien() throws Exception {
        int databaseSizeBeforeCreate = mandatAncienRepository.findAll().size();
        // Create the MandatAncien
        restMandatAncienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatAncien)))
            .andExpect(status().isCreated());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeCreate + 1);
        MandatAncien testMandatAncien = mandatAncienList.get(mandatAncienList.size() - 1);
        assertThat(testMandatAncien.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testMandatAncien.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMandatAncien.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testMandatAncien.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testMandatAncien.getCirconscription()).isEqualTo(DEFAULT_CIRCONSCRIPTION);
        assertThat(testMandatAncien.getLibelleAffichage()).isEqualTo(DEFAULT_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void createMandatAncienWithExistingId() throws Exception {
        // Create the MandatAncien with an existing ID
        mandatAncien.setId(1L);

        int databaseSizeBeforeCreate = mandatAncienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandatAncienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatAncien)))
            .andExpect(status().isBadRequest());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMandatAnciens() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        // Get all the mandatAncienList
        restMandatAncienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandatAncien.getId().intValue())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].circonscription").value(hasItem(DEFAULT_CIRCONSCRIPTION)))
            .andExpect(jsonPath("$.[*].libelleAffichage").value(hasItem(DEFAULT_LIBELLE_AFFICHAGE)));
    }

    @Test
    @Transactional
    void getMandatAncien() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        // Get the mandatAncien
        restMandatAncienMockMvc
            .perform(get(ENTITY_API_URL_ID, mandatAncien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mandatAncien.getId().intValue()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.circonscription").value(DEFAULT_CIRCONSCRIPTION))
            .andExpect(jsonPath("$.libelleAffichage").value(DEFAULT_LIBELLE_AFFICHAGE));
    }

    @Test
    @Transactional
    void getNonExistingMandatAncien() throws Exception {
        // Get the mandatAncien
        restMandatAncienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMandatAncien() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();

        // Update the mandatAncien
        MandatAncien updatedMandatAncien = mandatAncienRepository.findById(mandatAncien.getId()).get();
        // Disconnect from session so that the updates on updatedMandatAncien are not directly saved in db
        em.detach(updatedMandatAncien);
        updatedMandatAncien
            .idType(UPDATED_ID_TYPE)
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);

        restMandatAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMandatAncien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMandatAncien))
            )
            .andExpect(status().isOk());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
        MandatAncien testMandatAncien = mandatAncienList.get(mandatAncienList.size() - 1);
        assertThat(testMandatAncien.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testMandatAncien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMandatAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMandatAncien.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testMandatAncien.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatAncien.getLibelleAffichage()).isEqualTo(UPDATED_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void putNonExistingMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mandatAncien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandatAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandatAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatAncien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMandatAncienWithPatch() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();

        // Update the mandatAncien using partial update
        MandatAncien partialUpdatedMandatAncien = new MandatAncien();
        partialUpdatedMandatAncien.setId(mandatAncien.getId());

        partialUpdatedMandatAncien.dateDebut(UPDATED_DATE_DEBUT).circonscription(UPDATED_CIRCONSCRIPTION);

        restMandatAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandatAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandatAncien))
            )
            .andExpect(status().isOk());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
        MandatAncien testMandatAncien = mandatAncienList.get(mandatAncienList.size() - 1);
        assertThat(testMandatAncien.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testMandatAncien.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMandatAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMandatAncien.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testMandatAncien.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatAncien.getLibelleAffichage()).isEqualTo(DEFAULT_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void fullUpdateMandatAncienWithPatch() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();

        // Update the mandatAncien using partial update
        MandatAncien partialUpdatedMandatAncien = new MandatAncien();
        partialUpdatedMandatAncien.setId(mandatAncien.getId());

        partialUpdatedMandatAncien
            .idType(UPDATED_ID_TYPE)
            .libelle(UPDATED_LIBELLE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);

        restMandatAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandatAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandatAncien))
            )
            .andExpect(status().isOk());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
        MandatAncien testMandatAncien = mandatAncienList.get(mandatAncienList.size() - 1);
        assertThat(testMandatAncien.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testMandatAncien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMandatAncien.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testMandatAncien.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testMandatAncien.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatAncien.getLibelleAffichage()).isEqualTo(UPDATED_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mandatAncien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandatAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandatAncien))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMandatAncien() throws Exception {
        int databaseSizeBeforeUpdate = mandatAncienRepository.findAll().size();
        mandatAncien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatAncienMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mandatAncien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandatAncien in the database
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMandatAncien() throws Exception {
        // Initialize the database
        mandatAncienRepository.saveAndFlush(mandatAncien);

        int databaseSizeBeforeDelete = mandatAncienRepository.findAll().size();

        // Delete the mandatAncien
        restMandatAncienMockMvc
            .perform(delete(ENTITY_API_URL_ID, mandatAncien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MandatAncien> mandatAncienList = mandatAncienRepository.findAll();
        assertThat(mandatAncienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

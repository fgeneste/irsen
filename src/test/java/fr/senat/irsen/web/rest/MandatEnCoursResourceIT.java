package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.MandatEnCours;
import fr.senat.irsen.repository.MandatEnCoursRepository;
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
 * Integration tests for the {@link MandatEnCoursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MandatEnCoursResourceIT {

    private static final Long DEFAULT_ID_TYPE = 1L;
    private static final Long UPDATED_ID_TYPE = 2L;

    private static final Long DEFAULT_ID_FONCTION = 1L;
    private static final Long UPDATED_ID_FONCTION = 2L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_CIRCONSCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CIRCONSCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPUIS = "AAAAAAAAAA";
    private static final String UPDATED_DEPUIS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ELECTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ELECTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_POPULATION = "AAAAAAAAAA";
    private static final String UPDATED_POPULATION = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_AFFICHAGE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_AFFICHAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mandat-en-cours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MandatEnCoursRepository mandatEnCoursRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMandatEnCoursMockMvc;

    private MandatEnCours mandatEnCours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandatEnCours createEntity(EntityManager em) {
        MandatEnCours mandatEnCours = new MandatEnCours()
            .idType(DEFAULT_ID_TYPE)
            .idFonction(DEFAULT_ID_FONCTION)
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .libelleFonction(DEFAULT_LIBELLE_FONCTION)
            .circonscription(DEFAULT_CIRCONSCRIPTION)
            .depuis(DEFAULT_DEPUIS)
            .dateElection(DEFAULT_DATE_ELECTION)
            .population(DEFAULT_POPULATION)
            .libelleAffichage(DEFAULT_LIBELLE_AFFICHAGE);
        return mandatEnCours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandatEnCours createUpdatedEntity(EntityManager em) {
        MandatEnCours mandatEnCours = new MandatEnCours()
            .idType(UPDATED_ID_TYPE)
            .idFonction(UPDATED_ID_FONCTION)
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .libelleFonction(UPDATED_LIBELLE_FONCTION)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .depuis(UPDATED_DEPUIS)
            .dateElection(UPDATED_DATE_ELECTION)
            .population(UPDATED_POPULATION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);
        return mandatEnCours;
    }

    @BeforeEach
    public void initTest() {
        mandatEnCours = createEntity(em);
    }

    @Test
    @Transactional
    void createMandatEnCours() throws Exception {
        int databaseSizeBeforeCreate = mandatEnCoursRepository.findAll().size();
        // Create the MandatEnCours
        restMandatEnCoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatEnCours)))
            .andExpect(status().isCreated());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeCreate + 1);
        MandatEnCours testMandatEnCours = mandatEnCoursList.get(mandatEnCoursList.size() - 1);
        assertThat(testMandatEnCours.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testMandatEnCours.getIdFonction()).isEqualTo(DEFAULT_ID_FONCTION);
        assertThat(testMandatEnCours.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMandatEnCours.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMandatEnCours.getLibelleFonction()).isEqualTo(DEFAULT_LIBELLE_FONCTION);
        assertThat(testMandatEnCours.getCirconscription()).isEqualTo(DEFAULT_CIRCONSCRIPTION);
        assertThat(testMandatEnCours.getDepuis()).isEqualTo(DEFAULT_DEPUIS);
        assertThat(testMandatEnCours.getDateElection()).isEqualTo(DEFAULT_DATE_ELECTION);
        assertThat(testMandatEnCours.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testMandatEnCours.getLibelleAffichage()).isEqualTo(DEFAULT_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void createMandatEnCoursWithExistingId() throws Exception {
        // Create the MandatEnCours with an existing ID
        mandatEnCours.setId(1L);

        int databaseSizeBeforeCreate = mandatEnCoursRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandatEnCoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatEnCours)))
            .andExpect(status().isBadRequest());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMandatEnCours() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        // Get all the mandatEnCoursList
        restMandatEnCoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandatEnCours.getId().intValue())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].idFonction").value(hasItem(DEFAULT_ID_FONCTION.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].libelleFonction").value(hasItem(DEFAULT_LIBELLE_FONCTION)))
            .andExpect(jsonPath("$.[*].circonscription").value(hasItem(DEFAULT_CIRCONSCRIPTION)))
            .andExpect(jsonPath("$.[*].depuis").value(hasItem(DEFAULT_DEPUIS)))
            .andExpect(jsonPath("$.[*].dateElection").value(hasItem(DEFAULT_DATE_ELECTION.toString())))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION)))
            .andExpect(jsonPath("$.[*].libelleAffichage").value(hasItem(DEFAULT_LIBELLE_AFFICHAGE)));
    }

    @Test
    @Transactional
    void getMandatEnCours() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        // Get the mandatEnCours
        restMandatEnCoursMockMvc
            .perform(get(ENTITY_API_URL_ID, mandatEnCours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mandatEnCours.getId().intValue()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.intValue()))
            .andExpect(jsonPath("$.idFonction").value(DEFAULT_ID_FONCTION.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleFonction").value(DEFAULT_LIBELLE_FONCTION))
            .andExpect(jsonPath("$.circonscription").value(DEFAULT_CIRCONSCRIPTION))
            .andExpect(jsonPath("$.depuis").value(DEFAULT_DEPUIS))
            .andExpect(jsonPath("$.dateElection").value(DEFAULT_DATE_ELECTION.toString()))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION))
            .andExpect(jsonPath("$.libelleAffichage").value(DEFAULT_LIBELLE_AFFICHAGE));
    }

    @Test
    @Transactional
    void getNonExistingMandatEnCours() throws Exception {
        // Get the mandatEnCours
        restMandatEnCoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMandatEnCours() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();

        // Update the mandatEnCours
        MandatEnCours updatedMandatEnCours = mandatEnCoursRepository.findById(mandatEnCours.getId()).get();
        // Disconnect from session so that the updates on updatedMandatEnCours are not directly saved in db
        em.detach(updatedMandatEnCours);
        updatedMandatEnCours
            .idType(UPDATED_ID_TYPE)
            .idFonction(UPDATED_ID_FONCTION)
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .libelleFonction(UPDATED_LIBELLE_FONCTION)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .depuis(UPDATED_DEPUIS)
            .dateElection(UPDATED_DATE_ELECTION)
            .population(UPDATED_POPULATION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);

        restMandatEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMandatEnCours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMandatEnCours))
            )
            .andExpect(status().isOk());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
        MandatEnCours testMandatEnCours = mandatEnCoursList.get(mandatEnCoursList.size() - 1);
        assertThat(testMandatEnCours.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testMandatEnCours.getIdFonction()).isEqualTo(UPDATED_ID_FONCTION);
        assertThat(testMandatEnCours.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMandatEnCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMandatEnCours.getLibelleFonction()).isEqualTo(UPDATED_LIBELLE_FONCTION);
        assertThat(testMandatEnCours.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatEnCours.getDepuis()).isEqualTo(UPDATED_DEPUIS);
        assertThat(testMandatEnCours.getDateElection()).isEqualTo(UPDATED_DATE_ELECTION);
        assertThat(testMandatEnCours.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testMandatEnCours.getLibelleAffichage()).isEqualTo(UPDATED_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void putNonExistingMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mandatEnCours.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandatEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandatEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mandatEnCours)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMandatEnCoursWithPatch() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();

        // Update the mandatEnCours using partial update
        MandatEnCours partialUpdatedMandatEnCours = new MandatEnCours();
        partialUpdatedMandatEnCours.setId(mandatEnCours.getId());

        partialUpdatedMandatEnCours
            .idType(UPDATED_ID_TYPE)
            .idFonction(UPDATED_ID_FONCTION)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .depuis(UPDATED_DEPUIS)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);

        restMandatEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandatEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandatEnCours))
            )
            .andExpect(status().isOk());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
        MandatEnCours testMandatEnCours = mandatEnCoursList.get(mandatEnCoursList.size() - 1);
        assertThat(testMandatEnCours.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testMandatEnCours.getIdFonction()).isEqualTo(UPDATED_ID_FONCTION);
        assertThat(testMandatEnCours.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMandatEnCours.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMandatEnCours.getLibelleFonction()).isEqualTo(DEFAULT_LIBELLE_FONCTION);
        assertThat(testMandatEnCours.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatEnCours.getDepuis()).isEqualTo(UPDATED_DEPUIS);
        assertThat(testMandatEnCours.getDateElection()).isEqualTo(DEFAULT_DATE_ELECTION);
        assertThat(testMandatEnCours.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testMandatEnCours.getLibelleAffichage()).isEqualTo(UPDATED_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void fullUpdateMandatEnCoursWithPatch() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();

        // Update the mandatEnCours using partial update
        MandatEnCours partialUpdatedMandatEnCours = new MandatEnCours();
        partialUpdatedMandatEnCours.setId(mandatEnCours.getId());

        partialUpdatedMandatEnCours
            .idType(UPDATED_ID_TYPE)
            .idFonction(UPDATED_ID_FONCTION)
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .libelleFonction(UPDATED_LIBELLE_FONCTION)
            .circonscription(UPDATED_CIRCONSCRIPTION)
            .depuis(UPDATED_DEPUIS)
            .dateElection(UPDATED_DATE_ELECTION)
            .population(UPDATED_POPULATION)
            .libelleAffichage(UPDATED_LIBELLE_AFFICHAGE);

        restMandatEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandatEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandatEnCours))
            )
            .andExpect(status().isOk());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
        MandatEnCours testMandatEnCours = mandatEnCoursList.get(mandatEnCoursList.size() - 1);
        assertThat(testMandatEnCours.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testMandatEnCours.getIdFonction()).isEqualTo(UPDATED_ID_FONCTION);
        assertThat(testMandatEnCours.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMandatEnCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMandatEnCours.getLibelleFonction()).isEqualTo(UPDATED_LIBELLE_FONCTION);
        assertThat(testMandatEnCours.getCirconscription()).isEqualTo(UPDATED_CIRCONSCRIPTION);
        assertThat(testMandatEnCours.getDepuis()).isEqualTo(UPDATED_DEPUIS);
        assertThat(testMandatEnCours.getDateElection()).isEqualTo(UPDATED_DATE_ELECTION);
        assertThat(testMandatEnCours.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testMandatEnCours.getLibelleAffichage()).isEqualTo(UPDATED_LIBELLE_AFFICHAGE);
    }

    @Test
    @Transactional
    void patchNonExistingMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mandatEnCours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandatEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandatEnCours))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMandatEnCours() throws Exception {
        int databaseSizeBeforeUpdate = mandatEnCoursRepository.findAll().size();
        mandatEnCours.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandatEnCoursMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mandatEnCours))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandatEnCours in the database
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMandatEnCours() throws Exception {
        // Initialize the database
        mandatEnCoursRepository.saveAndFlush(mandatEnCours);

        int databaseSizeBeforeDelete = mandatEnCoursRepository.findAll().size();

        // Delete the mandatEnCours
        restMandatEnCoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, mandatEnCours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MandatEnCours> mandatEnCoursList = mandatEnCoursRepository.findAll();
        assertThat(mandatEnCoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

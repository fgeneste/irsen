package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.DepartementNaissance;
import fr.senat.irsen.repository.DepartementNaissanceRepository;
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
 * Integration tests for the {@link DepartementNaissanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartementNaissanceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL = false;
    private static final Boolean UPDATED_AVEC_CONSEIL_DEPARTEMENTAL = true;

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_SIRPAS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SIRPAS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_COMPARAISON = "AAAAAAAAAA";
    private static final String UPDATED_CODE_COMPARAISON = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_COMPLET = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMPLET = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_AVEC_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_AVEC_ARTICLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/departement-naissances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepartementNaissanceRepository departementNaissanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartementNaissanceMockMvc;

    private DepartementNaissance departementNaissance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartementNaissance createEntity(EntityManager em) {
        DepartementNaissance departementNaissance = new DepartementNaissance()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .avecConseilDepartemental(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(DEFAULT_ARTICLE)
            .codeSirpas(DEFAULT_CODE_SIRPAS)
            .codeComparaison(DEFAULT_CODE_COMPARAISON)
            .libelleComplet(DEFAULT_LIBELLE_COMPLET)
            .libelleAvecArticle(DEFAULT_LIBELLE_AVEC_ARTICLE);
        return departementNaissance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartementNaissance createUpdatedEntity(EntityManager em) {
        DepartementNaissance departementNaissance = new DepartementNaissance()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeComparaison(UPDATED_CODE_COMPARAISON)
            .libelleComplet(UPDATED_LIBELLE_COMPLET)
            .libelleAvecArticle(UPDATED_LIBELLE_AVEC_ARTICLE);
        return departementNaissance;
    }

    @BeforeEach
    public void initTest() {
        departementNaissance = createEntity(em);
    }

    @Test
    @Transactional
    void createDepartementNaissance() throws Exception {
        int databaseSizeBeforeCreate = departementNaissanceRepository.findAll().size();
        // Create the DepartementNaissance
        restDepartementNaissanceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isCreated());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeCreate + 1);
        DepartementNaissance testDepartementNaissance = departementNaissanceList.get(departementNaissanceList.size() - 1);
        assertThat(testDepartementNaissance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDepartementNaissance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testDepartementNaissance.getAvecConseilDepartemental()).isEqualTo(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testDepartementNaissance.getArticle()).isEqualTo(DEFAULT_ARTICLE);
        assertThat(testDepartementNaissance.getCodeSirpas()).isEqualTo(DEFAULT_CODE_SIRPAS);
        assertThat(testDepartementNaissance.getCodeComparaison()).isEqualTo(DEFAULT_CODE_COMPARAISON);
        assertThat(testDepartementNaissance.getLibelleComplet()).isEqualTo(DEFAULT_LIBELLE_COMPLET);
        assertThat(testDepartementNaissance.getLibelleAvecArticle()).isEqualTo(DEFAULT_LIBELLE_AVEC_ARTICLE);
    }

    @Test
    @Transactional
    void createDepartementNaissanceWithExistingId() throws Exception {
        // Create the DepartementNaissance with an existing ID
        departementNaissance.setId(1L);

        int databaseSizeBeforeCreate = departementNaissanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartementNaissanceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartementNaissances() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        // Get all the departementNaissanceList
        restDepartementNaissanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departementNaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].avecConseilDepartemental").value(hasItem(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL.booleanValue())))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)))
            .andExpect(jsonPath("$.[*].codeSirpas").value(hasItem(DEFAULT_CODE_SIRPAS)))
            .andExpect(jsonPath("$.[*].codeComparaison").value(hasItem(DEFAULT_CODE_COMPARAISON)))
            .andExpect(jsonPath("$.[*].libelleComplet").value(hasItem(DEFAULT_LIBELLE_COMPLET)))
            .andExpect(jsonPath("$.[*].libelleAvecArticle").value(hasItem(DEFAULT_LIBELLE_AVEC_ARTICLE)));
    }

    @Test
    @Transactional
    void getDepartementNaissance() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        // Get the departementNaissance
        restDepartementNaissanceMockMvc
            .perform(get(ENTITY_API_URL_ID, departementNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departementNaissance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.avecConseilDepartemental").value(DEFAULT_AVEC_CONSEIL_DEPARTEMENTAL.booleanValue()))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE))
            .andExpect(jsonPath("$.codeSirpas").value(DEFAULT_CODE_SIRPAS))
            .andExpect(jsonPath("$.codeComparaison").value(DEFAULT_CODE_COMPARAISON))
            .andExpect(jsonPath("$.libelleComplet").value(DEFAULT_LIBELLE_COMPLET))
            .andExpect(jsonPath("$.libelleAvecArticle").value(DEFAULT_LIBELLE_AVEC_ARTICLE));
    }

    @Test
    @Transactional
    void getNonExistingDepartementNaissance() throws Exception {
        // Get the departementNaissance
        restDepartementNaissanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDepartementNaissance() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();

        // Update the departementNaissance
        DepartementNaissance updatedDepartementNaissance = departementNaissanceRepository.findById(departementNaissance.getId()).get();
        // Disconnect from session so that the updates on updatedDepartementNaissance are not directly saved in db
        em.detach(updatedDepartementNaissance);
        updatedDepartementNaissance
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeComparaison(UPDATED_CODE_COMPARAISON)
            .libelleComplet(UPDATED_LIBELLE_COMPLET)
            .libelleAvecArticle(UPDATED_LIBELLE_AVEC_ARTICLE);

        restDepartementNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartementNaissance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDepartementNaissance))
            )
            .andExpect(status().isOk());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
        DepartementNaissance testDepartementNaissance = departementNaissanceList.get(departementNaissanceList.size() - 1);
        assertThat(testDepartementNaissance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDepartementNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDepartementNaissance.getAvecConseilDepartemental()).isEqualTo(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testDepartementNaissance.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testDepartementNaissance.getCodeSirpas()).isEqualTo(UPDATED_CODE_SIRPAS);
        assertThat(testDepartementNaissance.getCodeComparaison()).isEqualTo(UPDATED_CODE_COMPARAISON);
        assertThat(testDepartementNaissance.getLibelleComplet()).isEqualTo(UPDATED_LIBELLE_COMPLET);
        assertThat(testDepartementNaissance.getLibelleAvecArticle()).isEqualTo(UPDATED_LIBELLE_AVEC_ARTICLE);
    }

    @Test
    @Transactional
    void putNonExistingDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departementNaissance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartementNaissanceWithPatch() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();

        // Update the departementNaissance using partial update
        DepartementNaissance partialUpdatedDepartementNaissance = new DepartementNaissance();
        partialUpdatedDepartementNaissance.setId(departementNaissance.getId());

        partialUpdatedDepartementNaissance.libelle(UPDATED_LIBELLE).avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);

        restDepartementNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartementNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartementNaissance))
            )
            .andExpect(status().isOk());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
        DepartementNaissance testDepartementNaissance = departementNaissanceList.get(departementNaissanceList.size() - 1);
        assertThat(testDepartementNaissance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDepartementNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDepartementNaissance.getAvecConseilDepartemental()).isEqualTo(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testDepartementNaissance.getArticle()).isEqualTo(DEFAULT_ARTICLE);
        assertThat(testDepartementNaissance.getCodeSirpas()).isEqualTo(DEFAULT_CODE_SIRPAS);
        assertThat(testDepartementNaissance.getCodeComparaison()).isEqualTo(DEFAULT_CODE_COMPARAISON);
        assertThat(testDepartementNaissance.getLibelleComplet()).isEqualTo(DEFAULT_LIBELLE_COMPLET);
        assertThat(testDepartementNaissance.getLibelleAvecArticle()).isEqualTo(DEFAULT_LIBELLE_AVEC_ARTICLE);
    }

    @Test
    @Transactional
    void fullUpdateDepartementNaissanceWithPatch() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();

        // Update the departementNaissance using partial update
        DepartementNaissance partialUpdatedDepartementNaissance = new DepartementNaissance();
        partialUpdatedDepartementNaissance.setId(departementNaissance.getId());

        partialUpdatedDepartementNaissance
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .avecConseilDepartemental(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL)
            .article(UPDATED_ARTICLE)
            .codeSirpas(UPDATED_CODE_SIRPAS)
            .codeComparaison(UPDATED_CODE_COMPARAISON)
            .libelleComplet(UPDATED_LIBELLE_COMPLET)
            .libelleAvecArticle(UPDATED_LIBELLE_AVEC_ARTICLE);

        restDepartementNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartementNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartementNaissance))
            )
            .andExpect(status().isOk());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
        DepartementNaissance testDepartementNaissance = departementNaissanceList.get(departementNaissanceList.size() - 1);
        assertThat(testDepartementNaissance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDepartementNaissance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDepartementNaissance.getAvecConseilDepartemental()).isEqualTo(UPDATED_AVEC_CONSEIL_DEPARTEMENTAL);
        assertThat(testDepartementNaissance.getArticle()).isEqualTo(UPDATED_ARTICLE);
        assertThat(testDepartementNaissance.getCodeSirpas()).isEqualTo(UPDATED_CODE_SIRPAS);
        assertThat(testDepartementNaissance.getCodeComparaison()).isEqualTo(UPDATED_CODE_COMPARAISON);
        assertThat(testDepartementNaissance.getLibelleComplet()).isEqualTo(UPDATED_LIBELLE_COMPLET);
        assertThat(testDepartementNaissance.getLibelleAvecArticle()).isEqualTo(UPDATED_LIBELLE_AVEC_ARTICLE);
    }

    @Test
    @Transactional
    void patchNonExistingDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departementNaissance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartementNaissance() throws Exception {
        int databaseSizeBeforeUpdate = departementNaissanceRepository.findAll().size();
        departementNaissance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartementNaissanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departementNaissance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartementNaissance in the database
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartementNaissance() throws Exception {
        // Initialize the database
        departementNaissanceRepository.saveAndFlush(departementNaissance);

        int databaseSizeBeforeDelete = departementNaissanceRepository.findAll().size();

        // Delete the departementNaissance
        restDepartementNaissanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, departementNaissance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartementNaissance> departementNaissanceList = departementNaissanceRepository.findAll();
        assertThat(departementNaissanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

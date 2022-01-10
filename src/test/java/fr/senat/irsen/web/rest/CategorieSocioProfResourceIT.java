package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.CategorieSocioProf;
import fr.senat.irsen.repository.CategorieSocioProfRepository;
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
 * Integration tests for the {@link CategorieSocioProfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieSocioProfResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_COMPLET = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMPLET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorie-socio-profs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieSocioProfRepository categorieSocioProfRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieSocioProfMockMvc;

    private CategorieSocioProf categorieSocioProf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieSocioProf createEntity(EntityManager em) {
        CategorieSocioProf categorieSocioProf = new CategorieSocioProf()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .libelleComplet(DEFAULT_LIBELLE_COMPLET);
        return categorieSocioProf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieSocioProf createUpdatedEntity(EntityManager em) {
        CategorieSocioProf categorieSocioProf = new CategorieSocioProf()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .libelleComplet(UPDATED_LIBELLE_COMPLET);
        return categorieSocioProf;
    }

    @BeforeEach
    public void initTest() {
        categorieSocioProf = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieSocioProf() throws Exception {
        int databaseSizeBeforeCreate = categorieSocioProfRepository.findAll().size();
        // Create the CategorieSocioProf
        restCategorieSocioProfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieSocioProf testCategorieSocioProf = categorieSocioProfList.get(categorieSocioProfList.size() - 1);
        assertThat(testCategorieSocioProf.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategorieSocioProf.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieSocioProf.getLibelleComplet()).isEqualTo(DEFAULT_LIBELLE_COMPLET);
    }

    @Test
    @Transactional
    void createCategorieSocioProfWithExistingId() throws Exception {
        // Create the CategorieSocioProf with an existing ID
        categorieSocioProf.setId(1L);

        int databaseSizeBeforeCreate = categorieSocioProfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieSocioProfMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieSocioProfs() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        // Get all the categorieSocioProfList
        restCategorieSocioProfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieSocioProf.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].libelleComplet").value(hasItem(DEFAULT_LIBELLE_COMPLET)));
    }

    @Test
    @Transactional
    void getCategorieSocioProf() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        // Get the categorieSocioProf
        restCategorieSocioProfMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieSocioProf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieSocioProf.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.libelleComplet").value(DEFAULT_LIBELLE_COMPLET));
    }

    @Test
    @Transactional
    void getNonExistingCategorieSocioProf() throws Exception {
        // Get the categorieSocioProf
        restCategorieSocioProfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieSocioProf() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();

        // Update the categorieSocioProf
        CategorieSocioProf updatedCategorieSocioProf = categorieSocioProfRepository.findById(categorieSocioProf.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieSocioProf are not directly saved in db
        em.detach(updatedCategorieSocioProf);
        updatedCategorieSocioProf.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).libelleComplet(UPDATED_LIBELLE_COMPLET);

        restCategorieSocioProfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategorieSocioProf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategorieSocioProf))
            )
            .andExpect(status().isOk());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
        CategorieSocioProf testCategorieSocioProf = categorieSocioProfList.get(categorieSocioProfList.size() - 1);
        assertThat(testCategorieSocioProf.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategorieSocioProf.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieSocioProf.getLibelleComplet()).isEqualTo(UPDATED_LIBELLE_COMPLET);
    }

    @Test
    @Transactional
    void putNonExistingCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieSocioProf.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieSocioProfWithPatch() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();

        // Update the categorieSocioProf using partial update
        CategorieSocioProf partialUpdatedCategorieSocioProf = new CategorieSocioProf();
        partialUpdatedCategorieSocioProf.setId(categorieSocioProf.getId());

        partialUpdatedCategorieSocioProf.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restCategorieSocioProfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieSocioProf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieSocioProf))
            )
            .andExpect(status().isOk());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
        CategorieSocioProf testCategorieSocioProf = categorieSocioProfList.get(categorieSocioProfList.size() - 1);
        assertThat(testCategorieSocioProf.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategorieSocioProf.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieSocioProf.getLibelleComplet()).isEqualTo(DEFAULT_LIBELLE_COMPLET);
    }

    @Test
    @Transactional
    void fullUpdateCategorieSocioProfWithPatch() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();

        // Update the categorieSocioProf using partial update
        CategorieSocioProf partialUpdatedCategorieSocioProf = new CategorieSocioProf();
        partialUpdatedCategorieSocioProf.setId(categorieSocioProf.getId());

        partialUpdatedCategorieSocioProf.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).libelleComplet(UPDATED_LIBELLE_COMPLET);

        restCategorieSocioProfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieSocioProf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieSocioProf))
            )
            .andExpect(status().isOk());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
        CategorieSocioProf testCategorieSocioProf = categorieSocioProfList.get(categorieSocioProfList.size() - 1);
        assertThat(testCategorieSocioProf.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategorieSocioProf.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieSocioProf.getLibelleComplet()).isEqualTo(UPDATED_LIBELLE_COMPLET);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieSocioProf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieSocioProf() throws Exception {
        int databaseSizeBeforeUpdate = categorieSocioProfRepository.findAll().size();
        categorieSocioProf.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieSocioProfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieSocioProf))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieSocioProf in the database
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieSocioProf() throws Exception {
        // Initialize the database
        categorieSocioProfRepository.saveAndFlush(categorieSocioProf);

        int databaseSizeBeforeDelete = categorieSocioProfRepository.findAll().size();

        // Delete the categorieSocioProf
        restCategorieSocioProfMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieSocioProf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieSocioProf> categorieSocioProfList = categorieSocioProfRepository.findAll();
        assertThat(categorieSocioProfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

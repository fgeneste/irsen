package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.Decoration;
import fr.senat.irsen.repository.DecorationRepository;
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
 * Integration tests for the {@link DecorationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DecorationResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/decorations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DecorationRepository decorationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDecorationMockMvc;

    private Decoration decoration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decoration createEntity(EntityManager em) {
        Decoration decoration = new Decoration().type(DEFAULT_TYPE).grade(DEFAULT_GRADE);
        return decoration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decoration createUpdatedEntity(EntityManager em) {
        Decoration decoration = new Decoration().type(UPDATED_TYPE).grade(UPDATED_GRADE);
        return decoration;
    }

    @BeforeEach
    public void initTest() {
        decoration = createEntity(em);
    }

    @Test
    @Transactional
    void createDecoration() throws Exception {
        int databaseSizeBeforeCreate = decorationRepository.findAll().size();
        // Create the Decoration
        restDecorationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decoration)))
            .andExpect(status().isCreated());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeCreate + 1);
        Decoration testDecoration = decorationList.get(decorationList.size() - 1);
        assertThat(testDecoration.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDecoration.getGrade()).isEqualTo(DEFAULT_GRADE);
    }

    @Test
    @Transactional
    void createDecorationWithExistingId() throws Exception {
        // Create the Decoration with an existing ID
        decoration.setId(1L);

        int databaseSizeBeforeCreate = decorationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDecorationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decoration)))
            .andExpect(status().isBadRequest());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDecorations() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        // Get all the decorationList
        restDecorationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(decoration.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)));
    }

    @Test
    @Transactional
    void getDecoration() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        // Get the decoration
        restDecorationMockMvc
            .perform(get(ENTITY_API_URL_ID, decoration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(decoration.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE));
    }

    @Test
    @Transactional
    void getNonExistingDecoration() throws Exception {
        // Get the decoration
        restDecorationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDecoration() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();

        // Update the decoration
        Decoration updatedDecoration = decorationRepository.findById(decoration.getId()).get();
        // Disconnect from session so that the updates on updatedDecoration are not directly saved in db
        em.detach(updatedDecoration);
        updatedDecoration.type(UPDATED_TYPE).grade(UPDATED_GRADE);

        restDecorationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDecoration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDecoration))
            )
            .andExpect(status().isOk());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
        Decoration testDecoration = decorationList.get(decorationList.size() - 1);
        assertThat(testDecoration.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDecoration.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    void putNonExistingDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, decoration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decoration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decoration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decoration)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDecorationWithPatch() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();

        // Update the decoration using partial update
        Decoration partialUpdatedDecoration = new Decoration();
        partialUpdatedDecoration.setId(decoration.getId());

        partialUpdatedDecoration.type(UPDATED_TYPE).grade(UPDATED_GRADE);

        restDecorationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecoration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecoration))
            )
            .andExpect(status().isOk());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
        Decoration testDecoration = decorationList.get(decorationList.size() - 1);
        assertThat(testDecoration.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDecoration.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    void fullUpdateDecorationWithPatch() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();

        // Update the decoration using partial update
        Decoration partialUpdatedDecoration = new Decoration();
        partialUpdatedDecoration.setId(decoration.getId());

        partialUpdatedDecoration.type(UPDATED_TYPE).grade(UPDATED_GRADE);

        restDecorationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecoration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecoration))
            )
            .andExpect(status().isOk());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
        Decoration testDecoration = decorationList.get(decorationList.size() - 1);
        assertThat(testDecoration.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDecoration.getGrade()).isEqualTo(UPDATED_GRADE);
    }

    @Test
    @Transactional
    void patchNonExistingDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, decoration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decoration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decoration))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDecoration() throws Exception {
        int databaseSizeBeforeUpdate = decorationRepository.findAll().size();
        decoration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecorationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(decoration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decoration in the database
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDecoration() throws Exception {
        // Initialize the database
        decorationRepository.saveAndFlush(decoration);

        int databaseSizeBeforeDelete = decorationRepository.findAll().size();

        // Delete the decoration
        restDecorationMockMvc
            .perform(delete(ENTITY_API_URL_ID, decoration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Decoration> decorationList = decorationRepository.findAll();
        assertThat(decorationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

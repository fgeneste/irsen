package fr.senat.irsen.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.senat.irsen.IntegrationTest;
import fr.senat.irsen.domain.EtatCivil;
import fr.senat.irsen.repository.EtatCivilRepository;
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
 * Integration tests for the {@link EtatCivilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtatCivilResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITE = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_FAMILLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FAMILLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MARITAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MARITAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_USUEL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_USUEL = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_USUEL = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_USUEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMUNE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_COURRIEL = "AAAAAAAAAA";
    private static final String UPDATED_COURRIEL = "BBBBBBBBBB";

    private static final String DEFAULT_COURRIEL_2 = "AAAAAAAAAA";
    private static final String UPDATED_COURRIEL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_PORTABLE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_PORTABLE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_PORTABLE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_PORTABLE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_FIXE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_FIXE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etat-civils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtatCivilRepository etatCivilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtatCivilMockMvc;

    private EtatCivil etatCivil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCivil createEntity(EntityManager em) {
        EtatCivil etatCivil = new EtatCivil()
            .matricule(DEFAULT_MATRICULE)
            .civilite(DEFAULT_CIVILITE)
            .titre(DEFAULT_TITRE)
            .nomFamille(DEFAULT_NOM_FAMILLE)
            .nomMarital(DEFAULT_NOM_MARITAL)
            .nomUsuel(DEFAULT_NOM_USUEL)
            .prenoms(DEFAULT_PRENOMS)
            .prenomUsuel(DEFAULT_PRENOM_USUEL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .communeNaissance(DEFAULT_COMMUNE_NAISSANCE)
            .profession(DEFAULT_PROFESSION)
            .courriel(DEFAULT_COURRIEL)
            .courriel2(DEFAULT_COURRIEL_2)
            .telephonePortable(DEFAULT_TELEPHONE_PORTABLE)
            .telephonePortable2(DEFAULT_TELEPHONE_PORTABLE_2)
            .telephoneFixe(DEFAULT_TELEPHONE_FIXE);
        return etatCivil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCivil createUpdatedEntity(EntityManager em) {
        EtatCivil etatCivil = new EtatCivil()
            .matricule(UPDATED_MATRICULE)
            .civilite(UPDATED_CIVILITE)
            .titre(UPDATED_TITRE)
            .nomFamille(UPDATED_NOM_FAMILLE)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenoms(UPDATED_PRENOMS)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .communeNaissance(UPDATED_COMMUNE_NAISSANCE)
            .profession(UPDATED_PROFESSION)
            .courriel(UPDATED_COURRIEL)
            .courriel2(UPDATED_COURRIEL_2)
            .telephonePortable(UPDATED_TELEPHONE_PORTABLE)
            .telephonePortable2(UPDATED_TELEPHONE_PORTABLE_2)
            .telephoneFixe(UPDATED_TELEPHONE_FIXE);
        return etatCivil;
    }

    @BeforeEach
    public void initTest() {
        etatCivil = createEntity(em);
    }

    @Test
    @Transactional
    void createEtatCivil() throws Exception {
        int databaseSizeBeforeCreate = etatCivilRepository.findAll().size();
        // Create the EtatCivil
        restEtatCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatCivil)))
            .andExpect(status().isCreated());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeCreate + 1);
        EtatCivil testEtatCivil = etatCivilList.get(etatCivilList.size() - 1);
        assertThat(testEtatCivil.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEtatCivil.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testEtatCivil.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testEtatCivil.getNomFamille()).isEqualTo(DEFAULT_NOM_FAMILLE);
        assertThat(testEtatCivil.getNomMarital()).isEqualTo(DEFAULT_NOM_MARITAL);
        assertThat(testEtatCivil.getNomUsuel()).isEqualTo(DEFAULT_NOM_USUEL);
        assertThat(testEtatCivil.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEtatCivil.getPrenomUsuel()).isEqualTo(DEFAULT_PRENOM_USUEL);
        assertThat(testEtatCivil.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEtatCivil.getCommuneNaissance()).isEqualTo(DEFAULT_COMMUNE_NAISSANCE);
        assertThat(testEtatCivil.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testEtatCivil.getCourriel()).isEqualTo(DEFAULT_COURRIEL);
        assertThat(testEtatCivil.getCourriel2()).isEqualTo(DEFAULT_COURRIEL_2);
        assertThat(testEtatCivil.getTelephonePortable()).isEqualTo(DEFAULT_TELEPHONE_PORTABLE);
        assertThat(testEtatCivil.getTelephonePortable2()).isEqualTo(DEFAULT_TELEPHONE_PORTABLE_2);
        assertThat(testEtatCivil.getTelephoneFixe()).isEqualTo(DEFAULT_TELEPHONE_FIXE);
    }

    @Test
    @Transactional
    void createEtatCivilWithExistingId() throws Exception {
        // Create the EtatCivil with an existing ID
        etatCivil.setId(1L);

        int databaseSizeBeforeCreate = etatCivilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatCivilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatCivil)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtatCivils() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        // Get all the etatCivilList
        restEtatCivilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatCivil.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].nomFamille").value(hasItem(DEFAULT_NOM_FAMILLE)))
            .andExpect(jsonPath("$.[*].nomMarital").value(hasItem(DEFAULT_NOM_MARITAL)))
            .andExpect(jsonPath("$.[*].nomUsuel").value(hasItem(DEFAULT_NOM_USUEL)))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].prenomUsuel").value(hasItem(DEFAULT_PRENOM_USUEL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].communeNaissance").value(hasItem(DEFAULT_COMMUNE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].courriel").value(hasItem(DEFAULT_COURRIEL)))
            .andExpect(jsonPath("$.[*].courriel2").value(hasItem(DEFAULT_COURRIEL_2)))
            .andExpect(jsonPath("$.[*].telephonePortable").value(hasItem(DEFAULT_TELEPHONE_PORTABLE)))
            .andExpect(jsonPath("$.[*].telephonePortable2").value(hasItem(DEFAULT_TELEPHONE_PORTABLE_2)))
            .andExpect(jsonPath("$.[*].telephoneFixe").value(hasItem(DEFAULT_TELEPHONE_FIXE)));
    }

    @Test
    @Transactional
    void getEtatCivil() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        // Get the etatCivil
        restEtatCivilMockMvc
            .perform(get(ENTITY_API_URL_ID, etatCivil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatCivil.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.nomFamille").value(DEFAULT_NOM_FAMILLE))
            .andExpect(jsonPath("$.nomMarital").value(DEFAULT_NOM_MARITAL))
            .andExpect(jsonPath("$.nomUsuel").value(DEFAULT_NOM_USUEL))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS))
            .andExpect(jsonPath("$.prenomUsuel").value(DEFAULT_PRENOM_USUEL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.communeNaissance").value(DEFAULT_COMMUNE_NAISSANCE))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.courriel").value(DEFAULT_COURRIEL))
            .andExpect(jsonPath("$.courriel2").value(DEFAULT_COURRIEL_2))
            .andExpect(jsonPath("$.telephonePortable").value(DEFAULT_TELEPHONE_PORTABLE))
            .andExpect(jsonPath("$.telephonePortable2").value(DEFAULT_TELEPHONE_PORTABLE_2))
            .andExpect(jsonPath("$.telephoneFixe").value(DEFAULT_TELEPHONE_FIXE));
    }

    @Test
    @Transactional
    void getNonExistingEtatCivil() throws Exception {
        // Get the etatCivil
        restEtatCivilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtatCivil() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();

        // Update the etatCivil
        EtatCivil updatedEtatCivil = etatCivilRepository.findById(etatCivil.getId()).get();
        // Disconnect from session so that the updates on updatedEtatCivil are not directly saved in db
        em.detach(updatedEtatCivil);
        updatedEtatCivil
            .matricule(UPDATED_MATRICULE)
            .civilite(UPDATED_CIVILITE)
            .titre(UPDATED_TITRE)
            .nomFamille(UPDATED_NOM_FAMILLE)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenoms(UPDATED_PRENOMS)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .communeNaissance(UPDATED_COMMUNE_NAISSANCE)
            .profession(UPDATED_PROFESSION)
            .courriel(UPDATED_COURRIEL)
            .courriel2(UPDATED_COURRIEL_2)
            .telephonePortable(UPDATED_TELEPHONE_PORTABLE)
            .telephonePortable2(UPDATED_TELEPHONE_PORTABLE_2)
            .telephoneFixe(UPDATED_TELEPHONE_FIXE);

        restEtatCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtatCivil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtatCivil))
            )
            .andExpect(status().isOk());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
        EtatCivil testEtatCivil = etatCivilList.get(etatCivilList.size() - 1);
        assertThat(testEtatCivil.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtatCivil.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testEtatCivil.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEtatCivil.getNomFamille()).isEqualTo(UPDATED_NOM_FAMILLE);
        assertThat(testEtatCivil.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testEtatCivil.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testEtatCivil.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEtatCivil.getPrenomUsuel()).isEqualTo(UPDATED_PRENOM_USUEL);
        assertThat(testEtatCivil.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEtatCivil.getCommuneNaissance()).isEqualTo(UPDATED_COMMUNE_NAISSANCE);
        assertThat(testEtatCivil.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testEtatCivil.getCourriel()).isEqualTo(UPDATED_COURRIEL);
        assertThat(testEtatCivil.getCourriel2()).isEqualTo(UPDATED_COURRIEL_2);
        assertThat(testEtatCivil.getTelephonePortable()).isEqualTo(UPDATED_TELEPHONE_PORTABLE);
        assertThat(testEtatCivil.getTelephonePortable2()).isEqualTo(UPDATED_TELEPHONE_PORTABLE_2);
        assertThat(testEtatCivil.getTelephoneFixe()).isEqualTo(UPDATED_TELEPHONE_FIXE);
    }

    @Test
    @Transactional
    void putNonExistingEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etatCivil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etatCivil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtatCivilWithPatch() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();

        // Update the etatCivil using partial update
        EtatCivil partialUpdatedEtatCivil = new EtatCivil();
        partialUpdatedEtatCivil.setId(etatCivil.getId());

        partialUpdatedEtatCivil
            .matricule(UPDATED_MATRICULE)
            .civilite(UPDATED_CIVILITE)
            .titre(UPDATED_TITRE)
            .nomFamille(UPDATED_NOM_FAMILLE)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenoms(UPDATED_PRENOMS)
            .communeNaissance(UPDATED_COMMUNE_NAISSANCE)
            .profession(UPDATED_PROFESSION)
            .courriel2(UPDATED_COURRIEL_2)
            .telephonePortable2(UPDATED_TELEPHONE_PORTABLE_2);

        restEtatCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatCivil))
            )
            .andExpect(status().isOk());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
        EtatCivil testEtatCivil = etatCivilList.get(etatCivilList.size() - 1);
        assertThat(testEtatCivil.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtatCivil.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testEtatCivil.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEtatCivil.getNomFamille()).isEqualTo(UPDATED_NOM_FAMILLE);
        assertThat(testEtatCivil.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testEtatCivil.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testEtatCivil.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEtatCivil.getPrenomUsuel()).isEqualTo(DEFAULT_PRENOM_USUEL);
        assertThat(testEtatCivil.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEtatCivil.getCommuneNaissance()).isEqualTo(UPDATED_COMMUNE_NAISSANCE);
        assertThat(testEtatCivil.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testEtatCivil.getCourriel()).isEqualTo(DEFAULT_COURRIEL);
        assertThat(testEtatCivil.getCourriel2()).isEqualTo(UPDATED_COURRIEL_2);
        assertThat(testEtatCivil.getTelephonePortable()).isEqualTo(DEFAULT_TELEPHONE_PORTABLE);
        assertThat(testEtatCivil.getTelephonePortable2()).isEqualTo(UPDATED_TELEPHONE_PORTABLE_2);
        assertThat(testEtatCivil.getTelephoneFixe()).isEqualTo(DEFAULT_TELEPHONE_FIXE);
    }

    @Test
    @Transactional
    void fullUpdateEtatCivilWithPatch() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();

        // Update the etatCivil using partial update
        EtatCivil partialUpdatedEtatCivil = new EtatCivil();
        partialUpdatedEtatCivil.setId(etatCivil.getId());

        partialUpdatedEtatCivil
            .matricule(UPDATED_MATRICULE)
            .civilite(UPDATED_CIVILITE)
            .titre(UPDATED_TITRE)
            .nomFamille(UPDATED_NOM_FAMILLE)
            .nomMarital(UPDATED_NOM_MARITAL)
            .nomUsuel(UPDATED_NOM_USUEL)
            .prenoms(UPDATED_PRENOMS)
            .prenomUsuel(UPDATED_PRENOM_USUEL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .communeNaissance(UPDATED_COMMUNE_NAISSANCE)
            .profession(UPDATED_PROFESSION)
            .courriel(UPDATED_COURRIEL)
            .courriel2(UPDATED_COURRIEL_2)
            .telephonePortable(UPDATED_TELEPHONE_PORTABLE)
            .telephonePortable2(UPDATED_TELEPHONE_PORTABLE_2)
            .telephoneFixe(UPDATED_TELEPHONE_FIXE);

        restEtatCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatCivil))
            )
            .andExpect(status().isOk());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
        EtatCivil testEtatCivil = etatCivilList.get(etatCivilList.size() - 1);
        assertThat(testEtatCivil.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtatCivil.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testEtatCivil.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEtatCivil.getNomFamille()).isEqualTo(UPDATED_NOM_FAMILLE);
        assertThat(testEtatCivil.getNomMarital()).isEqualTo(UPDATED_NOM_MARITAL);
        assertThat(testEtatCivil.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testEtatCivil.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEtatCivil.getPrenomUsuel()).isEqualTo(UPDATED_PRENOM_USUEL);
        assertThat(testEtatCivil.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEtatCivil.getCommuneNaissance()).isEqualTo(UPDATED_COMMUNE_NAISSANCE);
        assertThat(testEtatCivil.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testEtatCivil.getCourriel()).isEqualTo(UPDATED_COURRIEL);
        assertThat(testEtatCivil.getCourriel2()).isEqualTo(UPDATED_COURRIEL_2);
        assertThat(testEtatCivil.getTelephonePortable()).isEqualTo(UPDATED_TELEPHONE_PORTABLE);
        assertThat(testEtatCivil.getTelephonePortable2()).isEqualTo(UPDATED_TELEPHONE_PORTABLE_2);
        assertThat(testEtatCivil.getTelephoneFixe()).isEqualTo(UPDATED_TELEPHONE_FIXE);
    }

    @Test
    @Transactional
    void patchNonExistingEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etatCivil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatCivil))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtatCivil() throws Exception {
        int databaseSizeBeforeUpdate = etatCivilRepository.findAll().size();
        etatCivil.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatCivilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etatCivil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatCivil in the database
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtatCivil() throws Exception {
        // Initialize the database
        etatCivilRepository.saveAndFlush(etatCivil);

        int databaseSizeBeforeDelete = etatCivilRepository.findAll().size();

        // Delete the etatCivil
        restEtatCivilMockMvc
            .perform(delete(ENTITY_API_URL_ID, etatCivil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatCivil> etatCivilList = etatCivilRepository.findAll();
        assertThat(etatCivilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.EtatCivil;
import fr.senat.irsen.repository.EtatCivilRepository;
import fr.senat.irsen.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.senat.irsen.domain.EtatCivil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtatCivilResource {

    private final Logger log = LoggerFactory.getLogger(EtatCivilResource.class);

    private static final String ENTITY_NAME = "etatCivil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatCivilRepository etatCivilRepository;

    public EtatCivilResource(EtatCivilRepository etatCivilRepository) {
        this.etatCivilRepository = etatCivilRepository;
    }

    /**
     * {@code POST  /etat-civils} : Create a new etatCivil.
     *
     * @param etatCivil the etatCivil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatCivil, or with status {@code 400 (Bad Request)} if the etatCivil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-civils")
    public ResponseEntity<EtatCivil> createEtatCivil(@RequestBody EtatCivil etatCivil) throws URISyntaxException {
        log.debug("REST request to save EtatCivil : {}", etatCivil);
        if (etatCivil.getId() != null) {
            throw new BadRequestAlertException("A new etatCivil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatCivil result = etatCivilRepository.save(etatCivil);
        return ResponseEntity
            .created(new URI("/api/etat-civils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-civils/:id} : Updates an existing etatCivil.
     *
     * @param id the id of the etatCivil to save.
     * @param etatCivil the etatCivil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatCivil,
     * or with status {@code 400 (Bad Request)} if the etatCivil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatCivil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-civils/{id}")
    public ResponseEntity<EtatCivil> updateEtatCivil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtatCivil etatCivil
    ) throws URISyntaxException {
        log.debug("REST request to update EtatCivil : {}, {}", id, etatCivil);
        if (etatCivil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatCivil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatCivilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtatCivil result = etatCivilRepository.save(etatCivil);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatCivil.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etat-civils/:id} : Partial updates given fields of an existing etatCivil, field will ignore if it is null
     *
     * @param id the id of the etatCivil to save.
     * @param etatCivil the etatCivil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatCivil,
     * or with status {@code 400 (Bad Request)} if the etatCivil is not valid,
     * or with status {@code 404 (Not Found)} if the etatCivil is not found,
     * or with status {@code 500 (Internal Server Error)} if the etatCivil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etat-civils/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtatCivil> partialUpdateEtatCivil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtatCivil etatCivil
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtatCivil partially : {}, {}", id, etatCivil);
        if (etatCivil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etatCivil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etatCivilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtatCivil> result = etatCivilRepository
            .findById(etatCivil.getId())
            .map(existingEtatCivil -> {
                if (etatCivil.getMatricule() != null) {
                    existingEtatCivil.setMatricule(etatCivil.getMatricule());
                }
                if (etatCivil.getCivilite() != null) {
                    existingEtatCivil.setCivilite(etatCivil.getCivilite());
                }
                if (etatCivil.getTitre() != null) {
                    existingEtatCivil.setTitre(etatCivil.getTitre());
                }
                if (etatCivil.getNomFamille() != null) {
                    existingEtatCivil.setNomFamille(etatCivil.getNomFamille());
                }
                if (etatCivil.getNomMarital() != null) {
                    existingEtatCivil.setNomMarital(etatCivil.getNomMarital());
                }
                if (etatCivil.getNomUsuel() != null) {
                    existingEtatCivil.setNomUsuel(etatCivil.getNomUsuel());
                }
                if (etatCivil.getPrenoms() != null) {
                    existingEtatCivil.setPrenoms(etatCivil.getPrenoms());
                }
                if (etatCivil.getPrenomUsuel() != null) {
                    existingEtatCivil.setPrenomUsuel(etatCivil.getPrenomUsuel());
                }
                if (etatCivil.getDateNaissance() != null) {
                    existingEtatCivil.setDateNaissance(etatCivil.getDateNaissance());
                }
                if (etatCivil.getCommuneNaissance() != null) {
                    existingEtatCivil.setCommuneNaissance(etatCivil.getCommuneNaissance());
                }
                if (etatCivil.getProfession() != null) {
                    existingEtatCivil.setProfession(etatCivil.getProfession());
                }
                if (etatCivil.getCourriel() != null) {
                    existingEtatCivil.setCourriel(etatCivil.getCourriel());
                }
                if (etatCivil.getCourriel2() != null) {
                    existingEtatCivil.setCourriel2(etatCivil.getCourriel2());
                }
                if (etatCivil.getTelephonePortable() != null) {
                    existingEtatCivil.setTelephonePortable(etatCivil.getTelephonePortable());
                }
                if (etatCivil.getTelephonePortable2() != null) {
                    existingEtatCivil.setTelephonePortable2(etatCivil.getTelephonePortable2());
                }
                if (etatCivil.getTelephoneFixe() != null) {
                    existingEtatCivil.setTelephoneFixe(etatCivil.getTelephoneFixe());
                }

                return existingEtatCivil;
            })
            .map(etatCivilRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatCivil.getId().toString())
        );
    }

    /**
     * {@code GET  /etat-civils} : get all the etatCivils.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatCivils in body.
     */
    @GetMapping("/etat-civils")
    public List<EtatCivil> getAllEtatCivils(@RequestParam(required = false) String filter) {
        if ("senateur-is-null".equals(filter)) {
            log.debug("REST request to get all EtatCivils where senateur is null");
            return StreamSupport
                .stream(etatCivilRepository.findAll().spliterator(), false)
                .filter(etatCivil -> etatCivil.getSenateur() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all EtatCivils");
        return etatCivilRepository.findAll();
    }

    /**
     * {@code GET  /etat-civils/:id} : get the "id" etatCivil.
     *
     * @param id the id of the etatCivil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatCivil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-civils/{id}")
    public ResponseEntity<EtatCivil> getEtatCivil(@PathVariable Long id) {
        log.debug("REST request to get EtatCivil : {}", id);
        Optional<EtatCivil> etatCivil = etatCivilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(etatCivil);
    }

    /**
     * {@code DELETE  /etat-civils/:id} : delete the "id" etatCivil.
     *
     * @param id the id of the etatCivil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-civils/{id}")
    public ResponseEntity<Void> deleteEtatCivil(@PathVariable Long id) {
        log.debug("REST request to delete EtatCivil : {}", id);
        etatCivilRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

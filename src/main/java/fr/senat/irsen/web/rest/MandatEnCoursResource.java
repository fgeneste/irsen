package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.MandatEnCours;
import fr.senat.irsen.repository.MandatEnCoursRepository;
import fr.senat.irsen.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.senat.irsen.domain.MandatEnCours}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MandatEnCoursResource {

    private final Logger log = LoggerFactory.getLogger(MandatEnCoursResource.class);

    private static final String ENTITY_NAME = "mandatEnCours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MandatEnCoursRepository mandatEnCoursRepository;

    public MandatEnCoursResource(MandatEnCoursRepository mandatEnCoursRepository) {
        this.mandatEnCoursRepository = mandatEnCoursRepository;
    }

    /**
     * {@code POST  /mandat-en-cours} : Create a new mandatEnCours.
     *
     * @param mandatEnCours the mandatEnCours to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mandatEnCours, or with status {@code 400 (Bad Request)} if the mandatEnCours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mandat-en-cours")
    public ResponseEntity<MandatEnCours> createMandatEnCours(@RequestBody MandatEnCours mandatEnCours) throws URISyntaxException {
        log.debug("REST request to save MandatEnCours : {}", mandatEnCours);
        if (mandatEnCours.getId() != null) {
            throw new BadRequestAlertException("A new mandatEnCours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MandatEnCours result = mandatEnCoursRepository.save(mandatEnCours);
        return ResponseEntity
            .created(new URI("/api/mandat-en-cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mandat-en-cours/:id} : Updates an existing mandatEnCours.
     *
     * @param id the id of the mandatEnCours to save.
     * @param mandatEnCours the mandatEnCours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandatEnCours,
     * or with status {@code 400 (Bad Request)} if the mandatEnCours is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mandatEnCours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mandat-en-cours/{id}")
    public ResponseEntity<MandatEnCours> updateMandatEnCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MandatEnCours mandatEnCours
    ) throws URISyntaxException {
        log.debug("REST request to update MandatEnCours : {}, {}", id, mandatEnCours);
        if (mandatEnCours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandatEnCours.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatEnCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MandatEnCours result = mandatEnCoursRepository.save(mandatEnCours);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandatEnCours.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mandat-en-cours/:id} : Partial updates given fields of an existing mandatEnCours, field will ignore if it is null
     *
     * @param id the id of the mandatEnCours to save.
     * @param mandatEnCours the mandatEnCours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandatEnCours,
     * or with status {@code 400 (Bad Request)} if the mandatEnCours is not valid,
     * or with status {@code 404 (Not Found)} if the mandatEnCours is not found,
     * or with status {@code 500 (Internal Server Error)} if the mandatEnCours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mandat-en-cours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MandatEnCours> partialUpdateMandatEnCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MandatEnCours mandatEnCours
    ) throws URISyntaxException {
        log.debug("REST request to partial update MandatEnCours partially : {}, {}", id, mandatEnCours);
        if (mandatEnCours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandatEnCours.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatEnCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MandatEnCours> result = mandatEnCoursRepository
            .findById(mandatEnCours.getId())
            .map(existingMandatEnCours -> {
                if (mandatEnCours.getIdType() != null) {
                    existingMandatEnCours.setIdType(mandatEnCours.getIdType());
                }
                if (mandatEnCours.getIdFonction() != null) {
                    existingMandatEnCours.setIdFonction(mandatEnCours.getIdFonction());
                }
                if (mandatEnCours.getCode() != null) {
                    existingMandatEnCours.setCode(mandatEnCours.getCode());
                }
                if (mandatEnCours.getLibelle() != null) {
                    existingMandatEnCours.setLibelle(mandatEnCours.getLibelle());
                }
                if (mandatEnCours.getLibelleFonction() != null) {
                    existingMandatEnCours.setLibelleFonction(mandatEnCours.getLibelleFonction());
                }
                if (mandatEnCours.getCirconscription() != null) {
                    existingMandatEnCours.setCirconscription(mandatEnCours.getCirconscription());
                }
                if (mandatEnCours.getDepuis() != null) {
                    existingMandatEnCours.setDepuis(mandatEnCours.getDepuis());
                }
                if (mandatEnCours.getDateElection() != null) {
                    existingMandatEnCours.setDateElection(mandatEnCours.getDateElection());
                }
                if (mandatEnCours.getPopulation() != null) {
                    existingMandatEnCours.setPopulation(mandatEnCours.getPopulation());
                }
                if (mandatEnCours.getLibelleAffichage() != null) {
                    existingMandatEnCours.setLibelleAffichage(mandatEnCours.getLibelleAffichage());
                }

                return existingMandatEnCours;
            })
            .map(mandatEnCoursRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandatEnCours.getId().toString())
        );
    }

    /**
     * {@code GET  /mandat-en-cours} : get all the mandatEnCours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mandatEnCours in body.
     */
    @GetMapping("/mandat-en-cours")
    public List<MandatEnCours> getAllMandatEnCours() {
        log.debug("REST request to get all MandatEnCours");
        return mandatEnCoursRepository.findAll();
    }

    /**
     * {@code GET  /mandat-en-cours/:id} : get the "id" mandatEnCours.
     *
     * @param id the id of the mandatEnCours to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mandatEnCours, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mandat-en-cours/{id}")
    public ResponseEntity<MandatEnCours> getMandatEnCours(@PathVariable Long id) {
        log.debug("REST request to get MandatEnCours : {}", id);
        Optional<MandatEnCours> mandatEnCours = mandatEnCoursRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mandatEnCours);
    }

    /**
     * {@code DELETE  /mandat-en-cours/:id} : delete the "id" mandatEnCours.
     *
     * @param id the id of the mandatEnCours to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mandat-en-cours/{id}")
    public ResponseEntity<Void> deleteMandatEnCours(@PathVariable Long id) {
        log.debug("REST request to delete MandatEnCours : {}", id);
        mandatEnCoursRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

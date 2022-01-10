package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.MandatAncien;
import fr.senat.irsen.repository.MandatAncienRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.MandatAncien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MandatAncienResource {

    private final Logger log = LoggerFactory.getLogger(MandatAncienResource.class);

    private static final String ENTITY_NAME = "mandatAncien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MandatAncienRepository mandatAncienRepository;

    public MandatAncienResource(MandatAncienRepository mandatAncienRepository) {
        this.mandatAncienRepository = mandatAncienRepository;
    }

    /**
     * {@code POST  /mandat-anciens} : Create a new mandatAncien.
     *
     * @param mandatAncien the mandatAncien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mandatAncien, or with status {@code 400 (Bad Request)} if the mandatAncien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mandat-anciens")
    public ResponseEntity<MandatAncien> createMandatAncien(@RequestBody MandatAncien mandatAncien) throws URISyntaxException {
        log.debug("REST request to save MandatAncien : {}", mandatAncien);
        if (mandatAncien.getId() != null) {
            throw new BadRequestAlertException("A new mandatAncien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MandatAncien result = mandatAncienRepository.save(mandatAncien);
        return ResponseEntity
            .created(new URI("/api/mandat-anciens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mandat-anciens/:id} : Updates an existing mandatAncien.
     *
     * @param id the id of the mandatAncien to save.
     * @param mandatAncien the mandatAncien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandatAncien,
     * or with status {@code 400 (Bad Request)} if the mandatAncien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mandatAncien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mandat-anciens/{id}")
    public ResponseEntity<MandatAncien> updateMandatAncien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MandatAncien mandatAncien
    ) throws URISyntaxException {
        log.debug("REST request to update MandatAncien : {}, {}", id, mandatAncien);
        if (mandatAncien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandatAncien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatAncienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MandatAncien result = mandatAncienRepository.save(mandatAncien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandatAncien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mandat-anciens/:id} : Partial updates given fields of an existing mandatAncien, field will ignore if it is null
     *
     * @param id the id of the mandatAncien to save.
     * @param mandatAncien the mandatAncien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandatAncien,
     * or with status {@code 400 (Bad Request)} if the mandatAncien is not valid,
     * or with status {@code 404 (Not Found)} if the mandatAncien is not found,
     * or with status {@code 500 (Internal Server Error)} if the mandatAncien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mandat-anciens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MandatAncien> partialUpdateMandatAncien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MandatAncien mandatAncien
    ) throws URISyntaxException {
        log.debug("REST request to partial update MandatAncien partially : {}, {}", id, mandatAncien);
        if (mandatAncien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandatAncien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatAncienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MandatAncien> result = mandatAncienRepository
            .findById(mandatAncien.getId())
            .map(existingMandatAncien -> {
                if (mandatAncien.getIdType() != null) {
                    existingMandatAncien.setIdType(mandatAncien.getIdType());
                }
                if (mandatAncien.getLibelle() != null) {
                    existingMandatAncien.setLibelle(mandatAncien.getLibelle());
                }
                if (mandatAncien.getDateDebut() != null) {
                    existingMandatAncien.setDateDebut(mandatAncien.getDateDebut());
                }
                if (mandatAncien.getDateFin() != null) {
                    existingMandatAncien.setDateFin(mandatAncien.getDateFin());
                }
                if (mandatAncien.getCirconscription() != null) {
                    existingMandatAncien.setCirconscription(mandatAncien.getCirconscription());
                }
                if (mandatAncien.getLibelleAffichage() != null) {
                    existingMandatAncien.setLibelleAffichage(mandatAncien.getLibelleAffichage());
                }

                return existingMandatAncien;
            })
            .map(mandatAncienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandatAncien.getId().toString())
        );
    }

    /**
     * {@code GET  /mandat-anciens} : get all the mandatAnciens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mandatAnciens in body.
     */
    @GetMapping("/mandat-anciens")
    public List<MandatAncien> getAllMandatAnciens() {
        log.debug("REST request to get all MandatAnciens");
        return mandatAncienRepository.findAll();
    }

    /**
     * {@code GET  /mandat-anciens/:id} : get the "id" mandatAncien.
     *
     * @param id the id of the mandatAncien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mandatAncien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mandat-anciens/{id}")
    public ResponseEntity<MandatAncien> getMandatAncien(@PathVariable Long id) {
        log.debug("REST request to get MandatAncien : {}", id);
        Optional<MandatAncien> mandatAncien = mandatAncienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mandatAncien);
    }

    /**
     * {@code DELETE  /mandat-anciens/:id} : delete the "id" mandatAncien.
     *
     * @param id the id of the mandatAncien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mandat-anciens/{id}")
    public ResponseEntity<Void> deleteMandatAncien(@PathVariable Long id) {
        log.debug("REST request to delete MandatAncien : {}", id);
        mandatAncienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

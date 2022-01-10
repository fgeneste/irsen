package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.FonctionEnCours;
import fr.senat.irsen.repository.FonctionEnCoursRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.FonctionEnCours}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FonctionEnCoursResource {

    private final Logger log = LoggerFactory.getLogger(FonctionEnCoursResource.class);

    private static final String ENTITY_NAME = "fonctionEnCours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FonctionEnCoursRepository fonctionEnCoursRepository;

    public FonctionEnCoursResource(FonctionEnCoursRepository fonctionEnCoursRepository) {
        this.fonctionEnCoursRepository = fonctionEnCoursRepository;
    }

    /**
     * {@code POST  /fonction-en-cours} : Create a new fonctionEnCours.
     *
     * @param fonctionEnCours the fonctionEnCours to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fonctionEnCours, or with status {@code 400 (Bad Request)} if the fonctionEnCours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fonction-en-cours")
    public ResponseEntity<FonctionEnCours> createFonctionEnCours(@RequestBody FonctionEnCours fonctionEnCours) throws URISyntaxException {
        log.debug("REST request to save FonctionEnCours : {}", fonctionEnCours);
        if (fonctionEnCours.getId() != null) {
            throw new BadRequestAlertException("A new fonctionEnCours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FonctionEnCours result = fonctionEnCoursRepository.save(fonctionEnCours);
        return ResponseEntity
            .created(new URI("/api/fonction-en-cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fonction-en-cours/:id} : Updates an existing fonctionEnCours.
     *
     * @param id the id of the fonctionEnCours to save.
     * @param fonctionEnCours the fonctionEnCours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonctionEnCours,
     * or with status {@code 400 (Bad Request)} if the fonctionEnCours is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fonctionEnCours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fonction-en-cours/{id}")
    public ResponseEntity<FonctionEnCours> updateFonctionEnCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FonctionEnCours fonctionEnCours
    ) throws URISyntaxException {
        log.debug("REST request to update FonctionEnCours : {}, {}", id, fonctionEnCours);
        if (fonctionEnCours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fonctionEnCours.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fonctionEnCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FonctionEnCours result = fonctionEnCoursRepository.save(fonctionEnCours);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonctionEnCours.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fonction-en-cours/:id} : Partial updates given fields of an existing fonctionEnCours, field will ignore if it is null
     *
     * @param id the id of the fonctionEnCours to save.
     * @param fonctionEnCours the fonctionEnCours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonctionEnCours,
     * or with status {@code 400 (Bad Request)} if the fonctionEnCours is not valid,
     * or with status {@code 404 (Not Found)} if the fonctionEnCours is not found,
     * or with status {@code 500 (Internal Server Error)} if the fonctionEnCours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fonction-en-cours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FonctionEnCours> partialUpdateFonctionEnCours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FonctionEnCours fonctionEnCours
    ) throws URISyntaxException {
        log.debug("REST request to partial update FonctionEnCours partially : {}, {}", id, fonctionEnCours);
        if (fonctionEnCours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fonctionEnCours.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fonctionEnCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FonctionEnCours> result = fonctionEnCoursRepository
            .findById(fonctionEnCours.getId())
            .map(existingFonctionEnCours -> {
                if (fonctionEnCours.getLibelle() != null) {
                    existingFonctionEnCours.setLibelle(fonctionEnCours.getLibelle());
                }
                if (fonctionEnCours.getDateDebut() != null) {
                    existingFonctionEnCours.setDateDebut(fonctionEnCours.getDateDebut());
                }
                if (fonctionEnCours.getDateFin() != null) {
                    existingFonctionEnCours.setDateFin(fonctionEnCours.getDateFin());
                }

                return existingFonctionEnCours;
            })
            .map(fonctionEnCoursRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonctionEnCours.getId().toString())
        );
    }

    /**
     * {@code GET  /fonction-en-cours} : get all the fonctionEnCours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fonctionEnCours in body.
     */
    @GetMapping("/fonction-en-cours")
    public List<FonctionEnCours> getAllFonctionEnCours() {
        log.debug("REST request to get all FonctionEnCours");
        return fonctionEnCoursRepository.findAll();
    }

    /**
     * {@code GET  /fonction-en-cours/:id} : get the "id" fonctionEnCours.
     *
     * @param id the id of the fonctionEnCours to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fonctionEnCours, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fonction-en-cours/{id}")
    public ResponseEntity<FonctionEnCours> getFonctionEnCours(@PathVariable Long id) {
        log.debug("REST request to get FonctionEnCours : {}", id);
        Optional<FonctionEnCours> fonctionEnCours = fonctionEnCoursRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fonctionEnCours);
    }

    /**
     * {@code DELETE  /fonction-en-cours/:id} : delete the "id" fonctionEnCours.
     *
     * @param id the id of the fonctionEnCours to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fonction-en-cours/{id}")
    public ResponseEntity<Void> deleteFonctionEnCours(@PathVariable Long id) {
        log.debug("REST request to delete FonctionEnCours : {}", id);
        fonctionEnCoursRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

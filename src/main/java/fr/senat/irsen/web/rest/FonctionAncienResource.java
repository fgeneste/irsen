package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.FonctionAncien;
import fr.senat.irsen.repository.FonctionAncienRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.FonctionAncien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FonctionAncienResource {

    private final Logger log = LoggerFactory.getLogger(FonctionAncienResource.class);

    private static final String ENTITY_NAME = "fonctionAncien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FonctionAncienRepository fonctionAncienRepository;

    public FonctionAncienResource(FonctionAncienRepository fonctionAncienRepository) {
        this.fonctionAncienRepository = fonctionAncienRepository;
    }

    /**
     * {@code POST  /fonction-anciens} : Create a new fonctionAncien.
     *
     * @param fonctionAncien the fonctionAncien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fonctionAncien, or with status {@code 400 (Bad Request)} if the fonctionAncien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fonction-anciens")
    public ResponseEntity<FonctionAncien> createFonctionAncien(@RequestBody FonctionAncien fonctionAncien) throws URISyntaxException {
        log.debug("REST request to save FonctionAncien : {}", fonctionAncien);
        if (fonctionAncien.getId() != null) {
            throw new BadRequestAlertException("A new fonctionAncien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FonctionAncien result = fonctionAncienRepository.save(fonctionAncien);
        return ResponseEntity
            .created(new URI("/api/fonction-anciens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fonction-anciens/:id} : Updates an existing fonctionAncien.
     *
     * @param id the id of the fonctionAncien to save.
     * @param fonctionAncien the fonctionAncien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonctionAncien,
     * or with status {@code 400 (Bad Request)} if the fonctionAncien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fonctionAncien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fonction-anciens/{id}")
    public ResponseEntity<FonctionAncien> updateFonctionAncien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FonctionAncien fonctionAncien
    ) throws URISyntaxException {
        log.debug("REST request to update FonctionAncien : {}, {}", id, fonctionAncien);
        if (fonctionAncien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fonctionAncien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fonctionAncienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FonctionAncien result = fonctionAncienRepository.save(fonctionAncien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonctionAncien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fonction-anciens/:id} : Partial updates given fields of an existing fonctionAncien, field will ignore if it is null
     *
     * @param id the id of the fonctionAncien to save.
     * @param fonctionAncien the fonctionAncien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonctionAncien,
     * or with status {@code 400 (Bad Request)} if the fonctionAncien is not valid,
     * or with status {@code 404 (Not Found)} if the fonctionAncien is not found,
     * or with status {@code 500 (Internal Server Error)} if the fonctionAncien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fonction-anciens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FonctionAncien> partialUpdateFonctionAncien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FonctionAncien fonctionAncien
    ) throws URISyntaxException {
        log.debug("REST request to partial update FonctionAncien partially : {}, {}", id, fonctionAncien);
        if (fonctionAncien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fonctionAncien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fonctionAncienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FonctionAncien> result = fonctionAncienRepository
            .findById(fonctionAncien.getId())
            .map(existingFonctionAncien -> {
                if (fonctionAncien.getLibelle() != null) {
                    existingFonctionAncien.setLibelle(fonctionAncien.getLibelle());
                }
                if (fonctionAncien.getDateDebut() != null) {
                    existingFonctionAncien.setDateDebut(fonctionAncien.getDateDebut());
                }
                if (fonctionAncien.getDateFin() != null) {
                    existingFonctionAncien.setDateFin(fonctionAncien.getDateFin());
                }

                return existingFonctionAncien;
            })
            .map(fonctionAncienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonctionAncien.getId().toString())
        );
    }

    /**
     * {@code GET  /fonction-anciens} : get all the fonctionAnciens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fonctionAnciens in body.
     */
    @GetMapping("/fonction-anciens")
    public List<FonctionAncien> getAllFonctionAnciens() {
        log.debug("REST request to get all FonctionAnciens");
        return fonctionAncienRepository.findAll();
    }

    /**
     * {@code GET  /fonction-anciens/:id} : get the "id" fonctionAncien.
     *
     * @param id the id of the fonctionAncien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fonctionAncien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fonction-anciens/{id}")
    public ResponseEntity<FonctionAncien> getFonctionAncien(@PathVariable Long id) {
        log.debug("REST request to get FonctionAncien : {}", id);
        Optional<FonctionAncien> fonctionAncien = fonctionAncienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fonctionAncien);
    }

    /**
     * {@code DELETE  /fonction-anciens/:id} : delete the "id" fonctionAncien.
     *
     * @param id the id of the fonctionAncien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fonction-anciens/{id}")
    public ResponseEntity<Void> deleteFonctionAncien(@PathVariable Long id) {
        log.debug("REST request to delete FonctionAncien : {}", id);
        fonctionAncienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

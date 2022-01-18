package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.Decoration;
import fr.senat.irsen.repository.DecorationRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.Decoration}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DecorationResource {

    private final Logger log = LoggerFactory.getLogger(DecorationResource.class);

    private static final String ENTITY_NAME = "decoration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecorationRepository decorationRepository;

    public DecorationResource(DecorationRepository decorationRepository) {
        this.decorationRepository = decorationRepository;
    }

    /**
     * {@code POST  /decorations} : Create a new decoration.
     *
     * @param decoration the decoration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decoration, or with status {@code 400 (Bad Request)} if the decoration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decorations")
    public ResponseEntity<Decoration> createDecoration(@RequestBody Decoration decoration) throws URISyntaxException {
        log.debug("REST request to save Decoration : {}", decoration);
        if (decoration.getId() != null) {
            throw new BadRequestAlertException("A new decoration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Decoration result = decorationRepository.save(decoration);
        return ResponseEntity
            .created(new URI("/api/decorations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decorations/:id} : Updates an existing decoration.
     *
     * @param id the id of the decoration to save.
     * @param decoration the decoration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decoration,
     * or with status {@code 400 (Bad Request)} if the decoration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decoration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decorations/{id}")
    public ResponseEntity<Decoration> updateDecoration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Decoration decoration
    ) throws URISyntaxException {
        log.debug("REST request to update Decoration : {}, {}", id, decoration);
        if (decoration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decoration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decorationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Decoration result = decorationRepository.save(decoration);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, decoration.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /decorations/:id} : Partial updates given fields of an existing decoration, field will ignore if it is null
     *
     * @param id the id of the decoration to save.
     * @param decoration the decoration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decoration,
     * or with status {@code 400 (Bad Request)} if the decoration is not valid,
     * or with status {@code 404 (Not Found)} if the decoration is not found,
     * or with status {@code 500 (Internal Server Error)} if the decoration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/decorations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Decoration> partialUpdateDecoration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Decoration decoration
    ) throws URISyntaxException {
        log.debug("REST request to partial update Decoration partially : {}, {}", id, decoration);
        if (decoration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decoration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decorationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Decoration> result = decorationRepository
            .findById(decoration.getId())
            .map(existingDecoration -> {
                if (decoration.getType() != null) {
                    existingDecoration.setType(decoration.getType());
                }
                if (decoration.getGrade() != null) {
                    existingDecoration.setGrade(decoration.getGrade());
                }

                return existingDecoration;
            })
            .map(decorationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, decoration.getId().toString())
        );
    }

    /**
     * {@code GET  /decorations} : get all the decorations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decorations in body.
     */
    @GetMapping("/decorations")
    public List<Decoration> getAllDecorations() {
        log.debug("REST request to get all Decorations");
        return decorationRepository.findAll();
    }

    /**
     * {@code GET  /decorations/:id} : get the "id" decoration.
     *
     * @param id the id of the decoration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decoration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decorations/{id}")
    public ResponseEntity<Decoration> getDecoration(@PathVariable Long id) {
        log.debug("REST request to get Decoration : {}", id);
        Optional<Decoration> decoration = decorationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(decoration);
    }

    /**
     * {@code DELETE  /decorations/:id} : delete the "id" decoration.
     *
     * @param id the id of the decoration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decorations/{id}")
    public ResponseEntity<Void> deleteDecoration(@PathVariable Long id) {
        log.debug("REST request to delete Decoration : {}", id);
        decorationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

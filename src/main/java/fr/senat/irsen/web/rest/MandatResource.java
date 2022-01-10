package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.Mandat;
import fr.senat.irsen.repository.MandatRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.Mandat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MandatResource {

    private final Logger log = LoggerFactory.getLogger(MandatResource.class);

    private static final String ENTITY_NAME = "mandat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MandatRepository mandatRepository;

    public MandatResource(MandatRepository mandatRepository) {
        this.mandatRepository = mandatRepository;
    }

    /**
     * {@code POST  /mandats} : Create a new mandat.
     *
     * @param mandat the mandat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mandat, or with status {@code 400 (Bad Request)} if the mandat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mandats")
    public ResponseEntity<Mandat> createMandat(@RequestBody Mandat mandat) throws URISyntaxException {
        log.debug("REST request to save Mandat : {}", mandat);
        if (mandat.getId() != null) {
            throw new BadRequestAlertException("A new mandat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mandat result = mandatRepository.save(mandat);
        return ResponseEntity
            .created(new URI("/api/mandats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mandats/:id} : Updates an existing mandat.
     *
     * @param id the id of the mandat to save.
     * @param mandat the mandat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandat,
     * or with status {@code 400 (Bad Request)} if the mandat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mandat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mandats/{id}")
    public ResponseEntity<Mandat> updateMandat(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mandat mandat)
        throws URISyntaxException {
        log.debug("REST request to update Mandat : {}, {}", id, mandat);
        if (mandat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mandat result = mandatRepository.save(mandat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mandats/:id} : Partial updates given fields of an existing mandat, field will ignore if it is null
     *
     * @param id the id of the mandat to save.
     * @param mandat the mandat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandat,
     * or with status {@code 400 (Bad Request)} if the mandat is not valid,
     * or with status {@code 404 (Not Found)} if the mandat is not found,
     * or with status {@code 500 (Internal Server Error)} if the mandat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mandats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mandat> partialUpdateMandat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mandat mandat
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mandat partially : {}, {}", id, mandat);
        if (mandat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mandat> result = mandatRepository
            .findById(mandat.getId())
            .map(existingMandat -> {
                return existingMandat;
            })
            .map(mandatRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandat.getId().toString())
        );
    }

    /**
     * {@code GET  /mandats} : get all the mandats.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mandats in body.
     */
    @GetMapping("/mandats")
    public List<Mandat> getAllMandats(@RequestParam(required = false) String filter) {
        if ("senateur-is-null".equals(filter)) {
            log.debug("REST request to get all Mandats where senateur is null");
            return StreamSupport
                .stream(mandatRepository.findAll().spliterator(), false)
                .filter(mandat -> mandat.getSenateur() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Mandats");
        return mandatRepository.findAll();
    }

    /**
     * {@code GET  /mandats/:id} : get the "id" mandat.
     *
     * @param id the id of the mandat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mandat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mandats/{id}")
    public ResponseEntity<Mandat> getMandat(@PathVariable Long id) {
        log.debug("REST request to get Mandat : {}", id);
        Optional<Mandat> mandat = mandatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mandat);
    }

    /**
     * {@code DELETE  /mandats/:id} : delete the "id" mandat.
     *
     * @param id the id of the mandat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mandats/{id}")
    public ResponseEntity<Void> deleteMandat(@PathVariable Long id) {
        log.debug("REST request to delete Mandat : {}", id);
        mandatRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

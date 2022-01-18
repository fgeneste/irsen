package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.TelephoneFixe;
import fr.senat.irsen.repository.TelephoneFixeRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.TelephoneFixe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TelephoneFixeResource {

    private final Logger log = LoggerFactory.getLogger(TelephoneFixeResource.class);

    private static final String ENTITY_NAME = "telephoneFixe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelephoneFixeRepository telephoneFixeRepository;

    public TelephoneFixeResource(TelephoneFixeRepository telephoneFixeRepository) {
        this.telephoneFixeRepository = telephoneFixeRepository;
    }

    /**
     * {@code POST  /telephone-fixes} : Create a new telephoneFixe.
     *
     * @param telephoneFixe the telephoneFixe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telephoneFixe, or with status {@code 400 (Bad Request)} if the telephoneFixe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telephone-fixes")
    public ResponseEntity<TelephoneFixe> createTelephoneFixe(@RequestBody TelephoneFixe telephoneFixe) throws URISyntaxException {
        log.debug("REST request to save TelephoneFixe : {}", telephoneFixe);
        if (telephoneFixe.getId() != null) {
            throw new BadRequestAlertException("A new telephoneFixe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelephoneFixe result = telephoneFixeRepository.save(telephoneFixe);
        return ResponseEntity
            .created(new URI("/api/telephone-fixes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telephone-fixes/:id} : Updates an existing telephoneFixe.
     *
     * @param id the id of the telephoneFixe to save.
     * @param telephoneFixe the telephoneFixe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephoneFixe,
     * or with status {@code 400 (Bad Request)} if the telephoneFixe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telephoneFixe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telephone-fixes/{id}")
    public ResponseEntity<TelephoneFixe> updateTelephoneFixe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephoneFixe telephoneFixe
    ) throws URISyntaxException {
        log.debug("REST request to update TelephoneFixe : {}, {}", id, telephoneFixe);
        if (telephoneFixe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephoneFixe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephoneFixeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelephoneFixe result = telephoneFixeRepository.save(telephoneFixe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephoneFixe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telephone-fixes/:id} : Partial updates given fields of an existing telephoneFixe, field will ignore if it is null
     *
     * @param id the id of the telephoneFixe to save.
     * @param telephoneFixe the telephoneFixe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephoneFixe,
     * or with status {@code 400 (Bad Request)} if the telephoneFixe is not valid,
     * or with status {@code 404 (Not Found)} if the telephoneFixe is not found,
     * or with status {@code 500 (Internal Server Error)} if the telephoneFixe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telephone-fixes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelephoneFixe> partialUpdateTelephoneFixe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephoneFixe telephoneFixe
    ) throws URISyntaxException {
        log.debug("REST request to partial update TelephoneFixe partially : {}, {}", id, telephoneFixe);
        if (telephoneFixe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephoneFixe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephoneFixeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelephoneFixe> result = telephoneFixeRepository
            .findById(telephoneFixe.getId())
            .map(existingTelephoneFixe -> {
                if (telephoneFixe.getType() != null) {
                    existingTelephoneFixe.setType(telephoneFixe.getType());
                }
                if (telephoneFixe.getNumero() != null) {
                    existingTelephoneFixe.setNumero(telephoneFixe.getNumero());
                }

                return existingTelephoneFixe;
            })
            .map(telephoneFixeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephoneFixe.getId().toString())
        );
    }

    /**
     * {@code GET  /telephone-fixes} : get all the telephoneFixes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telephoneFixes in body.
     */
    @GetMapping("/telephone-fixes")
    public List<TelephoneFixe> getAllTelephoneFixes(@RequestParam(required = false) String filter) {
        if ("etatcivil-is-null".equals(filter)) {
            log.debug("REST request to get all TelephoneFixes where etatCivil is null");
            return StreamSupport
                .stream(telephoneFixeRepository.findAll().spliterator(), false)
                .filter(telephoneFixe -> telephoneFixe.getEtatCivil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TelephoneFixes");
        return telephoneFixeRepository.findAll();
    }

    /**
     * {@code GET  /telephone-fixes/:id} : get the "id" telephoneFixe.
     *
     * @param id the id of the telephoneFixe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telephoneFixe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telephone-fixes/{id}")
    public ResponseEntity<TelephoneFixe> getTelephoneFixe(@PathVariable Long id) {
        log.debug("REST request to get TelephoneFixe : {}", id);
        Optional<TelephoneFixe> telephoneFixe = telephoneFixeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telephoneFixe);
    }

    /**
     * {@code DELETE  /telephone-fixes/:id} : delete the "id" telephoneFixe.
     *
     * @param id the id of the telephoneFixe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telephone-fixes/{id}")
    public ResponseEntity<Void> deleteTelephoneFixe(@PathVariable Long id) {
        log.debug("REST request to delete TelephoneFixe : {}", id);
        telephoneFixeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

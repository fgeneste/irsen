package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.TelephonePortable;
import fr.senat.irsen.repository.TelephonePortableRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.TelephonePortable}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TelephonePortableResource {

    private final Logger log = LoggerFactory.getLogger(TelephonePortableResource.class);

    private static final String ENTITY_NAME = "telephonePortable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelephonePortableRepository telephonePortableRepository;

    public TelephonePortableResource(TelephonePortableRepository telephonePortableRepository) {
        this.telephonePortableRepository = telephonePortableRepository;
    }

    /**
     * {@code POST  /telephone-portables} : Create a new telephonePortable.
     *
     * @param telephonePortable the telephonePortable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telephonePortable, or with status {@code 400 (Bad Request)} if the telephonePortable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telephone-portables")
    public ResponseEntity<TelephonePortable> createTelephonePortable(@RequestBody TelephonePortable telephonePortable)
        throws URISyntaxException {
        log.debug("REST request to save TelephonePortable : {}", telephonePortable);
        if (telephonePortable.getId() != null) {
            throw new BadRequestAlertException("A new telephonePortable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelephonePortable result = telephonePortableRepository.save(telephonePortable);
        return ResponseEntity
            .created(new URI("/api/telephone-portables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telephone-portables/:id} : Updates an existing telephonePortable.
     *
     * @param id the id of the telephonePortable to save.
     * @param telephonePortable the telephonePortable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephonePortable,
     * or with status {@code 400 (Bad Request)} if the telephonePortable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telephonePortable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telephone-portables/{id}")
    public ResponseEntity<TelephonePortable> updateTelephonePortable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephonePortable telephonePortable
    ) throws URISyntaxException {
        log.debug("REST request to update TelephonePortable : {}, {}", id, telephonePortable);
        if (telephonePortable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephonePortable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephonePortableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelephonePortable result = telephonePortableRepository.save(telephonePortable);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephonePortable.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telephone-portables/:id} : Partial updates given fields of an existing telephonePortable, field will ignore if it is null
     *
     * @param id the id of the telephonePortable to save.
     * @param telephonePortable the telephonePortable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephonePortable,
     * or with status {@code 400 (Bad Request)} if the telephonePortable is not valid,
     * or with status {@code 404 (Not Found)} if the telephonePortable is not found,
     * or with status {@code 500 (Internal Server Error)} if the telephonePortable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telephone-portables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelephonePortable> partialUpdateTelephonePortable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephonePortable telephonePortable
    ) throws URISyntaxException {
        log.debug("REST request to partial update TelephonePortable partially : {}, {}", id, telephonePortable);
        if (telephonePortable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephonePortable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephonePortableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelephonePortable> result = telephonePortableRepository
            .findById(telephonePortable.getId())
            .map(existingTelephonePortable -> {
                if (telephonePortable.getType() != null) {
                    existingTelephonePortable.setType(telephonePortable.getType());
                }
                if (telephonePortable.getNumero() != null) {
                    existingTelephonePortable.setNumero(telephonePortable.getNumero());
                }

                return existingTelephonePortable;
            })
            .map(telephonePortableRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephonePortable.getId().toString())
        );
    }

    /**
     * {@code GET  /telephone-portables} : get all the telephonePortables.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telephonePortables in body.
     */
    @GetMapping("/telephone-portables")
    public List<TelephonePortable> getAllTelephonePortables(@RequestParam(required = false) String filter) {
        if ("etatcivil-is-null".equals(filter)) {
            log.debug("REST request to get all TelephonePortables where etatCivil is null");
            return StreamSupport
                .stream(telephonePortableRepository.findAll().spliterator(), false)
                .filter(telephonePortable -> telephonePortable.getEtatCivil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TelephonePortables");
        return telephonePortableRepository.findAll();
    }

    /**
     * {@code GET  /telephone-portables/:id} : get the "id" telephonePortable.
     *
     * @param id the id of the telephonePortable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telephonePortable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telephone-portables/{id}")
    public ResponseEntity<TelephonePortable> getTelephonePortable(@PathVariable Long id) {
        log.debug("REST request to get TelephonePortable : {}", id);
        Optional<TelephonePortable> telephonePortable = telephonePortableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telephonePortable);
    }

    /**
     * {@code DELETE  /telephone-portables/:id} : delete the "id" telephonePortable.
     *
     * @param id the id of the telephonePortable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telephone-portables/{id}")
    public ResponseEntity<Void> deleteTelephonePortable(@PathVariable Long id) {
        log.debug("REST request to delete TelephonePortable : {}", id);
        telephonePortableRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

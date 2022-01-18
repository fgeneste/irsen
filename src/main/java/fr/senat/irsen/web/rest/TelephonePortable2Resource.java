package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.TelephonePortable2;
import fr.senat.irsen.repository.TelephonePortable2Repository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.TelephonePortable2}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TelephonePortable2Resource {

    private final Logger log = LoggerFactory.getLogger(TelephonePortable2Resource.class);

    private static final String ENTITY_NAME = "telephonePortable2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelephonePortable2Repository telephonePortable2Repository;

    public TelephonePortable2Resource(TelephonePortable2Repository telephonePortable2Repository) {
        this.telephonePortable2Repository = telephonePortable2Repository;
    }

    /**
     * {@code POST  /telephone-portable-2-s} : Create a new telephonePortable2.
     *
     * @param telephonePortable2 the telephonePortable2 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telephonePortable2, or with status {@code 400 (Bad Request)} if the telephonePortable2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telephone-portable-2-s")
    public ResponseEntity<TelephonePortable2> createTelephonePortable2(@RequestBody TelephonePortable2 telephonePortable2)
        throws URISyntaxException {
        log.debug("REST request to save TelephonePortable2 : {}", telephonePortable2);
        if (telephonePortable2.getId() != null) {
            throw new BadRequestAlertException("A new telephonePortable2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelephonePortable2 result = telephonePortable2Repository.save(telephonePortable2);
        return ResponseEntity
            .created(new URI("/api/telephone-portable-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telephone-portable-2-s/:id} : Updates an existing telephonePortable2.
     *
     * @param id the id of the telephonePortable2 to save.
     * @param telephonePortable2 the telephonePortable2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephonePortable2,
     * or with status {@code 400 (Bad Request)} if the telephonePortable2 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telephonePortable2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telephone-portable-2-s/{id}")
    public ResponseEntity<TelephonePortable2> updateTelephonePortable2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephonePortable2 telephonePortable2
    ) throws URISyntaxException {
        log.debug("REST request to update TelephonePortable2 : {}, {}", id, telephonePortable2);
        if (telephonePortable2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephonePortable2.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephonePortable2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelephonePortable2 result = telephonePortable2Repository.save(telephonePortable2);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephonePortable2.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telephone-portable-2-s/:id} : Partial updates given fields of an existing telephonePortable2, field will ignore if it is null
     *
     * @param id the id of the telephonePortable2 to save.
     * @param telephonePortable2 the telephonePortable2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephonePortable2,
     * or with status {@code 400 (Bad Request)} if the telephonePortable2 is not valid,
     * or with status {@code 404 (Not Found)} if the telephonePortable2 is not found,
     * or with status {@code 500 (Internal Server Error)} if the telephonePortable2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telephone-portable-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelephonePortable2> partialUpdateTelephonePortable2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelephonePortable2 telephonePortable2
    ) throws URISyntaxException {
        log.debug("REST request to partial update TelephonePortable2 partially : {}, {}", id, telephonePortable2);
        if (telephonePortable2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telephonePortable2.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telephonePortable2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelephonePortable2> result = telephonePortable2Repository
            .findById(telephonePortable2.getId())
            .map(existingTelephonePortable2 -> {
                if (telephonePortable2.getType() != null) {
                    existingTelephonePortable2.setType(telephonePortable2.getType());
                }
                if (telephonePortable2.getNumero() != null) {
                    existingTelephonePortable2.setNumero(telephonePortable2.getNumero());
                }

                return existingTelephonePortable2;
            })
            .map(telephonePortable2Repository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephonePortable2.getId().toString())
        );
    }

    /**
     * {@code GET  /telephone-portable-2-s} : get all the telephonePortable2s.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telephonePortable2s in body.
     */
    @GetMapping("/telephone-portable-2-s")
    public List<TelephonePortable2> getAllTelephonePortable2s(@RequestParam(required = false) String filter) {
        if ("etatcivil-is-null".equals(filter)) {
            log.debug("REST request to get all TelephonePortable2s where etatCivil is null");
            return StreamSupport
                .stream(telephonePortable2Repository.findAll().spliterator(), false)
                .filter(telephonePortable2 -> telephonePortable2.getEtatCivil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TelephonePortable2s");
        return telephonePortable2Repository.findAll();
    }

    /**
     * {@code GET  /telephone-portable-2-s/:id} : get the "id" telephonePortable2.
     *
     * @param id the id of the telephonePortable2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telephonePortable2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telephone-portable-2-s/{id}")
    public ResponseEntity<TelephonePortable2> getTelephonePortable2(@PathVariable Long id) {
        log.debug("REST request to get TelephonePortable2 : {}", id);
        Optional<TelephonePortable2> telephonePortable2 = telephonePortable2Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(telephonePortable2);
    }

    /**
     * {@code DELETE  /telephone-portable-2-s/:id} : delete the "id" telephonePortable2.
     *
     * @param id the id of the telephonePortable2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telephone-portable-2-s/{id}")
    public ResponseEntity<Void> deleteTelephonePortable2(@PathVariable Long id) {
        log.debug("REST request to delete TelephonePortable2 : {}", id);
        telephonePortable2Repository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

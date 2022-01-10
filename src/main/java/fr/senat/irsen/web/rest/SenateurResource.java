package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.Senateur;
import fr.senat.irsen.repository.SenateurRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.Senateur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SenateurResource {

    private final Logger log = LoggerFactory.getLogger(SenateurResource.class);

    private static final String ENTITY_NAME = "senateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SenateurRepository senateurRepository;

    public SenateurResource(SenateurRepository senateurRepository) {
        this.senateurRepository = senateurRepository;
    }

    /**
     * {@code POST  /senateurs} : Create a new senateur.
     *
     * @param senateur the senateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new senateur, or with status {@code 400 (Bad Request)} if the senateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/senateurs")
    public ResponseEntity<Senateur> createSenateur(@RequestBody Senateur senateur) throws URISyntaxException {
        log.debug("REST request to save Senateur : {}", senateur);
        if (senateur.getId() != null) {
            throw new BadRequestAlertException("A new senateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Senateur result = senateurRepository.save(senateur);
        return ResponseEntity
            .created(new URI("/api/senateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /senateurs/:id} : Updates an existing senateur.
     *
     * @param id the id of the senateur to save.
     * @param senateur the senateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated senateur,
     * or with status {@code 400 (Bad Request)} if the senateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the senateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/senateurs/{id}")
    public ResponseEntity<Senateur> updateSenateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Senateur senateur
    ) throws URISyntaxException {
        log.debug("REST request to update Senateur : {}, {}", id, senateur);
        if (senateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, senateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!senateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Senateur result = senateurRepository.save(senateur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, senateur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /senateurs/:id} : Partial updates given fields of an existing senateur, field will ignore if it is null
     *
     * @param id the id of the senateur to save.
     * @param senateur the senateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated senateur,
     * or with status {@code 400 (Bad Request)} if the senateur is not valid,
     * or with status {@code 404 (Not Found)} if the senateur is not found,
     * or with status {@code 500 (Internal Server Error)} if the senateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/senateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Senateur> partialUpdateSenateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Senateur senateur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Senateur partially : {}, {}", id, senateur);
        if (senateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, senateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!senateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Senateur> result = senateurRepository
            .findById(senateur.getId())
            .map(existingSenateur -> {
                return existingSenateur;
            })
            .map(senateurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, senateur.getId().toString())
        );
    }

    /**
     * {@code GET  /senateurs} : get all the senateurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of senateurs in body.
     */
    @GetMapping("/senateurs")
    public List<Senateur> getAllSenateurs() {
        log.debug("REST request to get all Senateurs");
        return senateurRepository.findAll();
    }

    /**
     * {@code GET  /senateurs/:id} : get the "id" senateur.
     *
     * @param id the id of the senateur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the senateur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/senateurs/{id}")
    public ResponseEntity<Senateur> getSenateur(@PathVariable Long id) {
        log.debug("REST request to get Senateur : {}", id);
        Optional<Senateur> senateur = senateurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(senateur);
    }

    /**
     * {@code DELETE  /senateurs/:id} : delete the "id" senateur.
     *
     * @param id the id of the senateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/senateurs/{id}")
    public ResponseEntity<Void> deleteSenateur(@PathVariable Long id) {
        log.debug("REST request to delete Senateur : {}", id);
        senateurRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

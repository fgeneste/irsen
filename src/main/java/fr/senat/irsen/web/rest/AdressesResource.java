package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.Adresses;
import fr.senat.irsen.repository.AdressesRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.Adresses}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdressesResource {

    private final Logger log = LoggerFactory.getLogger(AdressesResource.class);

    private static final String ENTITY_NAME = "adresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdressesRepository adressesRepository;

    public AdressesResource(AdressesRepository adressesRepository) {
        this.adressesRepository = adressesRepository;
    }

    /**
     * {@code POST  /adresses} : Create a new adresses.
     *
     * @param adresses the adresses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresses, or with status {@code 400 (Bad Request)} if the adresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresses")
    public ResponseEntity<Adresses> createAdresses(@RequestBody Adresses adresses) throws URISyntaxException {
        log.debug("REST request to save Adresses : {}", adresses);
        if (adresses.getId() != null) {
            throw new BadRequestAlertException("A new adresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Adresses result = adressesRepository.save(adresses);
        return ResponseEntity
            .created(new URI("/api/adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresses/:id} : Updates an existing adresses.
     *
     * @param id the id of the adresses to save.
     * @param adresses the adresses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresses,
     * or with status {@code 400 (Bad Request)} if the adresses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresses/{id}")
    public ResponseEntity<Adresses> updateAdresses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresses adresses
    ) throws URISyntaxException {
        log.debug("REST request to update Adresses : {}, {}", id, adresses);
        if (adresses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Adresses result = adressesRepository.save(adresses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresses/:id} : Partial updates given fields of an existing adresses, field will ignore if it is null
     *
     * @param id the id of the adresses to save.
     * @param adresses the adresses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresses,
     * or with status {@code 400 (Bad Request)} if the adresses is not valid,
     * or with status {@code 404 (Not Found)} if the adresses is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresses> partialUpdateAdresses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresses adresses
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresses partially : {}, {}", id, adresses);
        if (adresses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresses> result = adressesRepository
            .findById(adresses.getId())
            .map(existingAdresses -> {
                return existingAdresses;
            })
            .map(adressesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresses.getId().toString())
        );
    }

    /**
     * {@code GET  /adresses} : get all the adresses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresses in body.
     */
    @GetMapping("/adresses")
    public List<Adresses> getAllAdresses(@RequestParam(required = false) String filter) {
        if ("senateur-is-null".equals(filter)) {
            log.debug("REST request to get all Adressess where senateur is null");
            return StreamSupport
                .stream(adressesRepository.findAll().spliterator(), false)
                .filter(adresses -> adresses.getSenateur() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Adresses");
        return adressesRepository.findAll();
    }

    /**
     * {@code GET  /adresses/:id} : get the "id" adresses.
     *
     * @param id the id of the adresses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresses/{id}")
    public ResponseEntity<Adresses> getAdresses(@PathVariable Long id) {
        log.debug("REST request to get Adresses : {}", id);
        Optional<Adresses> adresses = adressesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresses);
    }

    /**
     * {@code DELETE  /adresses/:id} : delete the "id" adresses.
     *
     * @param id the id of the adresses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresses/{id}")
    public ResponseEntity<Void> deleteAdresses(@PathVariable Long id) {
        log.debug("REST request to delete Adresses : {}", id);
        adressesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

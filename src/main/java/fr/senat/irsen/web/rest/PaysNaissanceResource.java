package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.PaysNaissance;
import fr.senat.irsen.repository.PaysNaissanceRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.PaysNaissance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaysNaissanceResource {

    private final Logger log = LoggerFactory.getLogger(PaysNaissanceResource.class);

    private static final String ENTITY_NAME = "paysNaissance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaysNaissanceRepository paysNaissanceRepository;

    public PaysNaissanceResource(PaysNaissanceRepository paysNaissanceRepository) {
        this.paysNaissanceRepository = paysNaissanceRepository;
    }

    /**
     * {@code POST  /pays-naissances} : Create a new paysNaissance.
     *
     * @param paysNaissance the paysNaissance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paysNaissance, or with status {@code 400 (Bad Request)} if the paysNaissance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pays-naissances")
    public ResponseEntity<PaysNaissance> createPaysNaissance(@RequestBody PaysNaissance paysNaissance) throws URISyntaxException {
        log.debug("REST request to save PaysNaissance : {}", paysNaissance);
        if (paysNaissance.getId() != null) {
            throw new BadRequestAlertException("A new paysNaissance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaysNaissance result = paysNaissanceRepository.save(paysNaissance);
        return ResponseEntity
            .created(new URI("/api/pays-naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pays-naissances/:id} : Updates an existing paysNaissance.
     *
     * @param id the id of the paysNaissance to save.
     * @param paysNaissance the paysNaissance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paysNaissance,
     * or with status {@code 400 (Bad Request)} if the paysNaissance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paysNaissance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pays-naissances/{id}")
    public ResponseEntity<PaysNaissance> updatePaysNaissance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaysNaissance paysNaissance
    ) throws URISyntaxException {
        log.debug("REST request to update PaysNaissance : {}, {}", id, paysNaissance);
        if (paysNaissance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paysNaissance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paysNaissanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaysNaissance result = paysNaissanceRepository.save(paysNaissance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paysNaissance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pays-naissances/:id} : Partial updates given fields of an existing paysNaissance, field will ignore if it is null
     *
     * @param id the id of the paysNaissance to save.
     * @param paysNaissance the paysNaissance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paysNaissance,
     * or with status {@code 400 (Bad Request)} if the paysNaissance is not valid,
     * or with status {@code 404 (Not Found)} if the paysNaissance is not found,
     * or with status {@code 500 (Internal Server Error)} if the paysNaissance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pays-naissances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaysNaissance> partialUpdatePaysNaissance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaysNaissance paysNaissance
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaysNaissance partially : {}, {}", id, paysNaissance);
        if (paysNaissance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paysNaissance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paysNaissanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaysNaissance> result = paysNaissanceRepository
            .findById(paysNaissance.getId())
            .map(existingPaysNaissance -> {
                if (paysNaissance.getCode() != null) {
                    existingPaysNaissance.setCode(paysNaissance.getCode());
                }
                if (paysNaissance.getLibelle() != null) {
                    existingPaysNaissance.setLibelle(paysNaissance.getLibelle());
                }
                if (paysNaissance.getAvecConseilDepartemental() != null) {
                    existingPaysNaissance.setAvecConseilDepartemental(paysNaissance.getAvecConseilDepartemental());
                }
                if (paysNaissance.getArticle() != null) {
                    existingPaysNaissance.setArticle(paysNaissance.getArticle());
                }

                return existingPaysNaissance;
            })
            .map(paysNaissanceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paysNaissance.getId().toString())
        );
    }

    /**
     * {@code GET  /pays-naissances} : get all the paysNaissances.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paysNaissances in body.
     */
    @GetMapping("/pays-naissances")
    public List<PaysNaissance> getAllPaysNaissances(@RequestParam(required = false) String filter) {
        if ("etatcivil-is-null".equals(filter)) {
            log.debug("REST request to get all PaysNaissances where etatCivil is null");
            return StreamSupport
                .stream(paysNaissanceRepository.findAll().spliterator(), false)
                .filter(paysNaissance -> paysNaissance.getEtatCivil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PaysNaissances");
        return paysNaissanceRepository.findAll();
    }

    /**
     * {@code GET  /pays-naissances/:id} : get the "id" paysNaissance.
     *
     * @param id the id of the paysNaissance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paysNaissance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pays-naissances/{id}")
    public ResponseEntity<PaysNaissance> getPaysNaissance(@PathVariable Long id) {
        log.debug("REST request to get PaysNaissance : {}", id);
        Optional<PaysNaissance> paysNaissance = paysNaissanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paysNaissance);
    }

    /**
     * {@code DELETE  /pays-naissances/:id} : delete the "id" paysNaissance.
     *
     * @param id the id of the paysNaissance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pays-naissances/{id}")
    public ResponseEntity<Void> deletePaysNaissance(@PathVariable Long id) {
        log.debug("REST request to delete PaysNaissance : {}", id);
        paysNaissanceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

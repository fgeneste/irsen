package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.DepartementNaissance;
import fr.senat.irsen.repository.DepartementNaissanceRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.DepartementNaissance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DepartementNaissanceResource {

    private final Logger log = LoggerFactory.getLogger(DepartementNaissanceResource.class);

    private static final String ENTITY_NAME = "departementNaissance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartementNaissanceRepository departementNaissanceRepository;

    public DepartementNaissanceResource(DepartementNaissanceRepository departementNaissanceRepository) {
        this.departementNaissanceRepository = departementNaissanceRepository;
    }

    /**
     * {@code POST  /departement-naissances} : Create a new departementNaissance.
     *
     * @param departementNaissance the departementNaissance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departementNaissance, or with status {@code 400 (Bad Request)} if the departementNaissance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departement-naissances")
    public ResponseEntity<DepartementNaissance> createDepartementNaissance(@RequestBody DepartementNaissance departementNaissance)
        throws URISyntaxException {
        log.debug("REST request to save DepartementNaissance : {}", departementNaissance);
        if (departementNaissance.getId() != null) {
            throw new BadRequestAlertException("A new departementNaissance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartementNaissance result = departementNaissanceRepository.save(departementNaissance);
        return ResponseEntity
            .created(new URI("/api/departement-naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departement-naissances/:id} : Updates an existing departementNaissance.
     *
     * @param id the id of the departementNaissance to save.
     * @param departementNaissance the departementNaissance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departementNaissance,
     * or with status {@code 400 (Bad Request)} if the departementNaissance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departementNaissance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departement-naissances/{id}")
    public ResponseEntity<DepartementNaissance> updateDepartementNaissance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DepartementNaissance departementNaissance
    ) throws URISyntaxException {
        log.debug("REST request to update DepartementNaissance : {}, {}", id, departementNaissance);
        if (departementNaissance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departementNaissance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departementNaissanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DepartementNaissance result = departementNaissanceRepository.save(departementNaissance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departementNaissance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /departement-naissances/:id} : Partial updates given fields of an existing departementNaissance, field will ignore if it is null
     *
     * @param id the id of the departementNaissance to save.
     * @param departementNaissance the departementNaissance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departementNaissance,
     * or with status {@code 400 (Bad Request)} if the departementNaissance is not valid,
     * or with status {@code 404 (Not Found)} if the departementNaissance is not found,
     * or with status {@code 500 (Internal Server Error)} if the departementNaissance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/departement-naissances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartementNaissance> partialUpdateDepartementNaissance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DepartementNaissance departementNaissance
    ) throws URISyntaxException {
        log.debug("REST request to partial update DepartementNaissance partially : {}, {}", id, departementNaissance);
        if (departementNaissance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departementNaissance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departementNaissanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartementNaissance> result = departementNaissanceRepository
            .findById(departementNaissance.getId())
            .map(existingDepartementNaissance -> {
                if (departementNaissance.getCode() != null) {
                    existingDepartementNaissance.setCode(departementNaissance.getCode());
                }
                if (departementNaissance.getLibelle() != null) {
                    existingDepartementNaissance.setLibelle(departementNaissance.getLibelle());
                }
                if (departementNaissance.getAvecConseilDepartemental() != null) {
                    existingDepartementNaissance.setAvecConseilDepartemental(departementNaissance.getAvecConseilDepartemental());
                }
                if (departementNaissance.getArticle() != null) {
                    existingDepartementNaissance.setArticle(departementNaissance.getArticle());
                }
                if (departementNaissance.getCodeSirpas() != null) {
                    existingDepartementNaissance.setCodeSirpas(departementNaissance.getCodeSirpas());
                }
                if (departementNaissance.getCodeComparaison() != null) {
                    existingDepartementNaissance.setCodeComparaison(departementNaissance.getCodeComparaison());
                }
                if (departementNaissance.getLibelleComplet() != null) {
                    existingDepartementNaissance.setLibelleComplet(departementNaissance.getLibelleComplet());
                }
                if (departementNaissance.getLibelleAvecArticle() != null) {
                    existingDepartementNaissance.setLibelleAvecArticle(departementNaissance.getLibelleAvecArticle());
                }

                return existingDepartementNaissance;
            })
            .map(departementNaissanceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departementNaissance.getId().toString())
        );
    }

    /**
     * {@code GET  /departement-naissances} : get all the departementNaissances.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departementNaissances in body.
     */
    @GetMapping("/departement-naissances")
    public List<DepartementNaissance> getAllDepartementNaissances(@RequestParam(required = false) String filter) {
        if ("etatcivil-is-null".equals(filter)) {
            log.debug("REST request to get all DepartementNaissances where etatCivil is null");
            return StreamSupport
                .stream(departementNaissanceRepository.findAll().spliterator(), false)
                .filter(departementNaissance -> departementNaissance.getEtatCivil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DepartementNaissances");
        return departementNaissanceRepository.findAll();
    }

    /**
     * {@code GET  /departement-naissances/:id} : get the "id" departementNaissance.
     *
     * @param id the id of the departementNaissance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departementNaissance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departement-naissances/{id}")
    public ResponseEntity<DepartementNaissance> getDepartementNaissance(@PathVariable Long id) {
        log.debug("REST request to get DepartementNaissance : {}", id);
        Optional<DepartementNaissance> departementNaissance = departementNaissanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departementNaissance);
    }

    /**
     * {@code DELETE  /departement-naissances/:id} : delete the "id" departementNaissance.
     *
     * @param id the id of the departementNaissance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departement-naissances/{id}")
    public ResponseEntity<Void> deleteDepartementNaissance(@PathVariable Long id) {
        log.debug("REST request to delete DepartementNaissance : {}", id);
        departementNaissanceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

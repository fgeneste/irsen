package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.CategorieSocioProf;
import fr.senat.irsen.repository.CategorieSocioProfRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.CategorieSocioProf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CategorieSocioProfResource {

    private final Logger log = LoggerFactory.getLogger(CategorieSocioProfResource.class);

    private static final String ENTITY_NAME = "categorieSocioProf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieSocioProfRepository categorieSocioProfRepository;

    public CategorieSocioProfResource(CategorieSocioProfRepository categorieSocioProfRepository) {
        this.categorieSocioProfRepository = categorieSocioProfRepository;
    }

    /**
     * {@code POST  /categorie-socio-profs} : Create a new categorieSocioProf.
     *
     * @param categorieSocioProf the categorieSocioProf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieSocioProf, or with status {@code 400 (Bad Request)} if the categorieSocioProf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-socio-profs")
    public ResponseEntity<CategorieSocioProf> createCategorieSocioProf(@RequestBody CategorieSocioProf categorieSocioProf)
        throws URISyntaxException {
        log.debug("REST request to save CategorieSocioProf : {}", categorieSocioProf);
        if (categorieSocioProf.getId() != null) {
            throw new BadRequestAlertException("A new categorieSocioProf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieSocioProf result = categorieSocioProfRepository.save(categorieSocioProf);
        return ResponseEntity
            .created(new URI("/api/categorie-socio-profs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-socio-profs/:id} : Updates an existing categorieSocioProf.
     *
     * @param id the id of the categorieSocioProf to save.
     * @param categorieSocioProf the categorieSocioProf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieSocioProf,
     * or with status {@code 400 (Bad Request)} if the categorieSocioProf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieSocioProf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-socio-profs/{id}")
    public ResponseEntity<CategorieSocioProf> updateCategorieSocioProf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieSocioProf categorieSocioProf
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieSocioProf : {}, {}", id, categorieSocioProf);
        if (categorieSocioProf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieSocioProf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieSocioProfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieSocioProf result = categorieSocioProfRepository.save(categorieSocioProf);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieSocioProf.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-socio-profs/:id} : Partial updates given fields of an existing categorieSocioProf, field will ignore if it is null
     *
     * @param id the id of the categorieSocioProf to save.
     * @param categorieSocioProf the categorieSocioProf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieSocioProf,
     * or with status {@code 400 (Bad Request)} if the categorieSocioProf is not valid,
     * or with status {@code 404 (Not Found)} if the categorieSocioProf is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieSocioProf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-socio-profs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategorieSocioProf> partialUpdateCategorieSocioProf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieSocioProf categorieSocioProf
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieSocioProf partially : {}, {}", id, categorieSocioProf);
        if (categorieSocioProf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieSocioProf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieSocioProfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieSocioProf> result = categorieSocioProfRepository
            .findById(categorieSocioProf.getId())
            .map(existingCategorieSocioProf -> {
                if (categorieSocioProf.getCode() != null) {
                    existingCategorieSocioProf.setCode(categorieSocioProf.getCode());
                }
                if (categorieSocioProf.getLibelle() != null) {
                    existingCategorieSocioProf.setLibelle(categorieSocioProf.getLibelle());
                }
                if (categorieSocioProf.getLibelleComplet() != null) {
                    existingCategorieSocioProf.setLibelleComplet(categorieSocioProf.getLibelleComplet());
                }

                return existingCategorieSocioProf;
            })
            .map(categorieSocioProfRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieSocioProf.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-socio-profs} : get all the categorieSocioProfs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieSocioProfs in body.
     */
    @GetMapping("/categorie-socio-profs")
    public List<CategorieSocioProf> getAllCategorieSocioProfs(@RequestParam(required = false) String filter) {
        if ("categoriesocioprof-is-null".equals(filter)) {
            log.debug("REST request to get all CategorieSocioProfs where categorieSocioProf is null");
            return StreamSupport
                .stream(categorieSocioProfRepository.findAll().spliterator(), false)
                .filter(categorieSocioProf -> categorieSocioProf.getCategorieSocioProf() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all CategorieSocioProfs");
        return categorieSocioProfRepository.findAll();
    }

    /**
     * {@code GET  /categorie-socio-profs/:id} : get the "id" categorieSocioProf.
     *
     * @param id the id of the categorieSocioProf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieSocioProf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-socio-profs/{id}")
    public ResponseEntity<CategorieSocioProf> getCategorieSocioProf(@PathVariable Long id) {
        log.debug("REST request to get CategorieSocioProf : {}", id);
        Optional<CategorieSocioProf> categorieSocioProf = categorieSocioProfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categorieSocioProf);
    }

    /**
     * {@code DELETE  /categorie-socio-profs/:id} : delete the "id" categorieSocioProf.
     *
     * @param id the id of the categorieSocioProf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-socio-profs/{id}")
    public ResponseEntity<Void> deleteCategorieSocioProf(@PathVariable Long id) {
        log.debug("REST request to delete CategorieSocioProf : {}", id);
        categorieSocioProfRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

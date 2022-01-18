package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.AdressePostale2;
import fr.senat.irsen.repository.AdressePostale2Repository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.AdressePostale2}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdressePostale2Resource {

    private final Logger log = LoggerFactory.getLogger(AdressePostale2Resource.class);

    private static final String ENTITY_NAME = "adressePostale2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdressePostale2Repository adressePostale2Repository;

    public AdressePostale2Resource(AdressePostale2Repository adressePostale2Repository) {
        this.adressePostale2Repository = adressePostale2Repository;
    }

    /**
     * {@code POST  /adresse-postale-2-s} : Create a new adressePostale2.
     *
     * @param adressePostale2 the adressePostale2 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adressePostale2, or with status {@code 400 (Bad Request)} if the adressePostale2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresse-postale-2-s")
    public ResponseEntity<AdressePostale2> createAdressePostale2(@RequestBody AdressePostale2 adressePostale2) throws URISyntaxException {
        log.debug("REST request to save AdressePostale2 : {}", adressePostale2);
        if (adressePostale2.getId() != null) {
            throw new BadRequestAlertException("A new adressePostale2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdressePostale2 result = adressePostale2Repository.save(adressePostale2);
        return ResponseEntity
            .created(new URI("/api/adresse-postale-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresse-postale-2-s/:id} : Updates an existing adressePostale2.
     *
     * @param id the id of the adressePostale2 to save.
     * @param adressePostale2 the adressePostale2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressePostale2,
     * or with status {@code 400 (Bad Request)} if the adressePostale2 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adressePostale2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresse-postale-2-s/{id}")
    public ResponseEntity<AdressePostale2> updateAdressePostale2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressePostale2 adressePostale2
    ) throws URISyntaxException {
        log.debug("REST request to update AdressePostale2 : {}, {}", id, adressePostale2);
        if (adressePostale2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressePostale2.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressePostale2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdressePostale2 result = adressePostale2Repository.save(adressePostale2);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressePostale2.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresse-postale-2-s/:id} : Partial updates given fields of an existing adressePostale2, field will ignore if it is null
     *
     * @param id the id of the adressePostale2 to save.
     * @param adressePostale2 the adressePostale2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressePostale2,
     * or with status {@code 400 (Bad Request)} if the adressePostale2 is not valid,
     * or with status {@code 404 (Not Found)} if the adressePostale2 is not found,
     * or with status {@code 500 (Internal Server Error)} if the adressePostale2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adresse-postale-2-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdressePostale2> partialUpdateAdressePostale2(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressePostale2 adressePostale2
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdressePostale2 partially : {}, {}", id, adressePostale2);
        if (adressePostale2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressePostale2.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressePostale2Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdressePostale2> result = adressePostale2Repository
            .findById(adressePostale2.getId())
            .map(existingAdressePostale2 -> {
                if (adressePostale2.getNumero() != null) {
                    existingAdressePostale2.setNumero(adressePostale2.getNumero());
                }
                if (adressePostale2.getBister() != null) {
                    existingAdressePostale2.setBister(adressePostale2.getBister());
                }
                if (adressePostale2.getComplement1() != null) {
                    existingAdressePostale2.setComplement1(adressePostale2.getComplement1());
                }
                if (adressePostale2.getComplement2() != null) {
                    existingAdressePostale2.setComplement2(adressePostale2.getComplement2());
                }
                if (adressePostale2.getTypeVoie() != null) {
                    existingAdressePostale2.setTypeVoie(adressePostale2.getTypeVoie());
                }
                if (adressePostale2.getVoie() != null) {
                    existingAdressePostale2.setVoie(adressePostale2.getVoie());
                }
                if (adressePostale2.getCodePostal() != null) {
                    existingAdressePostale2.setCodePostal(adressePostale2.getCodePostal());
                }
                if (adressePostale2.getVille() != null) {
                    existingAdressePostale2.setVille(adressePostale2.getVille());
                }
                if (adressePostale2.getPays() != null) {
                    existingAdressePostale2.setPays(adressePostale2.getPays());
                }
                if (adressePostale2.getAffichageInternet() != null) {
                    existingAdressePostale2.setAffichageInternet(adressePostale2.getAffichageInternet());
                }
                if (adressePostale2.getAffichageIntranet() != null) {
                    existingAdressePostale2.setAffichageIntranet(adressePostale2.getAffichageIntranet());
                }

                return existingAdressePostale2;
            })
            .map(adressePostale2Repository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressePostale2.getId().toString())
        );
    }

    /**
     * {@code GET  /adresse-postale-2-s} : get all the adressePostale2s.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adressePostale2s in body.
     */
    @GetMapping("/adresse-postale-2-s")
    public List<AdressePostale2> getAllAdressePostale2s(@RequestParam(required = false) String filter) {
        if ("adresses-is-null".equals(filter)) {
            log.debug("REST request to get all AdressePostale2s where adresses is null");
            return StreamSupport
                .stream(adressePostale2Repository.findAll().spliterator(), false)
                .filter(adressePostale2 -> adressePostale2.getAdresses() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AdressePostale2s");
        return adressePostale2Repository.findAll();
    }

    /**
     * {@code GET  /adresse-postale-2-s/:id} : get the "id" adressePostale2.
     *
     * @param id the id of the adressePostale2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adressePostale2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresse-postale-2-s/{id}")
    public ResponseEntity<AdressePostale2> getAdressePostale2(@PathVariable Long id) {
        log.debug("REST request to get AdressePostale2 : {}", id);
        Optional<AdressePostale2> adressePostale2 = adressePostale2Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(adressePostale2);
    }

    /**
     * {@code DELETE  /adresse-postale-2-s/:id} : delete the "id" adressePostale2.
     *
     * @param id the id of the adressePostale2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresse-postale-2-s/{id}")
    public ResponseEntity<Void> deleteAdressePostale2(@PathVariable Long id) {
        log.debug("REST request to delete AdressePostale2 : {}", id);
        adressePostale2Repository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

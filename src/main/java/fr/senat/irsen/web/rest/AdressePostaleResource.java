package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.AdressePostale;
import fr.senat.irsen.repository.AdressePostaleRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.AdressePostale}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdressePostaleResource {

    private final Logger log = LoggerFactory.getLogger(AdressePostaleResource.class);

    private static final String ENTITY_NAME = "adressePostale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdressePostaleRepository adressePostaleRepository;

    public AdressePostaleResource(AdressePostaleRepository adressePostaleRepository) {
        this.adressePostaleRepository = adressePostaleRepository;
    }

    /**
     * {@code POST  /adresse-postales} : Create a new adressePostale.
     *
     * @param adressePostale the adressePostale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adressePostale, or with status {@code 400 (Bad Request)} if the adressePostale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresse-postales")
    public ResponseEntity<AdressePostale> createAdressePostale(@RequestBody AdressePostale adressePostale) throws URISyntaxException {
        log.debug("REST request to save AdressePostale : {}", adressePostale);
        if (adressePostale.getId() != null) {
            throw new BadRequestAlertException("A new adressePostale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdressePostale result = adressePostaleRepository.save(adressePostale);
        return ResponseEntity
            .created(new URI("/api/adresse-postales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresse-postales/:id} : Updates an existing adressePostale.
     *
     * @param id the id of the adressePostale to save.
     * @param adressePostale the adressePostale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressePostale,
     * or with status {@code 400 (Bad Request)} if the adressePostale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adressePostale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresse-postales/{id}")
    public ResponseEntity<AdressePostale> updateAdressePostale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressePostale adressePostale
    ) throws URISyntaxException {
        log.debug("REST request to update AdressePostale : {}, {}", id, adressePostale);
        if (adressePostale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressePostale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressePostaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdressePostale result = adressePostaleRepository.save(adressePostale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressePostale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresse-postales/:id} : Partial updates given fields of an existing adressePostale, field will ignore if it is null
     *
     * @param id the id of the adressePostale to save.
     * @param adressePostale the adressePostale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressePostale,
     * or with status {@code 400 (Bad Request)} if the adressePostale is not valid,
     * or with status {@code 404 (Not Found)} if the adressePostale is not found,
     * or with status {@code 500 (Internal Server Error)} if the adressePostale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adresse-postales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdressePostale> partialUpdateAdressePostale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressePostale adressePostale
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdressePostale partially : {}, {}", id, adressePostale);
        if (adressePostale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressePostale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressePostaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdressePostale> result = adressePostaleRepository
            .findById(adressePostale.getId())
            .map(existingAdressePostale -> {
                if (adressePostale.getNumero() != null) {
                    existingAdressePostale.setNumero(adressePostale.getNumero());
                }
                if (adressePostale.getBister() != null) {
                    existingAdressePostale.setBister(adressePostale.getBister());
                }
                if (adressePostale.getComplement1() != null) {
                    existingAdressePostale.setComplement1(adressePostale.getComplement1());
                }
                if (adressePostale.getComplement2() != null) {
                    existingAdressePostale.setComplement2(adressePostale.getComplement2());
                }
                if (adressePostale.getTypeVoie() != null) {
                    existingAdressePostale.setTypeVoie(adressePostale.getTypeVoie());
                }
                if (adressePostale.getVoie() != null) {
                    existingAdressePostale.setVoie(adressePostale.getVoie());
                }
                if (adressePostale.getCodePostal() != null) {
                    existingAdressePostale.setCodePostal(adressePostale.getCodePostal());
                }
                if (adressePostale.getVille() != null) {
                    existingAdressePostale.setVille(adressePostale.getVille());
                }
                if (adressePostale.getPays() != null) {
                    existingAdressePostale.setPays(adressePostale.getPays());
                }
                if (adressePostale.getAffichageInternet() != null) {
                    existingAdressePostale.setAffichageInternet(adressePostale.getAffichageInternet());
                }
                if (adressePostale.getAffichageIntranet() != null) {
                    existingAdressePostale.setAffichageIntranet(adressePostale.getAffichageIntranet());
                }

                return existingAdressePostale;
            })
            .map(adressePostaleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressePostale.getId().toString())
        );
    }

    /**
     * {@code GET  /adresse-postales} : get all the adressePostales.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adressePostales in body.
     */
    @GetMapping("/adresse-postales")
    public List<AdressePostale> getAllAdressePostales(@RequestParam(required = false) String filter) {
        if ("adresses-is-null".equals(filter)) {
            log.debug("REST request to get all AdressePostales where adresses is null");
            return StreamSupport
                .stream(adressePostaleRepository.findAll().spliterator(), false)
                .filter(adressePostale -> adressePostale.getAdresses() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AdressePostales");
        return adressePostaleRepository.findAll();
    }

    /**
     * {@code GET  /adresse-postales/:id} : get the "id" adressePostale.
     *
     * @param id the id of the adressePostale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adressePostale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresse-postales/{id}")
    public ResponseEntity<AdressePostale> getAdressePostale(@PathVariable Long id) {
        log.debug("REST request to get AdressePostale : {}", id);
        Optional<AdressePostale> adressePostale = adressePostaleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adressePostale);
    }

    /**
     * {@code DELETE  /adresse-postales/:id} : delete the "id" adressePostale.
     *
     * @param id the id of the adressePostale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresse-postales/{id}")
    public ResponseEntity<Void> deleteAdressePostale(@PathVariable Long id) {
        log.debug("REST request to delete AdressePostale : {}", id);
        adressePostaleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package fr.senat.irsen.web.rest;

import fr.senat.irsen.domain.AdresseFiscale;
import fr.senat.irsen.repository.AdresseFiscaleRepository;
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
 * REST controller for managing {@link fr.senat.irsen.domain.AdresseFiscale}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdresseFiscaleResource {

    private final Logger log = LoggerFactory.getLogger(AdresseFiscaleResource.class);

    private static final String ENTITY_NAME = "adresseFiscale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresseFiscaleRepository adresseFiscaleRepository;

    public AdresseFiscaleResource(AdresseFiscaleRepository adresseFiscaleRepository) {
        this.adresseFiscaleRepository = adresseFiscaleRepository;
    }

    /**
     * {@code POST  /adresse-fiscales} : Create a new adresseFiscale.
     *
     * @param adresseFiscale the adresseFiscale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresseFiscale, or with status {@code 400 (Bad Request)} if the adresseFiscale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adresse-fiscales")
    public ResponseEntity<AdresseFiscale> createAdresseFiscale(@RequestBody AdresseFiscale adresseFiscale) throws URISyntaxException {
        log.debug("REST request to save AdresseFiscale : {}", adresseFiscale);
        if (adresseFiscale.getId() != null) {
            throw new BadRequestAlertException("A new adresseFiscale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdresseFiscale result = adresseFiscaleRepository.save(adresseFiscale);
        return ResponseEntity
            .created(new URI("/api/adresse-fiscales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresse-fiscales/:id} : Updates an existing adresseFiscale.
     *
     * @param id the id of the adresseFiscale to save.
     * @param adresseFiscale the adresseFiscale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseFiscale,
     * or with status {@code 400 (Bad Request)} if the adresseFiscale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresseFiscale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adresse-fiscales/{id}")
    public ResponseEntity<AdresseFiscale> updateAdresseFiscale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdresseFiscale adresseFiscale
    ) throws URISyntaxException {
        log.debug("REST request to update AdresseFiscale : {}, {}", id, adresseFiscale);
        if (adresseFiscale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseFiscale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseFiscaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdresseFiscale result = adresseFiscaleRepository.save(adresseFiscale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresseFiscale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresse-fiscales/:id} : Partial updates given fields of an existing adresseFiscale, field will ignore if it is null
     *
     * @param id the id of the adresseFiscale to save.
     * @param adresseFiscale the adresseFiscale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseFiscale,
     * or with status {@code 400 (Bad Request)} if the adresseFiscale is not valid,
     * or with status {@code 404 (Not Found)} if the adresseFiscale is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresseFiscale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adresse-fiscales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdresseFiscale> partialUpdateAdresseFiscale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdresseFiscale adresseFiscale
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdresseFiscale partially : {}, {}", id, adresseFiscale);
        if (adresseFiscale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseFiscale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseFiscaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdresseFiscale> result = adresseFiscaleRepository
            .findById(adresseFiscale.getId())
            .map(existingAdresseFiscale -> {
                if (adresseFiscale.getNumero() != null) {
                    existingAdresseFiscale.setNumero(adresseFiscale.getNumero());
                }
                if (adresseFiscale.getBister() != null) {
                    existingAdresseFiscale.setBister(adresseFiscale.getBister());
                }
                if (adresseFiscale.getComplement1() != null) {
                    existingAdresseFiscale.setComplement1(adresseFiscale.getComplement1());
                }
                if (adresseFiscale.getComplement2() != null) {
                    existingAdresseFiscale.setComplement2(adresseFiscale.getComplement2());
                }
                if (adresseFiscale.getTypeVoie() != null) {
                    existingAdresseFiscale.setTypeVoie(adresseFiscale.getTypeVoie());
                }
                if (adresseFiscale.getVoie() != null) {
                    existingAdresseFiscale.setVoie(adresseFiscale.getVoie());
                }
                if (adresseFiscale.getCodePostal() != null) {
                    existingAdresseFiscale.setCodePostal(adresseFiscale.getCodePostal());
                }
                if (adresseFiscale.getVille() != null) {
                    existingAdresseFiscale.setVille(adresseFiscale.getVille());
                }
                if (adresseFiscale.getPays() != null) {
                    existingAdresseFiscale.setPays(adresseFiscale.getPays());
                }
                if (adresseFiscale.getAffichageInternet() != null) {
                    existingAdresseFiscale.setAffichageInternet(adresseFiscale.getAffichageInternet());
                }
                if (adresseFiscale.getAffichageIntranet() != null) {
                    existingAdresseFiscale.setAffichageIntranet(adresseFiscale.getAffichageIntranet());
                }

                return existingAdresseFiscale;
            })
            .map(adresseFiscaleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adresseFiscale.getId().toString())
        );
    }

    /**
     * {@code GET  /adresse-fiscales} : get all the adresseFiscales.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresseFiscales in body.
     */
    @GetMapping("/adresse-fiscales")
    public List<AdresseFiscale> getAllAdresseFiscales(@RequestParam(required = false) String filter) {
        if ("adresses-is-null".equals(filter)) {
            log.debug("REST request to get all AdresseFiscales where adresses is null");
            return StreamSupport
                .stream(adresseFiscaleRepository.findAll().spliterator(), false)
                .filter(adresseFiscale -> adresseFiscale.getAdresses() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AdresseFiscales");
        return adresseFiscaleRepository.findAll();
    }

    /**
     * {@code GET  /adresse-fiscales/:id} : get the "id" adresseFiscale.
     *
     * @param id the id of the adresseFiscale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresseFiscale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adresse-fiscales/{id}")
    public ResponseEntity<AdresseFiscale> getAdresseFiscale(@PathVariable Long id) {
        log.debug("REST request to get AdresseFiscale : {}", id);
        Optional<AdresseFiscale> adresseFiscale = adresseFiscaleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresseFiscale);
    }

    /**
     * {@code DELETE  /adresse-fiscales/:id} : delete the "id" adresseFiscale.
     *
     * @param id the id of the adresseFiscale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adresse-fiscales/{id}")
    public ResponseEntity<Void> deleteAdresseFiscale(@PathVariable Long id) {
        log.debug("REST request to delete AdresseFiscale : {}", id);
        adresseFiscaleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

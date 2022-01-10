package fr.senat.irsen.repository;

import fr.senat.irsen.domain.AdresseFiscale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdresseFiscale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseFiscaleRepository extends JpaRepository<AdresseFiscale, Long> {}

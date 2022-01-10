package fr.senat.irsen.repository;

import fr.senat.irsen.domain.AdressePostale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdressePostale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdressePostaleRepository extends JpaRepository<AdressePostale, Long> {}

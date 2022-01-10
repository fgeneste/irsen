package fr.senat.irsen.repository;

import fr.senat.irsen.domain.AdressePostale2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdressePostale2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdressePostale2Repository extends JpaRepository<AdressePostale2, Long> {}

package fr.senat.irsen.repository;

import fr.senat.irsen.domain.EtatCivil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtatCivil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Long> {}

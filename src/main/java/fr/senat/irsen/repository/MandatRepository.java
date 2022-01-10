package fr.senat.irsen.repository;

import fr.senat.irsen.domain.Mandat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mandat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandatRepository extends JpaRepository<Mandat, Long> {}

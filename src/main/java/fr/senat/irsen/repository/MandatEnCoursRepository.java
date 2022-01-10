package fr.senat.irsen.repository;

import fr.senat.irsen.domain.MandatEnCours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MandatEnCours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandatEnCoursRepository extends JpaRepository<MandatEnCours, Long> {}

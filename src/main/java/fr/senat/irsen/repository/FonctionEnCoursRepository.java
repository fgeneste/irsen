package fr.senat.irsen.repository;

import fr.senat.irsen.domain.FonctionEnCours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FonctionEnCours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionEnCoursRepository extends JpaRepository<FonctionEnCours, Long> {}

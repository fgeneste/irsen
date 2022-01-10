package fr.senat.irsen.repository;

import fr.senat.irsen.domain.CategorieSocioProf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategorieSocioProf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieSocioProfRepository extends JpaRepository<CategorieSocioProf, Long> {}

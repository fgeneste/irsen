package fr.senat.irsen.repository;

import fr.senat.irsen.domain.FonctionAncien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FonctionAncien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionAncienRepository extends JpaRepository<FonctionAncien, Long> {}

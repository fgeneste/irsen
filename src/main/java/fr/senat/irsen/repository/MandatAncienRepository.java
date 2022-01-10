package fr.senat.irsen.repository;

import fr.senat.irsen.domain.MandatAncien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MandatAncien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandatAncienRepository extends JpaRepository<MandatAncien, Long> {}

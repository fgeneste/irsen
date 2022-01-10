package fr.senat.irsen.repository;

import fr.senat.irsen.domain.PaysNaissance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaysNaissance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaysNaissanceRepository extends JpaRepository<PaysNaissance, Long> {}

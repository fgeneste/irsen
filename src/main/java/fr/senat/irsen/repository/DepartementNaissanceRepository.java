package fr.senat.irsen.repository;

import fr.senat.irsen.domain.DepartementNaissance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DepartementNaissance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartementNaissanceRepository extends JpaRepository<DepartementNaissance, Long> {}

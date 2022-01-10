package fr.senat.irsen.repository;

import fr.senat.irsen.domain.Senateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Senateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SenateurRepository extends JpaRepository<Senateur, Long> {}

package fr.senat.irsen.repository;

import fr.senat.irsen.domain.TelephoneFixe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TelephoneFixe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelephoneFixeRepository extends JpaRepository<TelephoneFixe, Long> {}

package fr.senat.irsen.repository;

import fr.senat.irsen.domain.Adresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Adresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdressesRepository extends JpaRepository<Adresses, Long> {}

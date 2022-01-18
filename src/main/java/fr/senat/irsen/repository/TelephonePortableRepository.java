package fr.senat.irsen.repository;

import fr.senat.irsen.domain.TelephonePortable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TelephonePortable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelephonePortableRepository extends JpaRepository<TelephonePortable, Long> {}

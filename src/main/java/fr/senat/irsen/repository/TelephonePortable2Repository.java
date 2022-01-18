package fr.senat.irsen.repository;

import fr.senat.irsen.domain.TelephonePortable2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TelephonePortable2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelephonePortable2Repository extends JpaRepository<TelephonePortable2, Long> {}

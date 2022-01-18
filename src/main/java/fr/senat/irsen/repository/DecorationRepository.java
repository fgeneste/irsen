package fr.senat.irsen.repository;

import fr.senat.irsen.domain.Decoration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Decoration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecorationRepository extends JpaRepository<Decoration, Long> {}

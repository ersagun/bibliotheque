package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Exemplaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Exemplaire entity.
 */
@SuppressWarnings("unused")
public interface ExemplaireRepository extends JpaRepository<Exemplaire,Long> {


    @Query("select COUNT(exemplaire) from Exemplaire exemplaire inner join exemplaire.oeuvre where exemplaire.disponible = true AND oeuvre_id=:id")
    Long countDisponibleExemplaireBylivre(@Param("id") Long id);
}

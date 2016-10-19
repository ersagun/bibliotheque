package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Auteur;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Auteur entity.
 */
@SuppressWarnings("unused")
public interface AuteurRepository extends JpaRepository<Auteur,Long> {

    @Query("select distinct auteur from Auteur auteur left join fetch auteur.livres")
    List<Auteur> findAllWithEagerRelationships();

    @Query("select auteur from Auteur auteur left join fetch auteur.livres where auteur.id =:id")
    Auteur findOneWithEagerRelationships(@Param("id") Long id);

}

package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Exemplaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Exemplaire entity.
 */
@SuppressWarnings("unused")
public interface ExemplaireRepository extends JpaRepository<Exemplaire,Long> {

}

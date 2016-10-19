package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Emprunt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Emprunt entity.
 */
@SuppressWarnings("unused")
public interface EmpruntRepository extends JpaRepository<Emprunt,Long> {

}

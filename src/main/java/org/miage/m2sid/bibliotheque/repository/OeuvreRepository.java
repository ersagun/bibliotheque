package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Oeuvre;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Oeuvre entity.
 */
@SuppressWarnings("unused")
public interface OeuvreRepository extends JpaRepository<Oeuvre,Long> {

}

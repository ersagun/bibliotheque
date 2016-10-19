package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Usager;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Usager entity.
 */
@SuppressWarnings("unused")
public interface UsagerRepository extends JpaRepository<Usager,Long> {

}

package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Livre;

import org.miage.m2sid.bibliotheque.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Livre entity.
 */
@SuppressWarnings("unused")
public interface LivreRepository extends JpaRepository<Livre,Long> {


    @Query(value = "select livre from Livre livre")
    Page<Livre> findAllByPage(Pageable pageable);

}

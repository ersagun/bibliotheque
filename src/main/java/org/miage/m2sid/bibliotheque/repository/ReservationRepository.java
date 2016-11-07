package org.miage.m2sid.bibliotheque.repository;

import org.miage.m2sid.bibliotheque.domain.Reservation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
@SuppressWarnings("unused")
public interface ReservationRepository extends JpaRepository<Reservation,Long> {


    @Query("select COUNT(reservation) from Reservation reservation where oeuvre_id=:idOeuvre AND usager_id=:idUser")
    Long countIfUserAlreadyReserved(@Param("idUser") Long idUser, @Param("idOeuvre") Long idOeuvre);

    @Query("select distinct reservation from Reservation reservation where oeuvre_id=:idOeuvre AND usager_id=:idUser")
    Reservation getUserAlreadyReserved(@Param("idUser") Long idUser, @Param("idOeuvre") Long idOeuvre);

    @Query("select COUNT(exemplaire) from Exemplaire exemplaire where oeuvre_id=:idOeuvre AND disponible=true")
    Long countExemplaireAvailable(@Param("idOeuvre") Long idOeuvre);
}

package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.ReservationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReservationMapper {

    @Mapping(source = "usager.id", target = "usagerId")
    @Mapping(source = "oeuvre.id", target = "oeuvreId")
    ReservationDTO reservationToReservationDTO(Reservation reservation);

    List<ReservationDTO> reservationsToReservationDTOs(List<Reservation> reservations);

    @Mapping(source = "usagerId", target = "usager")
    @Mapping(source = "oeuvreId", target = "oeuvre")
    Reservation reservationDTOToReservation(ReservationDTO reservationDTO);

    List<Reservation> reservationDTOsToReservations(List<ReservationDTO> reservationDTOs);

    default Usager usagerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usager usager = new Usager();
        usager.setId(id);
        return usager;
    }

    default Oeuvre oeuvreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setId(id);
        return oeuvre;
    }
}

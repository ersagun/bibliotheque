package org.miage.m2sid.bibliotheque.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateDemande;


    private Long usagerId;
    
    private Long oeuvreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(ZonedDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Long getUsagerId() {
        return usagerId;
    }

    public void setUsagerId(Long usagerId) {
        this.usagerId = usagerId;
    }

    public Long getOeuvreId() {
        return oeuvreId;
    }

    public void setOeuvreId(Long oeuvreId) {
        this.oeuvreId = oeuvreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;

        if ( ! Objects.equals(id, reservationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + id +
            ", dateDemande='" + dateDemande + "'" +
            '}';
    }
}

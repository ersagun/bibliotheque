package org.miage.m2sid.bibliotheque.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Magazine entity.
 */
public class MagazineDTO implements Serializable {

    private Long id;

    private Integer numero;

    private LocalDate parution;

    private Integer periodicite;


    private Long oeuvreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public LocalDate getParution() {
        return parution;
    }

    public void setParution(LocalDate parution) {
        this.parution = parution;
    }
    public Integer getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(Integer periodicite) {
        this.periodicite = periodicite;
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

        MagazineDTO magazineDTO = (MagazineDTO) o;

        if ( ! Objects.equals(id, magazineDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MagazineDTO{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", parution='" + parution + "'" +
            ", periodicite='" + periodicite + "'" +
            '}';
    }
}

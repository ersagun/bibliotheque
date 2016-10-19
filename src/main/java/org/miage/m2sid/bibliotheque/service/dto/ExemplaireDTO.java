package org.miage.m2sid.bibliotheque.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Exemplaire entity.
 */
public class ExemplaireDTO implements Serializable {

    private Long id;

    private Integer etat;

    private Boolean disponible;


    private Long oeuvreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
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

        ExemplaireDTO exemplaireDTO = (ExemplaireDTO) o;

        if ( ! Objects.equals(id, exemplaireDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExemplaireDTO{" +
            "id=" + id +
            ", etat='" + etat + "'" +
            ", disponible='" + disponible + "'" +
            '}';
    }
}

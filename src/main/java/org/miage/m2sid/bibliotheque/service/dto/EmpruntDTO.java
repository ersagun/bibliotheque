package org.miage.m2sid.bibliotheque.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Emprunt entity.
 */
public class EmpruntDTO implements Serializable {

    private Long id;

    private ZonedDateTime debut;

    private Integer duree;


    private Long usagerId;
    
    private Long exemplaireId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDebut() {
        return debut;
    }

    public void setDebut(ZonedDateTime debut) {
        this.debut = debut;
    }
    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Long getUsagerId() {
        return usagerId;
    }

    public void setUsagerId(Long usagerId) {
        this.usagerId = usagerId;
    }

    public Long getExemplaireId() {
        return exemplaireId;
    }

    public void setExemplaireId(Long exemplaireId) {
        this.exemplaireId = exemplaireId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpruntDTO empruntDTO = (EmpruntDTO) o;

        if ( ! Objects.equals(id, empruntDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmpruntDTO{" +
            "id=" + id +
            ", debut='" + debut + "'" +
            ", duree='" + duree + "'" +
            '}';
    }
}

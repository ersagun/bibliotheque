package org.miage.m2sid.bibliotheque.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Livre entity.
 */
public class LivreDTO implements Serializable {

    private Long id;

    private LocalDate dateEdition;

    private String resume;

    private String url;


    private Long oeuvreId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(LocalDate dateEdition) {
        this.dateEdition = dateEdition;
    }
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        LivreDTO livreDTO = (LivreDTO) o;

        if ( ! Objects.equals(id, livreDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LivreDTO{" +
            "id=" + id +
            ", dateEdition='" + dateEdition + "'" +
            ", resume='" + resume + "'" +
            ", url='" + url + "'" +
            '}';
    }
}

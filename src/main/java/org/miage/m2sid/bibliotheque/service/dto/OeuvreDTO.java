package org.miage.m2sid.bibliotheque.service.dto;

import org.miage.m2sid.bibliotheque.domain.Exemplaire;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Oeuvre entity.
 */
public class OeuvreDTO implements Serializable {

    private Long id;

    private String titre;

    private String editeur;

    private Set<Exemplaire> exemplaires;

    private boolean exemplaireDisponible;


    public boolean getExemplaireDisponible(){
        return this.exemplaireDisponible;
    }

    public void setExemplaireDisponible(boolean bool){
        this.exemplaireDisponible=bool;
    }


    public Set<Exemplaire> getExemplaires(){return this.exemplaires;}
    public void setExemplaires( Set<Exemplaire> exemplaires ){this.exemplaires=exemplaires;}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OeuvreDTO oeuvreDTO = (OeuvreDTO) o;

        if ( ! Objects.equals(id, oeuvreDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OeuvreDTO{" +
            "id=" + id +
            ", titre='" + titre + "'" +
            ", editeur='" + editeur + "'" +
            '}';
    }
}

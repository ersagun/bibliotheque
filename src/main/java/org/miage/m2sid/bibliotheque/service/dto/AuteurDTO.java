package org.miage.m2sid.bibliotheque.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Auteur entity.
 */
public class AuteurDTO implements Serializable {

    private Long id;

    private String nom;

    private String prenom;


    private Set<LivreDTO> livres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Set<LivreDTO> getLivres() {
        return livres;
    }

    public void setLivres(Set<LivreDTO> livres) {
        this.livres = livres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuteurDTO auteurDTO = (AuteurDTO) o;

        if ( ! Objects.equals(id, auteurDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuteurDTO{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            '}';
    }
}

package org.miage.m2sid.bibliotheque.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Auteur.
 */
@Entity
@Table(name = "auteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Auteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "auteur_livre",
               joinColumns = @JoinColumn(name="auteurs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="livres_id", referencedColumnName="ID"))
    private Set<Livre> livres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Auteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Auteur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Set<Livre> getLivres() {
        return livres;
    }

    public Auteur livres(Set<Livre> livres) {
        this.livres = livres;
        return this;
    }

    public Auteur addLivre(Livre livre) {
        livres.add(livre);
        livre.getAuteurs().add(this);
        return this;
    }

    public Auteur removeLivre(Livre livre) {
        livres.remove(livre);
        livre.getAuteurs().remove(this);
        return this;
    }

    public void setLivres(Set<Livre> livres) {
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
        Auteur auteur = (Auteur) o;
        if(auteur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, auteur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Auteur{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            '}';
    }
}

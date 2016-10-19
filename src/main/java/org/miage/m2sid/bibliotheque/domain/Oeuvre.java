package org.miage.m2sid.bibliotheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Oeuvre.
 */
@Entity
@Table(name = "oeuvre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Oeuvre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "editeur")
    private String editeur;

    @OneToMany(mappedBy = "oeuvre")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "oeuvre", fetch=FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exemplaire> exemplaires = new HashSet<>();

    @OneToOne(mappedBy = "oeuvre")
    @JsonIgnore
    private Livre livre;

    @OneToOne(mappedBy = "oeuvre")
    @JsonIgnore
    private Magazine magazine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Oeuvre titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public Oeuvre editeur(String editeur) {
        this.editeur = editeur;
        return this;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Oeuvre reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Oeuvre addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setOeuvre(this);
        return this;
    }

    public Oeuvre removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setOeuvre(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public Oeuvre exemplaires(Set<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
        return this;
    }

    public Oeuvre addExemplaire(Exemplaire exemplaire) {
        exemplaires.add(exemplaire);
        exemplaire.setOeuvre(this);
        return this;
    }

    public Oeuvre removeExemplaire(Exemplaire exemplaire) {
        exemplaires.remove(exemplaire);
        exemplaire.setOeuvre(null);
        return this;
    }

    public void setExemplaires(Set<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    public Livre getLivre() {
        return livre;
    }

    public Oeuvre livre(Livre livre) {
        this.livre = livre;
        return this;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public Oeuvre magazine(Magazine magazine) {
        this.magazine = magazine;
        return this;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Oeuvre oeuvre = (Oeuvre) o;
        if(oeuvre.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, oeuvre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Oeuvre{" +
            "id=" + id +
            ", titre='" + titre + "'" +
            ", editeur='" + editeur + "'" +
            '}';
    }
}

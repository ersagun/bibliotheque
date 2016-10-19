package org.miage.m2sid.bibliotheque.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Usager.
 */
@Entity
@Table(name = "usager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "telephone")
    private Integer telephone;

    @OneToMany(mappedBy = "usager")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "usager")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emprunt> emprunts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Usager nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Usager prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Usager adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Usager dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public Usager telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Usager reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Usager addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUsager(this);
        return this;
    }

    public Usager removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setUsager(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Emprunt> getEmprunts() {
        return emprunts;
    }

    public Usager emprunts(Set<Emprunt> emprunts) {
        this.emprunts = emprunts;
        return this;
    }

    public Usager addEmprunt(Emprunt emprunt) {
        emprunts.add(emprunt);
        emprunt.setUsager(this);
        return this;
    }

    public Usager removeEmprunt(Emprunt emprunt) {
        emprunts.remove(emprunt);
        emprunt.setUsager(null);
        return this;
    }

    public void setEmprunts(Set<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usager usager = (Usager) o;
        if(usager.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usager{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", adresse='" + adresse + "'" +
            ", dateNaissance='" + dateNaissance + "'" +
            ", telephone='" + telephone + "'" +
            '}';
    }
}

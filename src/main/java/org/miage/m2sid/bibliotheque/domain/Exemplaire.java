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
 * A Exemplaire.
 */
@Entity
@Table(name = "exemplaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exemplaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "disponible")
    private Boolean disponible;

    @OneToMany(mappedBy = "exemplaire")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emprunt> emprunts = new HashSet<>();

    @ManyToOne
    private Oeuvre oeuvre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtat() {
        return etat;
    }

    public Exemplaire etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    public Exemplaire disponible(Boolean disponible) {
        this.disponible = disponible;
        return this;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Set<Emprunt> getEmprunts() {
        return emprunts;
    }

    public Exemplaire emprunts(Set<Emprunt> emprunts) {
        this.emprunts = emprunts;
        return this;
    }

    public Exemplaire addEmprunt(Emprunt emprunt) {
        emprunts.add(emprunt);
        emprunt.setExemplaire(this);
        return this;
    }

    public Exemplaire removeEmprunt(Emprunt emprunt) {
        emprunts.remove(emprunt);
        emprunt.setExemplaire(null);
        return this;
    }

    public void setEmprunts(Set<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public Exemplaire oeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
        return this;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exemplaire exemplaire = (Exemplaire) o;
        if(exemplaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exemplaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exemplaire{" +
            "id=" + id +
            ", etat='" + etat + "'" +
            ", disponible='" + disponible + "'" +
            '}';
    }
}

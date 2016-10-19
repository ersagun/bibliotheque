package org.miage.m2sid.bibliotheque.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Emprunt.
 */
@Entity
@Table(name = "emprunt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Emprunt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "debut")
    private ZonedDateTime debut;

    @Column(name = "duree")
    private Integer duree;

    @ManyToOne
    private Usager usager;

    @ManyToOne
    private Exemplaire exemplaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDebut() {
        return debut;
    }

    public Emprunt debut(ZonedDateTime debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(ZonedDateTime debut) {
        this.debut = debut;
    }

    public Integer getDuree() {
        return duree;
    }

    public Emprunt duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Usager getUsager() {
        return usager;
    }

    public Emprunt usager(Usager usager) {
        this.usager = usager;
        return this;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public Emprunt exemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
        return this;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Emprunt emprunt = (Emprunt) o;
        if(emprunt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, emprunt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Emprunt{" +
            "id=" + id +
            ", debut='" + debut + "'" +
            ", duree='" + duree + "'" +
            '}';
    }
}

package org.miage.m2sid.bibliotheque.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Magazine.
 */
@Entity
@Table(name = "magazine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Magazine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "parution")
    private LocalDate parution;

    @Column(name = "periodicite")
    private Integer periodicite;

    @OneToOne
    @JoinColumn(unique = true)
    private Oeuvre oeuvre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Magazine numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getParution() {
        return parution;
    }

    public Magazine parution(LocalDate parution) {
        this.parution = parution;
        return this;
    }

    public void setParution(LocalDate parution) {
        this.parution = parution;
    }

    public Integer getPeriodicite() {
        return periodicite;
    }

    public Magazine periodicite(Integer periodicite) {
        this.periodicite = periodicite;
        return this;
    }

    public void setPeriodicite(Integer periodicite) {
        this.periodicite = periodicite;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public Magazine oeuvre(Oeuvre oeuvre) {
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
        Magazine magazine = (Magazine) o;
        if(magazine.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, magazine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Magazine{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", parution='" + parution + "'" +
            ", periodicite='" + periodicite + "'" +
            '}';
    }
}

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
 * A Livre.
 */
@Entity
@Table(name = "livre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Livre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_edition")
    private LocalDate dateEdition;

    @Column(name = "resume")
    private String resume;

    @Column(name = "url")
    private String url;

    @OneToOne
    @JoinColumn(unique = true)
    private Oeuvre oeuvre;

    @ManyToMany(mappedBy = "livres", fetch=FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Auteur> auteurs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEdition() {
        return dateEdition;
    }

    public Livre dateEdition(LocalDate dateEdition) {
        this.dateEdition = dateEdition;
        return this;
    }

    public void setDateEdition(LocalDate dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String getResume() {
        return resume;
    }

    public Livre resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUrl() {
        return url;
    }

    public Livre url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public Livre oeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
        return this;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

    public Set<Auteur> getAuteurs() {
        return auteurs;
    }

    public Livre auteurs(Set<Auteur> auteurs) {
        this.auteurs = auteurs;
        return this;
    }

    public Livre addAuteur(Auteur auteur) {
        auteurs.add(auteur);
        auteur.getLivres().add(this);
        return this;
    }

    public Livre removeAuteur(Auteur auteur) {
        auteurs.remove(auteur);
        auteur.getLivres().remove(this);
        return this;
    }

    public void setAuteurs(Set<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Livre livre = (Livre) o;
        if(livre.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, livre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Livre{" +
            "id=" + id +
            ", dateEdition='" + dateEdition + "'" +
            ", resume='" + resume + "'" +
            ", url='" + url + "'" +
            '}';
    }
}

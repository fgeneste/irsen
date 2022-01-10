package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FonctionAncien.
 */
@Entity
@Table(name = "fonction_ancien")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FonctionAncien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @OneToMany(mappedBy = "fonctionAncien")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fonctionAncien", "anciensMandats" }, allowSetters = true)
    private Set<MandatAncien> fonctions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FonctionAncien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public FonctionAncien libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public FonctionAncien dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public FonctionAncien dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Set<MandatAncien> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<MandatAncien> mandatAnciens) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setFonctionAncien(null));
        }
        if (mandatAnciens != null) {
            mandatAnciens.forEach(i -> i.setFonctionAncien(this));
        }
        this.fonctions = mandatAnciens;
    }

    public FonctionAncien fonctions(Set<MandatAncien> mandatAnciens) {
        this.setFonctions(mandatAnciens);
        return this;
    }

    public FonctionAncien addFonctions(MandatAncien mandatAncien) {
        this.fonctions.add(mandatAncien);
        mandatAncien.setFonctionAncien(this);
        return this;
    }

    public FonctionAncien removeFonctions(MandatAncien mandatAncien) {
        this.fonctions.remove(mandatAncien);
        mandatAncien.setFonctionAncien(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FonctionAncien)) {
            return false;
        }
        return id != null && id.equals(((FonctionAncien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FonctionAncien{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}

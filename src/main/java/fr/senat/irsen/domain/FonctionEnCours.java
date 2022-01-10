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
 * A FonctionEnCours.
 */
@Entity
@Table(name = "fonction_en_cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FonctionEnCours implements Serializable {

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

    @OneToMany(mappedBy = "fonctionEnCours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fonctionEnCours", "mandatsEnCours" }, allowSetters = true)
    private Set<MandatEnCours> fonctions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FonctionEnCours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public FonctionEnCours libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public FonctionEnCours dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public FonctionEnCours dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Set<MandatEnCours> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<MandatEnCours> mandatEnCours) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setFonctionEnCours(null));
        }
        if (mandatEnCours != null) {
            mandatEnCours.forEach(i -> i.setFonctionEnCours(this));
        }
        this.fonctions = mandatEnCours;
    }

    public FonctionEnCours fonctions(Set<MandatEnCours> mandatEnCours) {
        this.setFonctions(mandatEnCours);
        return this;
    }

    public FonctionEnCours addFonctions(MandatEnCours mandatEnCours) {
        this.fonctions.add(mandatEnCours);
        mandatEnCours.setFonctionEnCours(this);
        return this;
    }

    public FonctionEnCours removeFonctions(MandatEnCours mandatEnCours) {
        this.fonctions.remove(mandatEnCours);
        mandatEnCours.setFonctionEnCours(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FonctionEnCours)) {
            return false;
        }
        return id != null && id.equals(((FonctionEnCours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FonctionEnCours{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}

package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mandat", "fonctions" }, allowSetters = true)
    private MandatEnCours mandatEnCours;

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

    public String getDateDebut() {
        return this.dateDebut;
    }

    public FonctionEnCours dateDebut(String dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return this.dateFin;
    }

    public FonctionEnCours dateFin(String dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public MandatEnCours getMandatEnCours() {
        return this.mandatEnCours;
    }

    public void setMandatEnCours(MandatEnCours mandatEnCours) {
        this.mandatEnCours = mandatEnCours;
    }

    public FonctionEnCours mandatEnCours(MandatEnCours mandatEnCours) {
        this.setMandatEnCours(mandatEnCours);
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

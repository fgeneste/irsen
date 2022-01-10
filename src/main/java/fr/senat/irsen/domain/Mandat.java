package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mandat.
 */
@Entity
@Table(name = "mandat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mandat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fonctionAncien", "anciensMandats" }, allowSetters = true)
    private MandatAncien mandatAncien;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fonctionEnCours", "mandatsEnCours" }, allowSetters = true)
    private MandatEnCours mandatEnCours;

    @JsonIgnoreProperties(value = { "etatCivil", "adresses", "mandats" }, allowSetters = true)
    @OneToOne(mappedBy = "mandats")
    private Senateur senateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mandat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MandatAncien getMandatAncien() {
        return this.mandatAncien;
    }

    public void setMandatAncien(MandatAncien mandatAncien) {
        this.mandatAncien = mandatAncien;
    }

    public Mandat mandatAncien(MandatAncien mandatAncien) {
        this.setMandatAncien(mandatAncien);
        return this;
    }

    public MandatEnCours getMandatEnCours() {
        return this.mandatEnCours;
    }

    public void setMandatEnCours(MandatEnCours mandatEnCours) {
        this.mandatEnCours = mandatEnCours;
    }

    public Mandat mandatEnCours(MandatEnCours mandatEnCours) {
        this.setMandatEnCours(mandatEnCours);
        return this;
    }

    public Senateur getSenateur() {
        return this.senateur;
    }

    public void setSenateur(Senateur senateur) {
        if (this.senateur != null) {
            this.senateur.setMandats(null);
        }
        if (senateur != null) {
            senateur.setMandats(this);
        }
        this.senateur = senateur;
    }

    public Mandat senateur(Senateur senateur) {
        this.setSenateur(senateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mandat)) {
            return false;
        }
        return id != null && id.equals(((Mandat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mandat{" +
            "id=" + getId() +
            "}";
    }
}

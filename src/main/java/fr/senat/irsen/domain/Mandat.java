package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @JsonIgnoreProperties(value = { "etatCivil", "adresses", "mandats" }, allowSetters = true)
    @OneToOne(mappedBy = "mandats")
    private Senateur senateur;

    @OneToMany(mappedBy = "mandat", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandat", "fonctions" }, allowSetters = true)
    private Set<MandatAncien> anciensMandats = new HashSet<>();

    @OneToMany(mappedBy = "mandat", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandat", "fonctions" }, allowSetters = true)
    private Set<MandatEnCours> mandatsEnCours = new HashSet<>();

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

    public Set<MandatAncien> getAnciensMandats() {
        return this.anciensMandats;
    }

    public void setAnciensMandats(Set<MandatAncien> mandatAnciens) {
        if (this.anciensMandats != null) {
            this.anciensMandats.forEach(i -> i.setMandat(null));
        }
        if (mandatAnciens != null) {
            mandatAnciens.forEach(i -> i.setMandat(this));
        }
        this.anciensMandats = mandatAnciens;
    }

    public Mandat anciensMandats(Set<MandatAncien> mandatAnciens) {
        this.setAnciensMandats(mandatAnciens);
        return this;
    }

    public Mandat addAnciensMandats(MandatAncien mandatAncien) {
        this.anciensMandats.add(mandatAncien);
        mandatAncien.setMandat(this);
        return this;
    }

    public Mandat removeAnciensMandats(MandatAncien mandatAncien) {
        this.anciensMandats.remove(mandatAncien);
        mandatAncien.setMandat(null);
        return this;
    }

    public Set<MandatEnCours> getMandatsEnCours() {
        return this.mandatsEnCours;
    }

    public void setMandatsEnCours(Set<MandatEnCours> mandatEnCours) {
        if (this.mandatsEnCours != null) {
            this.mandatsEnCours.forEach(i -> i.setMandat(null));
        }
        if (mandatEnCours != null) {
            mandatEnCours.forEach(i -> i.setMandat(this));
        }
        this.mandatsEnCours = mandatEnCours;
    }

    public Mandat mandatsEnCours(Set<MandatEnCours> mandatEnCours) {
        this.setMandatsEnCours(mandatEnCours);
        return this;
    }

    public Mandat addMandatsEnCours(MandatEnCours mandatEnCours) {
        this.mandatsEnCours.add(mandatEnCours);
        mandatEnCours.setMandat(this);
        return this;
    }

    public Mandat removeMandatsEnCours(MandatEnCours mandatEnCours) {
        this.mandatsEnCours.remove(mandatEnCours);
        mandatEnCours.setMandat(null);
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

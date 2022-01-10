package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Senateur.
 */
@Entity
@Table(name = "senateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Senateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "departementNaissance", "paysNaissance", "categorieSocioProf", "senateur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private EtatCivil etatCivil;

    @JsonIgnoreProperties(value = { "adresseFiscale", "adressePostale", "adressePostale2", "senateur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Adresses adresses;

    @JsonIgnoreProperties(value = { "mandatAncien", "mandatEnCours", "senateur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Mandat mandats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Senateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatCivil getEtatCivil() {
        return this.etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public Senateur etatCivil(EtatCivil etatCivil) {
        this.setEtatCivil(etatCivil);
        return this;
    }

    public Adresses getAdresses() {
        return this.adresses;
    }

    public void setAdresses(Adresses adresses) {
        this.adresses = adresses;
    }

    public Senateur adresses(Adresses adresses) {
        this.setAdresses(adresses);
        return this;
    }

    public Mandat getMandats() {
        return this.mandats;
    }

    public void setMandats(Mandat mandat) {
        this.mandats = mandat;
    }

    public Senateur mandats(Mandat mandat) {
        this.setMandats(mandat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Senateur)) {
            return false;
        }
        return id != null && id.equals(((Senateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Senateur{" +
            "id=" + getId() +
            "}";
    }
}

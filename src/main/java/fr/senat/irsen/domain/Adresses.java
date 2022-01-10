package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adresses.
 */
@Entity
@Table(name = "adresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Adresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "adresses" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AdresseFiscale adresseFiscale;

    @JsonIgnoreProperties(value = { "adresses" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AdressePostale adressePostale;

    @JsonIgnoreProperties(value = { "adresses" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AdressePostale2 adressePostale2;

    @JsonIgnoreProperties(value = { "etatCivil", "adresses", "mandats" }, allowSetters = true)
    @OneToOne(mappedBy = "adresses")
    private Senateur senateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdresseFiscale getAdresseFiscale() {
        return this.adresseFiscale;
    }

    public void setAdresseFiscale(AdresseFiscale adresseFiscale) {
        this.adresseFiscale = adresseFiscale;
    }

    public Adresses adresseFiscale(AdresseFiscale adresseFiscale) {
        this.setAdresseFiscale(adresseFiscale);
        return this;
    }

    public AdressePostale getAdressePostale() {
        return this.adressePostale;
    }

    public void setAdressePostale(AdressePostale adressePostale) {
        this.adressePostale = adressePostale;
    }

    public Adresses adressePostale(AdressePostale adressePostale) {
        this.setAdressePostale(adressePostale);
        return this;
    }

    public AdressePostale2 getAdressePostale2() {
        return this.adressePostale2;
    }

    public void setAdressePostale2(AdressePostale2 adressePostale2) {
        this.adressePostale2 = adressePostale2;
    }

    public Adresses adressePostale2(AdressePostale2 adressePostale2) {
        this.setAdressePostale2(adressePostale2);
        return this;
    }

    public Senateur getSenateur() {
        return this.senateur;
    }

    public void setSenateur(Senateur senateur) {
        if (this.senateur != null) {
            this.senateur.setAdresses(null);
        }
        if (senateur != null) {
            senateur.setAdresses(this);
        }
        this.senateur = senateur;
    }

    public Adresses senateur(Senateur senateur) {
        this.setSenateur(senateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresses)) {
            return false;
        }
        return id != null && id.equals(((Adresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresses{" +
            "id=" + getId() +
            "}";
    }
}

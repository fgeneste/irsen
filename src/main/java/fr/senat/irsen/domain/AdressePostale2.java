package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdressePostale2.
 */
@Entity
@Table(name = "adresse_postale_2")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdressePostale2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "numero")
    private String numero;

    @Column(name = "voie")
    private String voie;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @Column(name = "localisation")
    private String localisation;

    @Column(name = "mode_manuel")
    private Boolean modeManuel;

    @Column(name = "type")
    private String type;

    @JsonIgnoreProperties(value = { "adresseFiscale", "adressePostale", "adressePostale2", "senateur" }, allowSetters = true)
    @OneToOne(mappedBy = "adressePostale2")
    private Adresses adresses;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdressePostale2 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public AdressePostale2 label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNumero() {
        return this.numero;
    }

    public AdressePostale2 numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVoie() {
        return this.voie;
    }

    public AdressePostale2 voie(String voie) {
        this.setVoie(voie);
        return this;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public AdressePostale2 codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return this.ville;
    }

    public AdressePostale2 ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return this.pays;
    }

    public AdressePostale2 pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getLocalisation() {
        return this.localisation;
    }

    public AdressePostale2 localisation(String localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Boolean getModeManuel() {
        return this.modeManuel;
    }

    public AdressePostale2 modeManuel(Boolean modeManuel) {
        this.setModeManuel(modeManuel);
        return this;
    }

    public void setModeManuel(Boolean modeManuel) {
        this.modeManuel = modeManuel;
    }

    public String getType() {
        return this.type;
    }

    public AdressePostale2 type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Adresses getAdresses() {
        return this.adresses;
    }

    public void setAdresses(Adresses adresses) {
        if (this.adresses != null) {
            this.adresses.setAdressePostale2(null);
        }
        if (adresses != null) {
            adresses.setAdressePostale2(this);
        }
        this.adresses = adresses;
    }

    public AdressePostale2 adresses(Adresses adresses) {
        this.setAdresses(adresses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdressePostale2)) {
            return false;
        }
        return id != null && id.equals(((AdressePostale2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdressePostale2{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", numero='" + getNumero() + "'" +
            ", voie='" + getVoie() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", modeManuel='" + getModeManuel() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

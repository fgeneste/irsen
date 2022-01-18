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

    @Column(name = "numero")
    private String numero;

    @Column(name = "bister")
    private String bister;

    @Column(name = "complement_1")
    private String complement1;

    @Column(name = "complement_2")
    private String complement2;

    @Column(name = "type_voie")
    private String typeVoie;

    @Column(name = "voie")
    private String voie;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @Column(name = "affichage_internet")
    private Boolean affichageInternet;

    @Column(name = "affichage_intranet")
    private Boolean affichageIntranet;

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

    public String getBister() {
        return this.bister;
    }

    public AdressePostale2 bister(String bister) {
        this.setBister(bister);
        return this;
    }

    public void setBister(String bister) {
        this.bister = bister;
    }

    public String getComplement1() {
        return this.complement1;
    }

    public AdressePostale2 complement1(String complement1) {
        this.setComplement1(complement1);
        return this;
    }

    public void setComplement1(String complement1) {
        this.complement1 = complement1;
    }

    public String getComplement2() {
        return this.complement2;
    }

    public AdressePostale2 complement2(String complement2) {
        this.setComplement2(complement2);
        return this;
    }

    public void setComplement2(String complement2) {
        this.complement2 = complement2;
    }

    public String getTypeVoie() {
        return this.typeVoie;
    }

    public AdressePostale2 typeVoie(String typeVoie) {
        this.setTypeVoie(typeVoie);
        return this;
    }

    public void setTypeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
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

    public Boolean getAffichageInternet() {
        return this.affichageInternet;
    }

    public AdressePostale2 affichageInternet(Boolean affichageInternet) {
        this.setAffichageInternet(affichageInternet);
        return this;
    }

    public void setAffichageInternet(Boolean affichageInternet) {
        this.affichageInternet = affichageInternet;
    }

    public Boolean getAffichageIntranet() {
        return this.affichageIntranet;
    }

    public AdressePostale2 affichageIntranet(Boolean affichageIntranet) {
        this.setAffichageIntranet(affichageIntranet);
        return this;
    }

    public void setAffichageIntranet(Boolean affichageIntranet) {
        this.affichageIntranet = affichageIntranet;
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
            ", numero='" + getNumero() + "'" +
            ", bister='" + getBister() + "'" +
            ", complement1='" + getComplement1() + "'" +
            ", complement2='" + getComplement2() + "'" +
            ", typeVoie='" + getTypeVoie() + "'" +
            ", voie='" + getVoie() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            ", affichageInternet='" + getAffichageInternet() + "'" +
            ", affichageIntranet='" + getAffichageIntranet() + "'" +
            "}";
    }
}

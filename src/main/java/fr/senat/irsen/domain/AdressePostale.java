package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdressePostale.
 */
@Entity
@Table(name = "adresse_postale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdressePostale implements Serializable {

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
    @OneToOne(mappedBy = "adressePostale")
    private Adresses adresses;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdressePostale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public AdressePostale numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBister() {
        return this.bister;
    }

    public AdressePostale bister(String bister) {
        this.setBister(bister);
        return this;
    }

    public void setBister(String bister) {
        this.bister = bister;
    }

    public String getComplement1() {
        return this.complement1;
    }

    public AdressePostale complement1(String complement1) {
        this.setComplement1(complement1);
        return this;
    }

    public void setComplement1(String complement1) {
        this.complement1 = complement1;
    }

    public String getComplement2() {
        return this.complement2;
    }

    public AdressePostale complement2(String complement2) {
        this.setComplement2(complement2);
        return this;
    }

    public void setComplement2(String complement2) {
        this.complement2 = complement2;
    }

    public String getTypeVoie() {
        return this.typeVoie;
    }

    public AdressePostale typeVoie(String typeVoie) {
        this.setTypeVoie(typeVoie);
        return this;
    }

    public void setTypeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
    }

    public String getVoie() {
        return this.voie;
    }

    public AdressePostale voie(String voie) {
        this.setVoie(voie);
        return this;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public AdressePostale codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return this.ville;
    }

    public AdressePostale ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return this.pays;
    }

    public AdressePostale pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Boolean getAffichageInternet() {
        return this.affichageInternet;
    }

    public AdressePostale affichageInternet(Boolean affichageInternet) {
        this.setAffichageInternet(affichageInternet);
        return this;
    }

    public void setAffichageInternet(Boolean affichageInternet) {
        this.affichageInternet = affichageInternet;
    }

    public Boolean getAffichageIntranet() {
        return this.affichageIntranet;
    }

    public AdressePostale affichageIntranet(Boolean affichageIntranet) {
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
            this.adresses.setAdressePostale(null);
        }
        if (adresses != null) {
            adresses.setAdressePostale(this);
        }
        this.adresses = adresses;
    }

    public AdressePostale adresses(Adresses adresses) {
        this.setAdresses(adresses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdressePostale)) {
            return false;
        }
        return id != null && id.equals(((AdressePostale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdressePostale{" +
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

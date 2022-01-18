package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaysNaissance.
 */
@Entity
@Table(name = "pays_naissance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaysNaissance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "avec_conseil_departemental")
    private Boolean avecConseilDepartemental;

    @Column(name = "article")
    private String article;

    @JsonIgnoreProperties(
        value = {
            "departementNaissance",
            "paysNaissance",
            "categorieSocioProf",
            "telephonePortable",
            "telephonePortable2",
            "telephoneFixe",
            "senateur",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "paysNaissance")
    private EtatCivil etatCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaysNaissance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public PaysNaissance code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public PaysNaissance libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getAvecConseilDepartemental() {
        return this.avecConseilDepartemental;
    }

    public PaysNaissance avecConseilDepartemental(Boolean avecConseilDepartemental) {
        this.setAvecConseilDepartemental(avecConseilDepartemental);
        return this;
    }

    public void setAvecConseilDepartemental(Boolean avecConseilDepartemental) {
        this.avecConseilDepartemental = avecConseilDepartemental;
    }

    public String getArticle() {
        return this.article;
    }

    public PaysNaissance article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public EtatCivil getEtatCivil() {
        return this.etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        if (this.etatCivil != null) {
            this.etatCivil.setPaysNaissance(null);
        }
        if (etatCivil != null) {
            etatCivil.setPaysNaissance(this);
        }
        this.etatCivil = etatCivil;
    }

    public PaysNaissance etatCivil(EtatCivil etatCivil) {
        this.setEtatCivil(etatCivil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaysNaissance)) {
            return false;
        }
        return id != null && id.equals(((PaysNaissance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaysNaissance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", avecConseilDepartemental='" + getAvecConseilDepartemental() + "'" +
            ", article='" + getArticle() + "'" +
            "}";
    }
}

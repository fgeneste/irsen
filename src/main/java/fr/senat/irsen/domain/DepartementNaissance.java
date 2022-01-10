package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DepartementNaissance.
 */
@Entity
@Table(name = "departement_naissance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DepartementNaissance implements Serializable {

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

    @Column(name = "code_sirpas")
    private String codeSirpas;

    @Column(name = "code_comparaison")
    private String codeComparaison;

    @Column(name = "libelle_complet")
    private String libelleComplet;

    @Column(name = "libelle_avec_article")
    private String libelleAvecArticle;

    @JsonIgnoreProperties(value = { "departementNaissance", "paysNaissance", "categorieSocioProf", "senateur" }, allowSetters = true)
    @OneToOne(mappedBy = "departementNaissance")
    private EtatCivil etatCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DepartementNaissance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public DepartementNaissance code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public DepartementNaissance libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getAvecConseilDepartemental() {
        return this.avecConseilDepartemental;
    }

    public DepartementNaissance avecConseilDepartemental(Boolean avecConseilDepartemental) {
        this.setAvecConseilDepartemental(avecConseilDepartemental);
        return this;
    }

    public void setAvecConseilDepartemental(Boolean avecConseilDepartemental) {
        this.avecConseilDepartemental = avecConseilDepartemental;
    }

    public String getArticle() {
        return this.article;
    }

    public DepartementNaissance article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getCodeSirpas() {
        return this.codeSirpas;
    }

    public DepartementNaissance codeSirpas(String codeSirpas) {
        this.setCodeSirpas(codeSirpas);
        return this;
    }

    public void setCodeSirpas(String codeSirpas) {
        this.codeSirpas = codeSirpas;
    }

    public String getCodeComparaison() {
        return this.codeComparaison;
    }

    public DepartementNaissance codeComparaison(String codeComparaison) {
        this.setCodeComparaison(codeComparaison);
        return this;
    }

    public void setCodeComparaison(String codeComparaison) {
        this.codeComparaison = codeComparaison;
    }

    public String getLibelleComplet() {
        return this.libelleComplet;
    }

    public DepartementNaissance libelleComplet(String libelleComplet) {
        this.setLibelleComplet(libelleComplet);
        return this;
    }

    public void setLibelleComplet(String libelleComplet) {
        this.libelleComplet = libelleComplet;
    }

    public String getLibelleAvecArticle() {
        return this.libelleAvecArticle;
    }

    public DepartementNaissance libelleAvecArticle(String libelleAvecArticle) {
        this.setLibelleAvecArticle(libelleAvecArticle);
        return this;
    }

    public void setLibelleAvecArticle(String libelleAvecArticle) {
        this.libelleAvecArticle = libelleAvecArticle;
    }

    public EtatCivil getEtatCivil() {
        return this.etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        if (this.etatCivil != null) {
            this.etatCivil.setDepartementNaissance(null);
        }
        if (etatCivil != null) {
            etatCivil.setDepartementNaissance(this);
        }
        this.etatCivil = etatCivil;
    }

    public DepartementNaissance etatCivil(EtatCivil etatCivil) {
        this.setEtatCivil(etatCivil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartementNaissance)) {
            return false;
        }
        return id != null && id.equals(((DepartementNaissance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartementNaissance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", avecConseilDepartemental='" + getAvecConseilDepartemental() + "'" +
            ", article='" + getArticle() + "'" +
            ", codeSirpas='" + getCodeSirpas() + "'" +
            ", codeComparaison='" + getCodeComparaison() + "'" +
            ", libelleComplet='" + getLibelleComplet() + "'" +
            ", libelleAvecArticle='" + getLibelleAvecArticle() + "'" +
            "}";
    }
}

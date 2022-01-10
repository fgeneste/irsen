package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieSocioProf.
 */
@Entity
@Table(name = "categorie_socio_prof")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieSocioProf implements Serializable {

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

    @Column(name = "libelle_complet")
    private String libelleComplet;

    @JsonIgnoreProperties(value = { "departementNaissance", "paysNaissance", "categorieSocioProf", "senateur" }, allowSetters = true)
    @OneToOne(mappedBy = "categorieSocioProf")
    private EtatCivil categorieSocioProf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategorieSocioProf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public CategorieSocioProf code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CategorieSocioProf libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleComplet() {
        return this.libelleComplet;
    }

    public CategorieSocioProf libelleComplet(String libelleComplet) {
        this.setLibelleComplet(libelleComplet);
        return this;
    }

    public void setLibelleComplet(String libelleComplet) {
        this.libelleComplet = libelleComplet;
    }

    public EtatCivil getCategorieSocioProf() {
        return this.categorieSocioProf;
    }

    public void setCategorieSocioProf(EtatCivil etatCivil) {
        if (this.categorieSocioProf != null) {
            this.categorieSocioProf.setCategorieSocioProf(null);
        }
        if (etatCivil != null) {
            etatCivil.setCategorieSocioProf(this);
        }
        this.categorieSocioProf = etatCivil;
    }

    public CategorieSocioProf categorieSocioProf(EtatCivil etatCivil) {
        this.setCategorieSocioProf(etatCivil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieSocioProf)) {
            return false;
        }
        return id != null && id.equals(((CategorieSocioProf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieSocioProf{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", libelleComplet='" + getLibelleComplet() + "'" +
            "}";
    }
}

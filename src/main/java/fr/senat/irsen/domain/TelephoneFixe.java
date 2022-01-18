package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TelephoneFixe.
 */
@Entity
@Table(name = "telephone_fixe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelephoneFixe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "numero")
    private String numero;

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
    @OneToOne(mappedBy = "telephoneFixe")
    private EtatCivil etatCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TelephoneFixe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public TelephoneFixe type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumero() {
        return this.numero;
    }

    public TelephoneFixe numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public EtatCivil getEtatCivil() {
        return this.etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        if (this.etatCivil != null) {
            this.etatCivil.setTelephoneFixe(null);
        }
        if (etatCivil != null) {
            etatCivil.setTelephoneFixe(this);
        }
        this.etatCivil = etatCivil;
    }

    public TelephoneFixe etatCivil(EtatCivil etatCivil) {
        this.setEtatCivil(etatCivil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelephoneFixe)) {
            return false;
        }
        return id != null && id.equals(((TelephoneFixe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelephoneFixe{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}

package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TelephonePortable2.
 */
@Entity
@Table(name = "telephone_portable_2")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelephonePortable2 implements Serializable {

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
    @OneToOne(mappedBy = "telephonePortable2")
    private EtatCivil etatCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TelephonePortable2 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public TelephonePortable2 type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumero() {
        return this.numero;
    }

    public TelephonePortable2 numero(String numero) {
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
            this.etatCivil.setTelephonePortable2(null);
        }
        if (etatCivil != null) {
            etatCivil.setTelephonePortable2(this);
        }
        this.etatCivil = etatCivil;
    }

    public TelephonePortable2 etatCivil(EtatCivil etatCivil) {
        this.setEtatCivil(etatCivil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelephonePortable2)) {
            return false;
        }
        return id != null && id.equals(((TelephonePortable2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelephonePortable2{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}

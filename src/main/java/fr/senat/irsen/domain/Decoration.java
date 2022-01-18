package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Decoration.
 */
@Entity
@Table(name = "decoration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Decoration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "grade")
    private String grade;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etatCivil", "adresses", "mandats", "decorations" }, allowSetters = true)
    private Senateur senateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Decoration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Decoration type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return this.grade;
    }

    public Decoration grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Senateur getSenateur() {
        return this.senateur;
    }

    public void setSenateur(Senateur senateur) {
        this.senateur = senateur;
    }

    public Decoration senateur(Senateur senateur) {
        this.setSenateur(senateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Decoration)) {
            return false;
        }
        return id != null && id.equals(((Decoration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Decoration{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", grade='" + getGrade() + "'" +
            "}";
    }
}

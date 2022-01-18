package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MandatEnCours.
 */
@Entity
@Table(name = "mandat_en_cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MandatEnCours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_type")
    private Long idType;

    @Column(name = "id_fonction")
    private Long idFonction;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "libelle_fonction")
    private String libelleFonction;

    @Column(name = "circonscription")
    private String circonscription;

    @Column(name = "depuis")
    private String depuis;

    @Column(name = "date_election")
    private String dateElection;

    @Column(name = "population")
    private String population;

    @Column(name = "libelle_affichage")
    private String libelleAffichage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "senateur", "anciensMandats", "mandatsEnCours" }, allowSetters = true)
    private Mandat mandat;

    @OneToMany(mappedBy = "mandatEnCours", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandatEnCours" }, allowSetters = true)
    private Set<FonctionEnCours> fonctions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MandatEnCours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdType() {
        return this.idType;
    }

    public MandatEnCours idType(Long idType) {
        this.setIdType(idType);
        return this;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public Long getIdFonction() {
        return this.idFonction;
    }

    public MandatEnCours idFonction(Long idFonction) {
        this.setIdFonction(idFonction);
        return this;
    }

    public void setIdFonction(Long idFonction) {
        this.idFonction = idFonction;
    }

    public String getCode() {
        return this.code;
    }

    public MandatEnCours code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public MandatEnCours libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleFonction() {
        return this.libelleFonction;
    }

    public MandatEnCours libelleFonction(String libelleFonction) {
        this.setLibelleFonction(libelleFonction);
        return this;
    }

    public void setLibelleFonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
    }

    public String getCirconscription() {
        return this.circonscription;
    }

    public MandatEnCours circonscription(String circonscription) {
        this.setCirconscription(circonscription);
        return this;
    }

    public void setCirconscription(String circonscription) {
        this.circonscription = circonscription;
    }

    public String getDepuis() {
        return this.depuis;
    }

    public MandatEnCours depuis(String depuis) {
        this.setDepuis(depuis);
        return this;
    }

    public void setDepuis(String depuis) {
        this.depuis = depuis;
    }

    public String getDateElection() {
        return this.dateElection;
    }

    public MandatEnCours dateElection(String dateElection) {
        this.setDateElection(dateElection);
        return this;
    }

    public void setDateElection(String dateElection) {
        this.dateElection = dateElection;
    }

    public String getPopulation() {
        return this.population;
    }

    public MandatEnCours population(String population) {
        this.setPopulation(population);
        return this;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getLibelleAffichage() {
        return this.libelleAffichage;
    }

    public MandatEnCours libelleAffichage(String libelleAffichage) {
        this.setLibelleAffichage(libelleAffichage);
        return this;
    }

    public void setLibelleAffichage(String libelleAffichage) {
        this.libelleAffichage = libelleAffichage;
    }

    public Mandat getMandat() {
        return this.mandat;
    }

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public MandatEnCours mandat(Mandat mandat) {
        this.setMandat(mandat);
        return this;
    }

    public Set<FonctionEnCours> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<FonctionEnCours> fonctionEnCours) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setMandatEnCours(null));
        }
        if (fonctionEnCours != null) {
            fonctionEnCours.forEach(i -> i.setMandatEnCours(this));
        }
        this.fonctions = fonctionEnCours;
    }

    public MandatEnCours fonctions(Set<FonctionEnCours> fonctionEnCours) {
        this.setFonctions(fonctionEnCours);
        return this;
    }

    public MandatEnCours addFonctions(FonctionEnCours fonctionEnCours) {
        this.fonctions.add(fonctionEnCours);
        fonctionEnCours.setMandatEnCours(this);
        return this;
    }

    public MandatEnCours removeFonctions(FonctionEnCours fonctionEnCours) {
        this.fonctions.remove(fonctionEnCours);
        fonctionEnCours.setMandatEnCours(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MandatEnCours)) {
            return false;
        }
        return id != null && id.equals(((MandatEnCours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MandatEnCours{" +
            "id=" + getId() +
            ", idType=" + getIdType() +
            ", idFonction=" + getIdFonction() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", libelleFonction='" + getLibelleFonction() + "'" +
            ", circonscription='" + getCirconscription() + "'" +
            ", depuis='" + getDepuis() + "'" +
            ", dateElection='" + getDateElection() + "'" +
            ", population='" + getPopulation() + "'" +
            ", libelleAffichage='" + getLibelleAffichage() + "'" +
            "}";
    }
}

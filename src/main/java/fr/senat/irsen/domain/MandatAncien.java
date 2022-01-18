package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MandatAncien.
 */
@Entity
@Table(name = "mandat_ancien")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MandatAncien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_type")
    private Long idType;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date_debut")
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @Column(name = "circonscription")
    private String circonscription;

    @Column(name = "libelle_affichage")
    private String libelleAffichage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "senateur", "anciensMandats", "mandatsEnCours" }, allowSetters = true)
    private Mandat mandat;

    @OneToMany(mappedBy = "mandatAncien", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandatAncien" }, allowSetters = true)
    private Set<FonctionAncien> fonctions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MandatAncien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdType() {
        return this.idType;
    }

    public MandatAncien idType(Long idType) {
        this.setIdType(idType);
        return this;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public MandatAncien libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDateDebut() {
        return this.dateDebut;
    }

    public MandatAncien dateDebut(String dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return this.dateFin;
    }

    public MandatAncien dateFin(String dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getCirconscription() {
        return this.circonscription;
    }

    public MandatAncien circonscription(String circonscription) {
        this.setCirconscription(circonscription);
        return this;
    }

    public void setCirconscription(String circonscription) {
        this.circonscription = circonscription;
    }

    public String getLibelleAffichage() {
        return this.libelleAffichage;
    }

    public MandatAncien libelleAffichage(String libelleAffichage) {
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

    public MandatAncien mandat(Mandat mandat) {
        this.setMandat(mandat);
        return this;
    }

    public Set<FonctionAncien> getFonctions() {
        return this.fonctions;
    }

    public void setFonctions(Set<FonctionAncien> fonctionAnciens) {
        if (this.fonctions != null) {
            this.fonctions.forEach(i -> i.setMandatAncien(null));
        }
        if (fonctionAnciens != null) {
            fonctionAnciens.forEach(i -> i.setMandatAncien(this));
        }
        this.fonctions = fonctionAnciens;
    }

    public MandatAncien fonctions(Set<FonctionAncien> fonctionAnciens) {
        this.setFonctions(fonctionAnciens);
        return this;
    }

    public MandatAncien addFonctions(FonctionAncien fonctionAncien) {
        this.fonctions.add(fonctionAncien);
        fonctionAncien.setMandatAncien(this);
        return this;
    }

    public MandatAncien removeFonctions(FonctionAncien fonctionAncien) {
        this.fonctions.remove(fonctionAncien);
        fonctionAncien.setMandatAncien(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MandatAncien)) {
            return false;
        }
        return id != null && id.equals(((MandatAncien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MandatAncien{" +
            "id=" + getId() +
            ", idType=" + getIdType() +
            ", libelle='" + getLibelle() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", circonscription='" + getCirconscription() + "'" +
            ", libelleAffichage='" + getLibelleAffichage() + "'" +
            "}";
    }
}

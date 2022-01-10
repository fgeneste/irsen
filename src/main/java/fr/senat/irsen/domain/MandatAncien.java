package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "circonscription")
    private String circonscription;

    @Column(name = "libelle_affichage")
    private String libelleAffichage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fonctions" }, allowSetters = true)
    private FonctionAncien fonctionAncien;

    @OneToMany(mappedBy = "mandatAncien")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandatAncien", "mandatEnCours", "senateur" }, allowSetters = true)
    private Set<Mandat> anciensMandats = new HashSet<>();

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

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public MandatAncien dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public MandatAncien dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
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

    public FonctionAncien getFonctionAncien() {
        return this.fonctionAncien;
    }

    public void setFonctionAncien(FonctionAncien fonctionAncien) {
        this.fonctionAncien = fonctionAncien;
    }

    public MandatAncien fonctionAncien(FonctionAncien fonctionAncien) {
        this.setFonctionAncien(fonctionAncien);
        return this;
    }

    public Set<Mandat> getAnciensMandats() {
        return this.anciensMandats;
    }

    public void setAnciensMandats(Set<Mandat> mandats) {
        if (this.anciensMandats != null) {
            this.anciensMandats.forEach(i -> i.setMandatAncien(null));
        }
        if (mandats != null) {
            mandats.forEach(i -> i.setMandatAncien(this));
        }
        this.anciensMandats = mandats;
    }

    public MandatAncien anciensMandats(Set<Mandat> mandats) {
        this.setAnciensMandats(mandats);
        return this;
    }

    public MandatAncien addAnciensMandats(Mandat mandat) {
        this.anciensMandats.add(mandat);
        mandat.setMandatAncien(this);
        return this;
    }

    public MandatAncien removeAnciensMandats(Mandat mandat) {
        this.anciensMandats.remove(mandat);
        mandat.setMandatAncien(null);
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

package fr.senat.irsen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtatCivil.
 */
@Entity
@Table(name = "etat_civil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtatCivil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "civilite")
    private String civilite;

    @Column(name = "titre")
    private String titre;

    @Column(name = "nom_famille")
    private String nomFamille;

    @Column(name = "nom_marital")
    private String nomMarital;

    @Column(name = "nom_usuel")
    private String nomUsuel;

    @Column(name = "prenoms")
    private String prenoms;

    @Column(name = "prenom_usuel")
    private String prenomUsuel;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "commune_naissance")
    private String communeNaissance;

    @Column(name = "profession")
    private String profession;

    @Column(name = "courriel")
    private String courriel;

    @Column(name = "courriel_2")
    private String courriel2;

    @JsonIgnoreProperties(value = { "etatCivil" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private DepartementNaissance departementNaissance;

    @JsonIgnoreProperties(value = { "etatCivil" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private PaysNaissance paysNaissance;

    @JsonIgnoreProperties(value = { "categorieSocioProf" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private CategorieSocioProf categorieSocioProf;

    @JsonIgnoreProperties(value = { "etatCivil" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TelephonePortable telephonePortable;

    @JsonIgnoreProperties(value = { "etatCivil" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TelephonePortable2 telephonePortable2;

    @JsonIgnoreProperties(value = { "etatCivil" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TelephoneFixe telephoneFixe;

    @JsonIgnoreProperties(value = { "etatCivil", "adresses", "mandats", "decorations" }, allowSetters = true)
    @OneToOne(mappedBy = "etatCivil")
    private Senateur senateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtatCivil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public EtatCivil matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCivilite() {
        return this.civilite;
    }

    public EtatCivil civilite(String civilite) {
        this.setCivilite(civilite);
        return this;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getTitre() {
        return this.titre;
    }

    public EtatCivil titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNomFamille() {
        return this.nomFamille;
    }

    public EtatCivil nomFamille(String nomFamille) {
        this.setNomFamille(nomFamille);
        return this;
    }

    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }

    public String getNomMarital() {
        return this.nomMarital;
    }

    public EtatCivil nomMarital(String nomMarital) {
        this.setNomMarital(nomMarital);
        return this;
    }

    public void setNomMarital(String nomMarital) {
        this.nomMarital = nomMarital;
    }

    public String getNomUsuel() {
        return this.nomUsuel;
    }

    public EtatCivil nomUsuel(String nomUsuel) {
        this.setNomUsuel(nomUsuel);
        return this;
    }

    public void setNomUsuel(String nomUsuel) {
        this.nomUsuel = nomUsuel;
    }

    public String getPrenoms() {
        return this.prenoms;
    }

    public EtatCivil prenoms(String prenoms) {
        this.setPrenoms(prenoms);
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getPrenomUsuel() {
        return this.prenomUsuel;
    }

    public EtatCivil prenomUsuel(String prenomUsuel) {
        this.setPrenomUsuel(prenomUsuel);
        return this;
    }

    public void setPrenomUsuel(String prenomUsuel) {
        this.prenomUsuel = prenomUsuel;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public EtatCivil dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCommuneNaissance() {
        return this.communeNaissance;
    }

    public EtatCivil communeNaissance(String communeNaissance) {
        this.setCommuneNaissance(communeNaissance);
        return this;
    }

    public void setCommuneNaissance(String communeNaissance) {
        this.communeNaissance = communeNaissance;
    }

    public String getProfession() {
        return this.profession;
    }

    public EtatCivil profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCourriel() {
        return this.courriel;
    }

    public EtatCivil courriel(String courriel) {
        this.setCourriel(courriel);
        return this;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getCourriel2() {
        return this.courriel2;
    }

    public EtatCivil courriel2(String courriel2) {
        this.setCourriel2(courriel2);
        return this;
    }

    public void setCourriel2(String courriel2) {
        this.courriel2 = courriel2;
    }

    public DepartementNaissance getDepartementNaissance() {
        return this.departementNaissance;
    }

    public void setDepartementNaissance(DepartementNaissance departementNaissance) {
        this.departementNaissance = departementNaissance;
    }

    public EtatCivil departementNaissance(DepartementNaissance departementNaissance) {
        this.setDepartementNaissance(departementNaissance);
        return this;
    }

    public PaysNaissance getPaysNaissance() {
        return this.paysNaissance;
    }

    public void setPaysNaissance(PaysNaissance paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public EtatCivil paysNaissance(PaysNaissance paysNaissance) {
        this.setPaysNaissance(paysNaissance);
        return this;
    }

    public CategorieSocioProf getCategorieSocioProf() {
        return this.categorieSocioProf;
    }

    public void setCategorieSocioProf(CategorieSocioProf categorieSocioProf) {
        this.categorieSocioProf = categorieSocioProf;
    }

    public EtatCivil categorieSocioProf(CategorieSocioProf categorieSocioProf) {
        this.setCategorieSocioProf(categorieSocioProf);
        return this;
    }

    public TelephonePortable getTelephonePortable() {
        return this.telephonePortable;
    }

    public void setTelephonePortable(TelephonePortable telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public EtatCivil telephonePortable(TelephonePortable telephonePortable) {
        this.setTelephonePortable(telephonePortable);
        return this;
    }

    public TelephonePortable2 getTelephonePortable2() {
        return this.telephonePortable2;
    }

    public void setTelephonePortable2(TelephonePortable2 telephonePortable2) {
        this.telephonePortable2 = telephonePortable2;
    }

    public EtatCivil telephonePortable2(TelephonePortable2 telephonePortable2) {
        this.setTelephonePortable2(telephonePortable2);
        return this;
    }

    public TelephoneFixe getTelephoneFixe() {
        return this.telephoneFixe;
    }

    public void setTelephoneFixe(TelephoneFixe telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public EtatCivil telephoneFixe(TelephoneFixe telephoneFixe) {
        this.setTelephoneFixe(telephoneFixe);
        return this;
    }

    public Senateur getSenateur() {
        return this.senateur;
    }

    public void setSenateur(Senateur senateur) {
        if (this.senateur != null) {
            this.senateur.setEtatCivil(null);
        }
        if (senateur != null) {
            senateur.setEtatCivil(this);
        }
        this.senateur = senateur;
    }

    public EtatCivil senateur(Senateur senateur) {
        this.setSenateur(senateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatCivil)) {
            return false;
        }
        return id != null && id.equals(((EtatCivil) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtatCivil{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", civilite='" + getCivilite() + "'" +
            ", titre='" + getTitre() + "'" +
            ", nomFamille='" + getNomFamille() + "'" +
            ", nomMarital='" + getNomMarital() + "'" +
            ", nomUsuel='" + getNomUsuel() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", prenomUsuel='" + getPrenomUsuel() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", communeNaissance='" + getCommuneNaissance() + "'" +
            ", profession='" + getProfession() + "'" +
            ", courriel='" + getCourriel() + "'" +
            ", courriel2='" + getCourriel2() + "'" +
            "}";
    }
}

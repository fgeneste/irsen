<template>
  <div>
    <b-dropdown-item to="/senateur">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.senateur')">Senateur</span>
    </b-dropdown-item>
    <b-dropdown-item to="/departement-naissance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.departementNaissance')">Departement Naissance</span>
    </b-dropdown-item>
    <b-dropdown-item to="/pays-naissance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.paysNaissance')">Pays Naissance</span>
    </b-dropdown-item>
    <b-dropdown-item to="/categorie-socio-prof">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.categorieSocioProf')">Categorie Socio Prof</span>
    </b-dropdown-item>
    <b-dropdown-item to="/etat-civil">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.etatCivil')">Etat Civil</span>
    </b-dropdown-item>
    <b-dropdown-item to="/adresse-fiscale">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.adresseFiscale')">Adresse Fiscale</span>
    </b-dropdown-item>
    <b-dropdown-item to="/adresse-postale">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.adressePostale')">Adresse Postale</span>
    </b-dropdown-item>
    <b-dropdown-item to="/adresse-postale-2">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.adressePostale2')">Adresse Postale 2</span>
    </b-dropdown-item>
    <b-dropdown-item to="/adresses">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.adresses')">Adresses</span>
    </b-dropdown-item>
    <b-dropdown-item to="/fonction-ancien">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.fonctionAncien')">Fonction Ancien</span>
    </b-dropdown-item>
    <b-dropdown-item to="/fonction-en-cours">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.fonctionEnCours')">Fonction En Cours</span>
    </b-dropdown-item>
    <b-dropdown-item to="/mandat">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.mandat')">Mandat</span>
    </b-dropdown-item>
    <b-dropdown-item to="/mandat-ancien">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.mandatAncien')">Mandat Ancien</span>
    </b-dropdown-item>
    <b-dropdown-item to="/mandat-en-cours">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.mandatEnCours')">Mandat En Cours</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>

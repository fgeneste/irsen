<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adressePostale2">
        <h2 class="jh-entity-heading" data-cy="adressePostale2DetailsHeading">
          <span v-text="$t('irsenApp.adressePostale2.detail.title')">AdressePostale2</span> {{ adressePostale2.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.bister')">Bister</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.bister }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.complement1')">Complement 1</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.complement1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.complement2')">Complement 2</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.complement2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.typeVoie')">Type Voie</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.typeVoie }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.voie')">Voie</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.voie }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.codePostal')">Code Postal</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.codePostal }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.ville')">Ville</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.ville }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.pays')">Pays</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.pays }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.affichageInternet')">Affichage Internet</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.affichageInternet }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.affichageIntranet')">Affichage Intranet</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.affichageIntranet }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="adressePostale2.id"
          :to="{ name: 'AdressePostale2Edit', params: { adressePostale2Id: adressePostale2.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./adresse-postale-2-details.component.ts"></script>

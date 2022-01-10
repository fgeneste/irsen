<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adresseFiscale">
        <h2 class="jh-entity-heading" data-cy="adresseFiscaleDetailsHeading">
          <span v-text="$t('irsenApp.adresseFiscale.detail.title')">AdresseFiscale</span> {{ adresseFiscale.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.label')">Label</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.label }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.voie')">Voie</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.voie }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.codePostal')">Code Postal</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.codePostal }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.ville')">Ville</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.ville }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.pays')">Pays</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.pays }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.localisation')">Localisation</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.localisation }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.modeManuel')">Mode Manuel</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.modeManuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresseFiscale.type')">Type</span>
          </dt>
          <dd>
            <span>{{ adresseFiscale.type }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="adresseFiscale.id"
          :to="{ name: 'AdresseFiscaleEdit', params: { adresseFiscaleId: adresseFiscale.id } }"
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

<script lang="ts" src="./adresse-fiscale-details.component.ts"></script>

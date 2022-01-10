<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adressePostale">
        <h2 class="jh-entity-heading" data-cy="adressePostaleDetailsHeading">
          <span v-text="$t('irsenApp.adressePostale.detail.title')">AdressePostale</span> {{ adressePostale.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.adressePostale.label')">Label</span>
          </dt>
          <dd>
            <span>{{ adressePostale.label }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ adressePostale.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.voie')">Voie</span>
          </dt>
          <dd>
            <span>{{ adressePostale.voie }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.codePostal')">Code Postal</span>
          </dt>
          <dd>
            <span>{{ adressePostale.codePostal }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.ville')">Ville</span>
          </dt>
          <dd>
            <span>{{ adressePostale.ville }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.pays')">Pays</span>
          </dt>
          <dd>
            <span>{{ adressePostale.pays }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.localisation')">Localisation</span>
          </dt>
          <dd>
            <span>{{ adressePostale.localisation }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.modeManuel')">Mode Manuel</span>
          </dt>
          <dd>
            <span>{{ adressePostale.modeManuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale.type')">Type</span>
          </dt>
          <dd>
            <span>{{ adressePostale.type }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="adressePostale.id"
          :to="{ name: 'AdressePostaleEdit', params: { adressePostaleId: adressePostale.id } }"
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

<script lang="ts" src="./adresse-postale-details.component.ts"></script>

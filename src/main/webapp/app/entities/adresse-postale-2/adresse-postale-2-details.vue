<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adressePostale2">
        <h2 class="jh-entity-heading" data-cy="adressePostale2DetailsHeading">
          <span v-text="$t('irsenApp.adressePostale2.detail.title')">AdressePostale2</span> {{ adressePostale2.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.label')">Label</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.label }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.numero }}</span>
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
            <span v-text="$t('irsenApp.adressePostale2.localisation')">Localisation</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.localisation }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.modeManuel')">Mode Manuel</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.modeManuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adressePostale2.type')">Type</span>
          </dt>
          <dd>
            <span>{{ adressePostale2.type }}</span>
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

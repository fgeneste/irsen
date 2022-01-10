<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="adresses">
        <h2 class="jh-entity-heading" data-cy="adressesDetailsHeading">
          <span v-text="$t('irsenApp.adresses.detail.title')">Adresses</span> {{ adresses.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.adresses.adresseFiscale')">Adresse Fiscale</span>
          </dt>
          <dd>
            <div v-if="adresses.adresseFiscale">
              <router-link :to="{ name: 'AdresseFiscaleView', params: { adresseFiscaleId: adresses.adresseFiscale.id } }">{{
                adresses.adresseFiscale.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresses.adressePostale')">Adresse Postale</span>
          </dt>
          <dd>
            <div v-if="adresses.adressePostale">
              <router-link :to="{ name: 'AdressePostaleView', params: { adressePostaleId: adresses.adressePostale.id } }">{{
                adresses.adressePostale.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.adresses.adressePostale2')">Adresse Postale 2</span>
          </dt>
          <dd>
            <div v-if="adresses.adressePostale2">
              <router-link :to="{ name: 'AdressePostale2View', params: { adressePostale2Id: adresses.adressePostale2.id } }">{{
                adresses.adressePostale2.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="adresses.id" :to="{ name: 'AdressesEdit', params: { adressesId: adresses.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./adresses-details.component.ts"></script>

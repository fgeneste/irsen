<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="senateur">
        <h2 class="jh-entity-heading" data-cy="senateurDetailsHeading">
          <span v-text="$t('irsenApp.senateur.detail.title')">Senateur</span> {{ senateur.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.senateur.etatCivil')">Etat Civil</span>
          </dt>
          <dd>
            <div v-if="senateur.etatCivil">
              <router-link :to="{ name: 'EtatCivilView', params: { etatCivilId: senateur.etatCivil.id } }">{{
                senateur.etatCivil.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.senateur.adresses')">Adresses</span>
          </dt>
          <dd>
            <div v-if="senateur.adresses">
              <router-link :to="{ name: 'AdressesView', params: { adressesId: senateur.adresses.id } }">{{
                senateur.adresses.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.senateur.mandats')">Mandats</span>
          </dt>
          <dd>
            <div v-if="senateur.mandats">
              <router-link :to="{ name: 'MandatView', params: { mandatId: senateur.mandats.id } }">{{ senateur.mandats.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="senateur.id" :to="{ name: 'SenateurEdit', params: { senateurId: senateur.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./senateur-details.component.ts"></script>

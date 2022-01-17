<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="paysNaissance">
        <h2 class="jh-entity-heading" data-cy="paysNaissanceDetailsHeading">
          <span v-text="$t('irsenApp.paysNaissance.detail.title')">PaysNaissance</span> {{ paysNaissance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.paysNaissance.code')">Code</span>
          </dt>
          <dd>
            <span>{{ paysNaissance.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.paysNaissance.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ paysNaissance.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.paysNaissance.avecConseilDepartemental')">Avec Conseil Departemental</span>
          </dt>
          <dd>
            <span>{{ paysNaissance.avecConseilDepartemental }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.paysNaissance.article')">Article</span>
          </dt>
          <dd>
            <span>{{ paysNaissance.article }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="paysNaissance.id"
          :to="{ name: 'PaysNaissanceEdit', params: { paysNaissanceId: paysNaissance.id } }"
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

<script lang="ts" src="./pays-naissance-details.component.ts"></script>

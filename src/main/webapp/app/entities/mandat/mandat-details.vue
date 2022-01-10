<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mandat">
        <h2 class="jh-entity-heading" data-cy="mandatDetailsHeading">
          <span v-text="$t('irsenApp.mandat.detail.title')">Mandat</span> {{ mandat.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.mandat.mandatAncien')">Mandat Ancien</span>
          </dt>
          <dd>
            <div v-if="mandat.mandatAncien">
              <router-link :to="{ name: 'MandatAncienView', params: { mandatAncienId: mandat.mandatAncien.id } }">{{
                mandat.mandatAncien.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandat.mandatEnCours')">Mandat En Cours</span>
          </dt>
          <dd>
            <div v-if="mandat.mandatEnCours">
              <router-link :to="{ name: 'MandatEnCoursView', params: { mandatEnCoursId: mandat.mandatEnCours.id } }">{{
                mandat.mandatEnCours.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="mandat.id" :to="{ name: 'MandatEdit', params: { mandatId: mandat.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./mandat-details.component.ts"></script>

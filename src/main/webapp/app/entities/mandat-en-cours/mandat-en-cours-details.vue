<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mandatEnCours">
        <h2 class="jh-entity-heading" data-cy="mandatEnCoursDetailsHeading">
          <span v-text="$t('irsenApp.mandatEnCours.detail.title')">MandatEnCours</span> {{ mandatEnCours.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.idType')">Id Type</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.idType }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.idFonction')">Id Fonction</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.idFonction }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.code')">Code</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.libelleFonction')">Libelle Fonction</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.libelleFonction }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.circonscription')">Circonscription</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.circonscription }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.depuis')">Depuis</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.depuis }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.dateElection')">Date Election</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.dateElection }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.population')">Population</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.population }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.libelleAffichage')">Libelle Affichage</span>
          </dt>
          <dd>
            <span>{{ mandatEnCours.libelleAffichage }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatEnCours.fonctionEnCours')">Fonction En Cours</span>
          </dt>
          <dd>
            <div v-if="mandatEnCours.fonctionEnCours">
              <router-link :to="{ name: 'FonctionEnCoursView', params: { fonctionEnCoursId: mandatEnCours.fonctionEnCours.id } }">{{
                mandatEnCours.fonctionEnCours.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="mandatEnCours.id"
          :to="{ name: 'MandatEnCoursEdit', params: { mandatEnCoursId: mandatEnCours.id } }"
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

<script lang="ts" src="./mandat-en-cours-details.component.ts"></script>

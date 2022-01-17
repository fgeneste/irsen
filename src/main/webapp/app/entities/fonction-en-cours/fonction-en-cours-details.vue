<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fonctionEnCours">
        <h2 class="jh-entity-heading" data-cy="fonctionEnCoursDetailsHeading">
          <span v-text="$t('irsenApp.fonctionEnCours.detail.title')">FonctionEnCours</span> {{ fonctionEnCours.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.fonctionEnCours.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ fonctionEnCours.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.fonctionEnCours.dateDebut')">Date Debut</span>
          </dt>
          <dd>
            <span>{{ fonctionEnCours.dateDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.fonctionEnCours.dateFin')">Date Fin</span>
          </dt>
          <dd>
            <span>{{ fonctionEnCours.dateFin }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.fonctionEnCours.mandatEnCours')">Mandat En Cours</span>
          </dt>
          <dd>
            <div v-if="fonctionEnCours.mandatEnCours">
              <router-link :to="{ name: 'MandatEnCoursView', params: { mandatEnCoursId: fonctionEnCours.mandatEnCours.id } }">{{
                fonctionEnCours.mandatEnCours.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="fonctionEnCours.id"
          :to="{ name: 'FonctionEnCoursEdit', params: { fonctionEnCoursId: fonctionEnCours.id } }"
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

<script lang="ts" src="./fonction-en-cours-details.component.ts"></script>

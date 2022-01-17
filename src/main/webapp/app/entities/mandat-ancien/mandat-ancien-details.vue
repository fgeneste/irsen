<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="mandatAncien">
        <h2 class="jh-entity-heading" data-cy="mandatAncienDetailsHeading">
          <span v-text="$t('irsenApp.mandatAncien.detail.title')">MandatAncien</span> {{ mandatAncien.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.idType')">Id Type</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.idType }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.dateDebut')">Date Debut</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.dateDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.dateFin')">Date Fin</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.dateFin }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.circonscription')">Circonscription</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.circonscription }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.libelleAffichage')">Libelle Affichage</span>
          </dt>
          <dd>
            <span>{{ mandatAncien.libelleAffichage }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.mandatAncien.mandat')">Mandat</span>
          </dt>
          <dd>
            <div v-if="mandatAncien.mandat">
              <router-link :to="{ name: 'MandatView', params: { mandatId: mandatAncien.mandat.id } }">{{
                mandatAncien.mandat.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="mandatAncien.id"
          :to="{ name: 'MandatAncienEdit', params: { mandatAncienId: mandatAncien.id } }"
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

<script lang="ts" src="./mandat-ancien-details.component.ts"></script>

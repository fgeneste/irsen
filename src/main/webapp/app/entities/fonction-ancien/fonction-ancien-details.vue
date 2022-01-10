<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fonctionAncien">
        <h2 class="jh-entity-heading" data-cy="fonctionAncienDetailsHeading">
          <span v-text="$t('irsenApp.fonctionAncien.detail.title')">FonctionAncien</span> {{ fonctionAncien.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.fonctionAncien.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ fonctionAncien.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.fonctionAncien.dateDebut')">Date Debut</span>
          </dt>
          <dd>
            <span>{{ fonctionAncien.dateDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.fonctionAncien.dateFin')">Date Fin</span>
          </dt>
          <dd>
            <span>{{ fonctionAncien.dateFin }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="fonctionAncien.id"
          :to="{ name: 'FonctionAncienEdit', params: { fonctionAncienId: fonctionAncien.id } }"
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

<script lang="ts" src="./fonction-ancien-details.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="departementNaissance">
        <h2 class="jh-entity-heading" data-cy="departementNaissanceDetailsHeading">
          <span v-text="$t('irsenApp.departementNaissance.detail.title')">DepartementNaissance</span> {{ departementNaissance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.code')">Code</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.avecConseilDepartemental')">Avec Conseil Departemental</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.avecConseilDepartemental }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.article')">Article</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.article }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.codeSirpas')">Code Sirpas</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.codeSirpas }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.codeComparaison')">Code Comparaison</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.codeComparaison }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.libelleComplet')">Libelle Complet</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.libelleComplet }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.departementNaissance.libelleAvecArticle')">Libelle Avec Article</span>
          </dt>
          <dd>
            <span>{{ departementNaissance.libelleAvecArticle }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="departementNaissance.id"
          :to="{ name: 'DepartementNaissanceEdit', params: { departementNaissanceId: departementNaissance.id } }"
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

<script lang="ts" src="./departement-naissance-details.component.ts"></script>

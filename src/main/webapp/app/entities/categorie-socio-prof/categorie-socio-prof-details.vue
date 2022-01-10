<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="categorieSocioProf">
        <h2 class="jh-entity-heading" data-cy="categorieSocioProfDetailsHeading">
          <span v-text="$t('irsenApp.categorieSocioProf.detail.title')">CategorieSocioProf</span> {{ categorieSocioProf.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.categorieSocioProf.code')">Code</span>
          </dt>
          <dd>
            <span>{{ categorieSocioProf.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.categorieSocioProf.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ categorieSocioProf.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.categorieSocioProf.libelleComplet')">Libelle Complet</span>
          </dt>
          <dd>
            <span>{{ categorieSocioProf.libelleComplet }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="categorieSocioProf.id"
          :to="{ name: 'CategorieSocioProfEdit', params: { categorieSocioProfId: categorieSocioProf.id } }"
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

<script lang="ts" src="./categorie-socio-prof-details.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.departementNaissance.home.createOrEditLabel"
          data-cy="DepartementNaissanceCreateUpdateHeading"
          v-text="$t('irsenApp.departementNaissance.home.createOrEditLabel')"
        >
          Create or edit a DepartementNaissance
        </h2>
        <div>
          <div class="form-group" v-if="departementNaissance.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="departementNaissance.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.departementNaissance.code')" for="departement-naissance-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="departement-naissance-code"
              data-cy="code"
              :class="{ valid: !$v.departementNaissance.code.$invalid, invalid: $v.departementNaissance.code.$invalid }"
              v-model="$v.departementNaissance.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.departementNaissance.libelle')" for="departement-naissance-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="departement-naissance-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.departementNaissance.libelle.$invalid, invalid: $v.departementNaissance.libelle.$invalid }"
              v-model="$v.departementNaissance.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.departementNaissance.avecConseilDepartemental')"
              for="departement-naissance-avecConseilDepartemental"
              >Avec Conseil Departemental</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="avecConseilDepartemental"
              id="departement-naissance-avecConseilDepartemental"
              data-cy="avecConseilDepartemental"
              :class="{
                valid: !$v.departementNaissance.avecConseilDepartemental.$invalid,
                invalid: $v.departementNaissance.avecConseilDepartemental.$invalid,
              }"
              v-model="$v.departementNaissance.avecConseilDepartemental.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.departementNaissance.article')" for="departement-naissance-article"
              >Article</label
            >
            <input
              type="text"
              class="form-control"
              name="article"
              id="departement-naissance-article"
              data-cy="article"
              :class="{ valid: !$v.departementNaissance.article.$invalid, invalid: $v.departementNaissance.article.$invalid }"
              v-model="$v.departementNaissance.article.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.departementNaissance.codeSirpas')" for="departement-naissance-codeSirpas"
              >Code Sirpas</label
            >
            <input
              type="text"
              class="form-control"
              name="codeSirpas"
              id="departement-naissance-codeSirpas"
              data-cy="codeSirpas"
              :class="{ valid: !$v.departementNaissance.codeSirpas.$invalid, invalid: $v.departementNaissance.codeSirpas.$invalid }"
              v-model="$v.departementNaissance.codeSirpas.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.departementNaissance.codeComparaison')"
              for="departement-naissance-codeComparaison"
              >Code Comparaison</label
            >
            <input
              type="text"
              class="form-control"
              name="codeComparaison"
              id="departement-naissance-codeComparaison"
              data-cy="codeComparaison"
              :class="{
                valid: !$v.departementNaissance.codeComparaison.$invalid,
                invalid: $v.departementNaissance.codeComparaison.$invalid,
              }"
              v-model="$v.departementNaissance.codeComparaison.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.departementNaissance.libelleComplet')"
              for="departement-naissance-libelleComplet"
              >Libelle Complet</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleComplet"
              id="departement-naissance-libelleComplet"
              data-cy="libelleComplet"
              :class="{ valid: !$v.departementNaissance.libelleComplet.$invalid, invalid: $v.departementNaissance.libelleComplet.$invalid }"
              v-model="$v.departementNaissance.libelleComplet.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.departementNaissance.libelleAvecArticle')"
              for="departement-naissance-libelleAvecArticle"
              >Libelle Avec Article</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleAvecArticle"
              id="departement-naissance-libelleAvecArticle"
              data-cy="libelleAvecArticle"
              :class="{
                valid: !$v.departementNaissance.libelleAvecArticle.$invalid,
                invalid: $v.departementNaissance.libelleAvecArticle.$invalid,
              }"
              v-model="$v.departementNaissance.libelleAvecArticle.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.departementNaissance.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./departement-naissance-update.component.ts"></script>

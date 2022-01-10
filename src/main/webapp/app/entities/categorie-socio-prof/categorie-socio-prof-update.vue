<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.categorieSocioProf.home.createOrEditLabel"
          data-cy="CategorieSocioProfCreateUpdateHeading"
          v-text="$t('irsenApp.categorieSocioProf.home.createOrEditLabel')"
        >
          Create or edit a CategorieSocioProf
        </h2>
        <div>
          <div class="form-group" v-if="categorieSocioProf.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="categorieSocioProf.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.categorieSocioProf.code')" for="categorie-socio-prof-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="categorie-socio-prof-code"
              data-cy="code"
              :class="{ valid: !$v.categorieSocioProf.code.$invalid, invalid: $v.categorieSocioProf.code.$invalid }"
              v-model="$v.categorieSocioProf.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.categorieSocioProf.libelle')" for="categorie-socio-prof-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="categorie-socio-prof-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.categorieSocioProf.libelle.$invalid, invalid: $v.categorieSocioProf.libelle.$invalid }"
              v-model="$v.categorieSocioProf.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.categorieSocioProf.libelleComplet')"
              for="categorie-socio-prof-libelleComplet"
              >Libelle Complet</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleComplet"
              id="categorie-socio-prof-libelleComplet"
              data-cy="libelleComplet"
              :class="{ valid: !$v.categorieSocioProf.libelleComplet.$invalid, invalid: $v.categorieSocioProf.libelleComplet.$invalid }"
              v-model="$v.categorieSocioProf.libelleComplet.$model"
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
            :disabled="$v.categorieSocioProf.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./categorie-socio-prof-update.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.paysNaissance.home.createOrEditLabel"
          data-cy="PaysNaissanceCreateUpdateHeading"
          v-text="$t('irsenApp.paysNaissance.home.createOrEditLabel')"
        >
          Create or edit a PaysNaissance
        </h2>
        <div>
          <div class="form-group" v-if="paysNaissance.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="paysNaissance.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.paysNaissance.code')" for="pays-naissance-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="pays-naissance-code"
              data-cy="code"
              :class="{ valid: !$v.paysNaissance.code.$invalid, invalid: $v.paysNaissance.code.$invalid }"
              v-model="$v.paysNaissance.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.paysNaissance.libelle')" for="pays-naissance-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="pays-naissance-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.paysNaissance.libelle.$invalid, invalid: $v.paysNaissance.libelle.$invalid }"
              v-model="$v.paysNaissance.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.paysNaissance.avecConseilDepartemental')"
              for="pays-naissance-avecConseilDepartemental"
              >Avec Conseil Departemental</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="avecConseilDepartemental"
              id="pays-naissance-avecConseilDepartemental"
              data-cy="avecConseilDepartemental"
              :class="{
                valid: !$v.paysNaissance.avecConseilDepartemental.$invalid,
                invalid: $v.paysNaissance.avecConseilDepartemental.$invalid,
              }"
              v-model="$v.paysNaissance.avecConseilDepartemental.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.paysNaissance.article')" for="pays-naissance-article">Article</label>
            <input
              type="text"
              class="form-control"
              name="article"
              id="pays-naissance-article"
              data-cy="article"
              :class="{ valid: !$v.paysNaissance.article.$invalid, invalid: $v.paysNaissance.article.$invalid }"
              v-model="$v.paysNaissance.article.$model"
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
            :disabled="$v.paysNaissance.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./pays-naissance-update.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.fonctionAncien.home.createOrEditLabel"
          data-cy="FonctionAncienCreateUpdateHeading"
          v-text="$t('irsenApp.fonctionAncien.home.createOrEditLabel')"
        >
          Create or edit a FonctionAncien
        </h2>
        <div>
          <div class="form-group" v-if="fonctionAncien.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fonctionAncien.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionAncien.libelle')" for="fonction-ancien-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="fonction-ancien-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.fonctionAncien.libelle.$invalid, invalid: $v.fonctionAncien.libelle.$invalid }"
              v-model="$v.fonctionAncien.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionAncien.dateDebut')" for="fonction-ancien-dateDebut"
              >Date Debut</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="fonction-ancien-dateDebut"
                  v-model="$v.fonctionAncien.dateDebut.$model"
                  name="dateDebut"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="fonction-ancien-dateDebut"
                data-cy="dateDebut"
                type="text"
                class="form-control"
                name="dateDebut"
                :class="{ valid: !$v.fonctionAncien.dateDebut.$invalid, invalid: $v.fonctionAncien.dateDebut.$invalid }"
                v-model="$v.fonctionAncien.dateDebut.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionAncien.dateFin')" for="fonction-ancien-dateFin">Date Fin</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="fonction-ancien-dateFin"
                  v-model="$v.fonctionAncien.dateFin.$model"
                  name="dateFin"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="fonction-ancien-dateFin"
                data-cy="dateFin"
                type="text"
                class="form-control"
                name="dateFin"
                :class="{ valid: !$v.fonctionAncien.dateFin.$invalid, invalid: $v.fonctionAncien.dateFin.$invalid }"
                v-model="$v.fonctionAncien.dateFin.$model"
              />
            </b-input-group>
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
            :disabled="$v.fonctionAncien.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./fonction-ancien-update.component.ts"></script>

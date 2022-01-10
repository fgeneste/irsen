<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.fonctionEnCours.home.createOrEditLabel"
          data-cy="FonctionEnCoursCreateUpdateHeading"
          v-text="$t('irsenApp.fonctionEnCours.home.createOrEditLabel')"
        >
          Create or edit a FonctionEnCours
        </h2>
        <div>
          <div class="form-group" v-if="fonctionEnCours.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fonctionEnCours.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.libelle')" for="fonction-en-cours-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="fonction-en-cours-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.fonctionEnCours.libelle.$invalid, invalid: $v.fonctionEnCours.libelle.$invalid }"
              v-model="$v.fonctionEnCours.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.dateDebut')" for="fonction-en-cours-dateDebut"
              >Date Debut</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="fonction-en-cours-dateDebut"
                  v-model="$v.fonctionEnCours.dateDebut.$model"
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
                id="fonction-en-cours-dateDebut"
                data-cy="dateDebut"
                type="text"
                class="form-control"
                name="dateDebut"
                :class="{ valid: !$v.fonctionEnCours.dateDebut.$invalid, invalid: $v.fonctionEnCours.dateDebut.$invalid }"
                v-model="$v.fonctionEnCours.dateDebut.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.dateFin')" for="fonction-en-cours-dateFin"
              >Date Fin</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="fonction-en-cours-dateFin"
                  v-model="$v.fonctionEnCours.dateFin.$model"
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
                id="fonction-en-cours-dateFin"
                data-cy="dateFin"
                type="text"
                class="form-control"
                name="dateFin"
                :class="{ valid: !$v.fonctionEnCours.dateFin.$invalid, invalid: $v.fonctionEnCours.dateFin.$invalid }"
                v-model="$v.fonctionEnCours.dateFin.$model"
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
            :disabled="$v.fonctionEnCours.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./fonction-en-cours-update.component.ts"></script>

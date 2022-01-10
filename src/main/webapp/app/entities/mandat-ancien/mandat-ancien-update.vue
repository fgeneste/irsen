<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.mandatAncien.home.createOrEditLabel"
          data-cy="MandatAncienCreateUpdateHeading"
          v-text="$t('irsenApp.mandatAncien.home.createOrEditLabel')"
        >
          Create or edit a MandatAncien
        </h2>
        <div>
          <div class="form-group" v-if="mandatAncien.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="mandatAncien.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.idType')" for="mandat-ancien-idType">Id Type</label>
            <input
              type="number"
              class="form-control"
              name="idType"
              id="mandat-ancien-idType"
              data-cy="idType"
              :class="{ valid: !$v.mandatAncien.idType.$invalid, invalid: $v.mandatAncien.idType.$invalid }"
              v-model.number="$v.mandatAncien.idType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.libelle')" for="mandat-ancien-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="mandat-ancien-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.mandatAncien.libelle.$invalid, invalid: $v.mandatAncien.libelle.$invalid }"
              v-model="$v.mandatAncien.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.dateDebut')" for="mandat-ancien-dateDebut"
              >Date Debut</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="mandat-ancien-dateDebut"
                  v-model="$v.mandatAncien.dateDebut.$model"
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
                id="mandat-ancien-dateDebut"
                data-cy="dateDebut"
                type="text"
                class="form-control"
                name="dateDebut"
                :class="{ valid: !$v.mandatAncien.dateDebut.$invalid, invalid: $v.mandatAncien.dateDebut.$invalid }"
                v-model="$v.mandatAncien.dateDebut.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.dateFin')" for="mandat-ancien-dateFin">Date Fin</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="mandat-ancien-dateFin"
                  v-model="$v.mandatAncien.dateFin.$model"
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
                id="mandat-ancien-dateFin"
                data-cy="dateFin"
                type="text"
                class="form-control"
                name="dateFin"
                :class="{ valid: !$v.mandatAncien.dateFin.$invalid, invalid: $v.mandatAncien.dateFin.$invalid }"
                v-model="$v.mandatAncien.dateFin.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.circonscription')" for="mandat-ancien-circonscription"
              >Circonscription</label
            >
            <input
              type="text"
              class="form-control"
              name="circonscription"
              id="mandat-ancien-circonscription"
              data-cy="circonscription"
              :class="{ valid: !$v.mandatAncien.circonscription.$invalid, invalid: $v.mandatAncien.circonscription.$invalid }"
              v-model="$v.mandatAncien.circonscription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.libelleAffichage')" for="mandat-ancien-libelleAffichage"
              >Libelle Affichage</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleAffichage"
              id="mandat-ancien-libelleAffichage"
              data-cy="libelleAffichage"
              :class="{ valid: !$v.mandatAncien.libelleAffichage.$invalid, invalid: $v.mandatAncien.libelleAffichage.$invalid }"
              v-model="$v.mandatAncien.libelleAffichage.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.fonctionAncien')" for="mandat-ancien-fonctionAncien"
              >Fonction Ancien</label
            >
            <select
              class="form-control"
              id="mandat-ancien-fonctionAncien"
              data-cy="fonctionAncien"
              name="fonctionAncien"
              v-model="mandatAncien.fonctionAncien"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  mandatAncien.fonctionAncien && fonctionAncienOption.id === mandatAncien.fonctionAncien.id
                    ? mandatAncien.fonctionAncien
                    : fonctionAncienOption
                "
                v-for="fonctionAncienOption in fonctionAnciens"
                :key="fonctionAncienOption.id"
              >
                {{ fonctionAncienOption.id }}
              </option>
            </select>
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
            :disabled="$v.mandatAncien.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./mandat-ancien-update.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.mandatEnCours.home.createOrEditLabel"
          data-cy="MandatEnCoursCreateUpdateHeading"
          v-text="$t('irsenApp.mandatEnCours.home.createOrEditLabel')"
        >
          Create or edit a MandatEnCours
        </h2>
        <div>
          <div class="form-group" v-if="mandatEnCours.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="mandatEnCours.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.idType')" for="mandat-en-cours-idType">Id Type</label>
            <input
              type="number"
              class="form-control"
              name="idType"
              id="mandat-en-cours-idType"
              data-cy="idType"
              :class="{ valid: !$v.mandatEnCours.idType.$invalid, invalid: $v.mandatEnCours.idType.$invalid }"
              v-model.number="$v.mandatEnCours.idType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.idFonction')" for="mandat-en-cours-idFonction"
              >Id Fonction</label
            >
            <input
              type="number"
              class="form-control"
              name="idFonction"
              id="mandat-en-cours-idFonction"
              data-cy="idFonction"
              :class="{ valid: !$v.mandatEnCours.idFonction.$invalid, invalid: $v.mandatEnCours.idFonction.$invalid }"
              v-model.number="$v.mandatEnCours.idFonction.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.code')" for="mandat-en-cours-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="mandat-en-cours-code"
              data-cy="code"
              :class="{ valid: !$v.mandatEnCours.code.$invalid, invalid: $v.mandatEnCours.code.$invalid }"
              v-model="$v.mandatEnCours.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.libelle')" for="mandat-en-cours-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="mandat-en-cours-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.mandatEnCours.libelle.$invalid, invalid: $v.mandatEnCours.libelle.$invalid }"
              v-model="$v.mandatEnCours.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.libelleFonction')" for="mandat-en-cours-libelleFonction"
              >Libelle Fonction</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleFonction"
              id="mandat-en-cours-libelleFonction"
              data-cy="libelleFonction"
              :class="{ valid: !$v.mandatEnCours.libelleFonction.$invalid, invalid: $v.mandatEnCours.libelleFonction.$invalid }"
              v-model="$v.mandatEnCours.libelleFonction.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.circonscription')" for="mandat-en-cours-circonscription"
              >Circonscription</label
            >
            <input
              type="text"
              class="form-control"
              name="circonscription"
              id="mandat-en-cours-circonscription"
              data-cy="circonscription"
              :class="{ valid: !$v.mandatEnCours.circonscription.$invalid, invalid: $v.mandatEnCours.circonscription.$invalid }"
              v-model="$v.mandatEnCours.circonscription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.depuis')" for="mandat-en-cours-depuis">Depuis</label>
            <input
              type="text"
              class="form-control"
              name="depuis"
              id="mandat-en-cours-depuis"
              data-cy="depuis"
              :class="{ valid: !$v.mandatEnCours.depuis.$invalid, invalid: $v.mandatEnCours.depuis.$invalid }"
              v-model="$v.mandatEnCours.depuis.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.dateElection')" for="mandat-en-cours-dateElection"
              >Date Election</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="mandat-en-cours-dateElection"
                  v-model="$v.mandatEnCours.dateElection.$model"
                  name="dateElection"
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
                id="mandat-en-cours-dateElection"
                data-cy="dateElection"
                type="text"
                class="form-control"
                name="dateElection"
                :class="{ valid: !$v.mandatEnCours.dateElection.$invalid, invalid: $v.mandatEnCours.dateElection.$invalid }"
                v-model="$v.mandatEnCours.dateElection.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.population')" for="mandat-en-cours-population"
              >Population</label
            >
            <input
              type="text"
              class="form-control"
              name="population"
              id="mandat-en-cours-population"
              data-cy="population"
              :class="{ valid: !$v.mandatEnCours.population.$invalid, invalid: $v.mandatEnCours.population.$invalid }"
              v-model="$v.mandatEnCours.population.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.libelleAffichage')" for="mandat-en-cours-libelleAffichage"
              >Libelle Affichage</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleAffichage"
              id="mandat-en-cours-libelleAffichage"
              data-cy="libelleAffichage"
              :class="{ valid: !$v.mandatEnCours.libelleAffichage.$invalid, invalid: $v.mandatEnCours.libelleAffichage.$invalid }"
              v-model="$v.mandatEnCours.libelleAffichage.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatEnCours.fonctionEnCours')" for="mandat-en-cours-fonctionEnCours"
              >Fonction En Cours</label
            >
            <select
              class="form-control"
              id="mandat-en-cours-fonctionEnCours"
              data-cy="fonctionEnCours"
              name="fonctionEnCours"
              v-model="mandatEnCours.fonctionEnCours"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  mandatEnCours.fonctionEnCours && fonctionEnCoursOption.id === mandatEnCours.fonctionEnCours.id
                    ? mandatEnCours.fonctionEnCours
                    : fonctionEnCoursOption
                "
                v-for="fonctionEnCoursOption in fonctionEnCours"
                :key="fonctionEnCoursOption.id"
              >
                {{ fonctionEnCoursOption.id }}
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
            :disabled="$v.mandatEnCours.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./mandat-en-cours-update.component.ts"></script>

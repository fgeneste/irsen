<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.adresseFiscale.home.createOrEditLabel"
          data-cy="AdresseFiscaleCreateUpdateHeading"
          v-text="$t('irsenApp.adresseFiscale.home.createOrEditLabel')"
        >
          Create or edit a AdresseFiscale
        </h2>
        <div>
          <div class="form-group" v-if="adresseFiscale.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="adresseFiscale.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.label')" for="adresse-fiscale-label">Label</label>
            <input
              type="text"
              class="form-control"
              name="label"
              id="adresse-fiscale-label"
              data-cy="label"
              :class="{ valid: !$v.adresseFiscale.label.$invalid, invalid: $v.adresseFiscale.label.$invalid }"
              v-model="$v.adresseFiscale.label.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.numero')" for="adresse-fiscale-numero">Numero</label>
            <input
              type="text"
              class="form-control"
              name="numero"
              id="adresse-fiscale-numero"
              data-cy="numero"
              :class="{ valid: !$v.adresseFiscale.numero.$invalid, invalid: $v.adresseFiscale.numero.$invalid }"
              v-model="$v.adresseFiscale.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.voie')" for="adresse-fiscale-voie">Voie</label>
            <input
              type="text"
              class="form-control"
              name="voie"
              id="adresse-fiscale-voie"
              data-cy="voie"
              :class="{ valid: !$v.adresseFiscale.voie.$invalid, invalid: $v.adresseFiscale.voie.$invalid }"
              v-model="$v.adresseFiscale.voie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.codePostal')" for="adresse-fiscale-codePostal"
              >Code Postal</label
            >
            <input
              type="text"
              class="form-control"
              name="codePostal"
              id="adresse-fiscale-codePostal"
              data-cy="codePostal"
              :class="{ valid: !$v.adresseFiscale.codePostal.$invalid, invalid: $v.adresseFiscale.codePostal.$invalid }"
              v-model="$v.adresseFiscale.codePostal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.ville')" for="adresse-fiscale-ville">Ville</label>
            <input
              type="text"
              class="form-control"
              name="ville"
              id="adresse-fiscale-ville"
              data-cy="ville"
              :class="{ valid: !$v.adresseFiscale.ville.$invalid, invalid: $v.adresseFiscale.ville.$invalid }"
              v-model="$v.adresseFiscale.ville.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.pays')" for="adresse-fiscale-pays">Pays</label>
            <input
              type="text"
              class="form-control"
              name="pays"
              id="adresse-fiscale-pays"
              data-cy="pays"
              :class="{ valid: !$v.adresseFiscale.pays.$invalid, invalid: $v.adresseFiscale.pays.$invalid }"
              v-model="$v.adresseFiscale.pays.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.localisation')" for="adresse-fiscale-localisation"
              >Localisation</label
            >
            <input
              type="text"
              class="form-control"
              name="localisation"
              id="adresse-fiscale-localisation"
              data-cy="localisation"
              :class="{ valid: !$v.adresseFiscale.localisation.$invalid, invalid: $v.adresseFiscale.localisation.$invalid }"
              v-model="$v.adresseFiscale.localisation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.modeManuel')" for="adresse-fiscale-modeManuel"
              >Mode Manuel</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="modeManuel"
              id="adresse-fiscale-modeManuel"
              data-cy="modeManuel"
              :class="{ valid: !$v.adresseFiscale.modeManuel.$invalid, invalid: $v.adresseFiscale.modeManuel.$invalid }"
              v-model="$v.adresseFiscale.modeManuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.type')" for="adresse-fiscale-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="adresse-fiscale-type"
              data-cy="type"
              :class="{ valid: !$v.adresseFiscale.type.$invalid, invalid: $v.adresseFiscale.type.$invalid }"
              v-model="$v.adresseFiscale.type.$model"
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
            :disabled="$v.adresseFiscale.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./adresse-fiscale-update.component.ts"></script>

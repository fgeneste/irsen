<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.adressePostale.home.createOrEditLabel"
          data-cy="AdressePostaleCreateUpdateHeading"
          v-text="$t('irsenApp.adressePostale.home.createOrEditLabel')"
        >
          Create or edit a AdressePostale
        </h2>
        <div>
          <div class="form-group" v-if="adressePostale.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="adressePostale.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.label')" for="adresse-postale-label">Label</label>
            <input
              type="text"
              class="form-control"
              name="label"
              id="adresse-postale-label"
              data-cy="label"
              :class="{ valid: !$v.adressePostale.label.$invalid, invalid: $v.adressePostale.label.$invalid }"
              v-model="$v.adressePostale.label.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.numero')" for="adresse-postale-numero">Numero</label>
            <input
              type="text"
              class="form-control"
              name="numero"
              id="adresse-postale-numero"
              data-cy="numero"
              :class="{ valid: !$v.adressePostale.numero.$invalid, invalid: $v.adressePostale.numero.$invalid }"
              v-model="$v.adressePostale.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.voie')" for="adresse-postale-voie">Voie</label>
            <input
              type="text"
              class="form-control"
              name="voie"
              id="adresse-postale-voie"
              data-cy="voie"
              :class="{ valid: !$v.adressePostale.voie.$invalid, invalid: $v.adressePostale.voie.$invalid }"
              v-model="$v.adressePostale.voie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.codePostal')" for="adresse-postale-codePostal"
              >Code Postal</label
            >
            <input
              type="text"
              class="form-control"
              name="codePostal"
              id="adresse-postale-codePostal"
              data-cy="codePostal"
              :class="{ valid: !$v.adressePostale.codePostal.$invalid, invalid: $v.adressePostale.codePostal.$invalid }"
              v-model="$v.adressePostale.codePostal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.ville')" for="adresse-postale-ville">Ville</label>
            <input
              type="text"
              class="form-control"
              name="ville"
              id="adresse-postale-ville"
              data-cy="ville"
              :class="{ valid: !$v.adressePostale.ville.$invalid, invalid: $v.adressePostale.ville.$invalid }"
              v-model="$v.adressePostale.ville.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.pays')" for="adresse-postale-pays">Pays</label>
            <input
              type="text"
              class="form-control"
              name="pays"
              id="adresse-postale-pays"
              data-cy="pays"
              :class="{ valid: !$v.adressePostale.pays.$invalid, invalid: $v.adressePostale.pays.$invalid }"
              v-model="$v.adressePostale.pays.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.localisation')" for="adresse-postale-localisation"
              >Localisation</label
            >
            <input
              type="text"
              class="form-control"
              name="localisation"
              id="adresse-postale-localisation"
              data-cy="localisation"
              :class="{ valid: !$v.adressePostale.localisation.$invalid, invalid: $v.adressePostale.localisation.$invalid }"
              v-model="$v.adressePostale.localisation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.modeManuel')" for="adresse-postale-modeManuel"
              >Mode Manuel</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="modeManuel"
              id="adresse-postale-modeManuel"
              data-cy="modeManuel"
              :class="{ valid: !$v.adressePostale.modeManuel.$invalid, invalid: $v.adressePostale.modeManuel.$invalid }"
              v-model="$v.adressePostale.modeManuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.type')" for="adresse-postale-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="adresse-postale-type"
              data-cy="type"
              :class="{ valid: !$v.adressePostale.type.$invalid, invalid: $v.adressePostale.type.$invalid }"
              v-model="$v.adressePostale.type.$model"
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
            :disabled="$v.adressePostale.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./adresse-postale-update.component.ts"></script>

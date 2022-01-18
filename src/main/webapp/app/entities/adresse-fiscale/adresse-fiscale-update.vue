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
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.bister')" for="adresse-fiscale-bister">Bister</label>
            <input
              type="text"
              class="form-control"
              name="bister"
              id="adresse-fiscale-bister"
              data-cy="bister"
              :class="{ valid: !$v.adresseFiscale.bister.$invalid, invalid: $v.adresseFiscale.bister.$invalid }"
              v-model="$v.adresseFiscale.bister.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.complement1')" for="adresse-fiscale-complement1"
              >Complement 1</label
            >
            <input
              type="text"
              class="form-control"
              name="complement1"
              id="adresse-fiscale-complement1"
              data-cy="complement1"
              :class="{ valid: !$v.adresseFiscale.complement1.$invalid, invalid: $v.adresseFiscale.complement1.$invalid }"
              v-model="$v.adresseFiscale.complement1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.complement2')" for="adresse-fiscale-complement2"
              >Complement 2</label
            >
            <input
              type="text"
              class="form-control"
              name="complement2"
              id="adresse-fiscale-complement2"
              data-cy="complement2"
              :class="{ valid: !$v.adresseFiscale.complement2.$invalid, invalid: $v.adresseFiscale.complement2.$invalid }"
              v-model="$v.adresseFiscale.complement2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresseFiscale.typeVoie')" for="adresse-fiscale-typeVoie"
              >Type Voie</label
            >
            <input
              type="text"
              class="form-control"
              name="typeVoie"
              id="adresse-fiscale-typeVoie"
              data-cy="typeVoie"
              :class="{ valid: !$v.adresseFiscale.typeVoie.$invalid, invalid: $v.adresseFiscale.typeVoie.$invalid }"
              v-model="$v.adresseFiscale.typeVoie.$model"
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
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adresseFiscale.affichageInternet')"
              for="adresse-fiscale-affichageInternet"
              >Affichage Internet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageInternet"
              id="adresse-fiscale-affichageInternet"
              data-cy="affichageInternet"
              :class="{ valid: !$v.adresseFiscale.affichageInternet.$invalid, invalid: $v.adresseFiscale.affichageInternet.$invalid }"
              v-model="$v.adresseFiscale.affichageInternet.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adresseFiscale.affichageIntranet')"
              for="adresse-fiscale-affichageIntranet"
              >Affichage Intranet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageIntranet"
              id="adresse-fiscale-affichageIntranet"
              data-cy="affichageIntranet"
              :class="{ valid: !$v.adresseFiscale.affichageIntranet.$invalid, invalid: $v.adresseFiscale.affichageIntranet.$invalid }"
              v-model="$v.adresseFiscale.affichageIntranet.$model"
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

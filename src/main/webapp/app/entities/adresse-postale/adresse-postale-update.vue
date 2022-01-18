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
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.bister')" for="adresse-postale-bister">Bister</label>
            <input
              type="text"
              class="form-control"
              name="bister"
              id="adresse-postale-bister"
              data-cy="bister"
              :class="{ valid: !$v.adressePostale.bister.$invalid, invalid: $v.adressePostale.bister.$invalid }"
              v-model="$v.adressePostale.bister.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.complement1')" for="adresse-postale-complement1"
              >Complement 1</label
            >
            <input
              type="text"
              class="form-control"
              name="complement1"
              id="adresse-postale-complement1"
              data-cy="complement1"
              :class="{ valid: !$v.adressePostale.complement1.$invalid, invalid: $v.adressePostale.complement1.$invalid }"
              v-model="$v.adressePostale.complement1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.complement2')" for="adresse-postale-complement2"
              >Complement 2</label
            >
            <input
              type="text"
              class="form-control"
              name="complement2"
              id="adresse-postale-complement2"
              data-cy="complement2"
              :class="{ valid: !$v.adressePostale.complement2.$invalid, invalid: $v.adressePostale.complement2.$invalid }"
              v-model="$v.adressePostale.complement2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale.typeVoie')" for="adresse-postale-typeVoie"
              >Type Voie</label
            >
            <input
              type="text"
              class="form-control"
              name="typeVoie"
              id="adresse-postale-typeVoie"
              data-cy="typeVoie"
              :class="{ valid: !$v.adressePostale.typeVoie.$invalid, invalid: $v.adressePostale.typeVoie.$invalid }"
              v-model="$v.adressePostale.typeVoie.$model"
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
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adressePostale.affichageInternet')"
              for="adresse-postale-affichageInternet"
              >Affichage Internet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageInternet"
              id="adresse-postale-affichageInternet"
              data-cy="affichageInternet"
              :class="{ valid: !$v.adressePostale.affichageInternet.$invalid, invalid: $v.adressePostale.affichageInternet.$invalid }"
              v-model="$v.adressePostale.affichageInternet.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adressePostale.affichageIntranet')"
              for="adresse-postale-affichageIntranet"
              >Affichage Intranet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageIntranet"
              id="adresse-postale-affichageIntranet"
              data-cy="affichageIntranet"
              :class="{ valid: !$v.adressePostale.affichageIntranet.$invalid, invalid: $v.adressePostale.affichageIntranet.$invalid }"
              v-model="$v.adressePostale.affichageIntranet.$model"
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

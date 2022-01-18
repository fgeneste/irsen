<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.adressePostale2.home.createOrEditLabel"
          data-cy="AdressePostale2CreateUpdateHeading"
          v-text="$t('irsenApp.adressePostale2.home.createOrEditLabel')"
        >
          Create or edit a AdressePostale2
        </h2>
        <div>
          <div class="form-group" v-if="adressePostale2.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="adressePostale2.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.numero')" for="adresse-postale-2-numero">Numero</label>
            <input
              type="text"
              class="form-control"
              name="numero"
              id="adresse-postale-2-numero"
              data-cy="numero"
              :class="{ valid: !$v.adressePostale2.numero.$invalid, invalid: $v.adressePostale2.numero.$invalid }"
              v-model="$v.adressePostale2.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.bister')" for="adresse-postale-2-bister">Bister</label>
            <input
              type="text"
              class="form-control"
              name="bister"
              id="adresse-postale-2-bister"
              data-cy="bister"
              :class="{ valid: !$v.adressePostale2.bister.$invalid, invalid: $v.adressePostale2.bister.$invalid }"
              v-model="$v.adressePostale2.bister.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.complement1')" for="adresse-postale-2-complement1"
              >Complement 1</label
            >
            <input
              type="text"
              class="form-control"
              name="complement1"
              id="adresse-postale-2-complement1"
              data-cy="complement1"
              :class="{ valid: !$v.adressePostale2.complement1.$invalid, invalid: $v.adressePostale2.complement1.$invalid }"
              v-model="$v.adressePostale2.complement1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.complement2')" for="adresse-postale-2-complement2"
              >Complement 2</label
            >
            <input
              type="text"
              class="form-control"
              name="complement2"
              id="adresse-postale-2-complement2"
              data-cy="complement2"
              :class="{ valid: !$v.adressePostale2.complement2.$invalid, invalid: $v.adressePostale2.complement2.$invalid }"
              v-model="$v.adressePostale2.complement2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.typeVoie')" for="adresse-postale-2-typeVoie"
              >Type Voie</label
            >
            <input
              type="text"
              class="form-control"
              name="typeVoie"
              id="adresse-postale-2-typeVoie"
              data-cy="typeVoie"
              :class="{ valid: !$v.adressePostale2.typeVoie.$invalid, invalid: $v.adressePostale2.typeVoie.$invalid }"
              v-model="$v.adressePostale2.typeVoie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.voie')" for="adresse-postale-2-voie">Voie</label>
            <input
              type="text"
              class="form-control"
              name="voie"
              id="adresse-postale-2-voie"
              data-cy="voie"
              :class="{ valid: !$v.adressePostale2.voie.$invalid, invalid: $v.adressePostale2.voie.$invalid }"
              v-model="$v.adressePostale2.voie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.codePostal')" for="adresse-postale-2-codePostal"
              >Code Postal</label
            >
            <input
              type="text"
              class="form-control"
              name="codePostal"
              id="adresse-postale-2-codePostal"
              data-cy="codePostal"
              :class="{ valid: !$v.adressePostale2.codePostal.$invalid, invalid: $v.adressePostale2.codePostal.$invalid }"
              v-model="$v.adressePostale2.codePostal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.ville')" for="adresse-postale-2-ville">Ville</label>
            <input
              type="text"
              class="form-control"
              name="ville"
              id="adresse-postale-2-ville"
              data-cy="ville"
              :class="{ valid: !$v.adressePostale2.ville.$invalid, invalid: $v.adressePostale2.ville.$invalid }"
              v-model="$v.adressePostale2.ville.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adressePostale2.pays')" for="adresse-postale-2-pays">Pays</label>
            <input
              type="text"
              class="form-control"
              name="pays"
              id="adresse-postale-2-pays"
              data-cy="pays"
              :class="{ valid: !$v.adressePostale2.pays.$invalid, invalid: $v.adressePostale2.pays.$invalid }"
              v-model="$v.adressePostale2.pays.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adressePostale2.affichageInternet')"
              for="adresse-postale-2-affichageInternet"
              >Affichage Internet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageInternet"
              id="adresse-postale-2-affichageInternet"
              data-cy="affichageInternet"
              :class="{ valid: !$v.adressePostale2.affichageInternet.$invalid, invalid: $v.adressePostale2.affichageInternet.$invalid }"
              v-model="$v.adressePostale2.affichageInternet.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('irsenApp.adressePostale2.affichageIntranet')"
              for="adresse-postale-2-affichageIntranet"
              >Affichage Intranet</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="affichageIntranet"
              id="adresse-postale-2-affichageIntranet"
              data-cy="affichageIntranet"
              :class="{ valid: !$v.adressePostale2.affichageIntranet.$invalid, invalid: $v.adressePostale2.affichageIntranet.$invalid }"
              v-model="$v.adressePostale2.affichageIntranet.$model"
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
            :disabled="$v.adressePostale2.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./adresse-postale-2-update.component.ts"></script>

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
            <input
              type="text"
              class="form-control"
              name="dateDebut"
              id="mandat-ancien-dateDebut"
              data-cy="dateDebut"
              :class="{ valid: !$v.mandatAncien.dateDebut.$invalid, invalid: $v.mandatAncien.dateDebut.$invalid }"
              v-model="$v.mandatAncien.dateDebut.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.dateFin')" for="mandat-ancien-dateFin">Date Fin</label>
            <input
              type="text"
              class="form-control"
              name="dateFin"
              id="mandat-ancien-dateFin"
              data-cy="dateFin"
              :class="{ valid: !$v.mandatAncien.dateFin.$invalid, invalid: $v.mandatAncien.dateFin.$invalid }"
              v-model="$v.mandatAncien.dateFin.$model"
            />
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
            <label class="form-control-label" v-text="$t('irsenApp.mandatAncien.mandat')" for="mandat-ancien-mandat">Mandat</label>
            <select class="form-control" id="mandat-ancien-mandat" data-cy="mandat" name="mandat" v-model="mandatAncien.mandat">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="mandatAncien.mandat && mandatOption.id === mandatAncien.mandat.id ? mandatAncien.mandat : mandatOption"
                v-for="mandatOption in mandats"
                :key="mandatOption.id"
              >
                {{ mandatOption.id }}
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

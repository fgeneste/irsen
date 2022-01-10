<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.senateur.home.createOrEditLabel"
          data-cy="SenateurCreateUpdateHeading"
          v-text="$t('irsenApp.senateur.home.createOrEditLabel')"
        >
          Create or edit a Senateur
        </h2>
        <div>
          <div class="form-group" v-if="senateur.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="senateur.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.senateur.etatCivil')" for="senateur-etatCivil">Etat Civil</label>
            <select class="form-control" id="senateur-etatCivil" data-cy="etatCivil" name="etatCivil" v-model="senateur.etatCivil">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="senateur.etatCivil && etatCivilOption.id === senateur.etatCivil.id ? senateur.etatCivil : etatCivilOption"
                v-for="etatCivilOption in etatCivils"
                :key="etatCivilOption.id"
              >
                {{ etatCivilOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.senateur.adresses')" for="senateur-adresses">Adresses</label>
            <select class="form-control" id="senateur-adresses" data-cy="adresses" name="adresses" v-model="senateur.adresses">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="senateur.adresses && adressesOption.id === senateur.adresses.id ? senateur.adresses : adressesOption"
                v-for="adressesOption in adresses"
                :key="adressesOption.id"
              >
                {{ adressesOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.senateur.mandats')" for="senateur-mandats">Mandats</label>
            <select class="form-control" id="senateur-mandats" data-cy="mandats" name="mandats" v-model="senateur.mandats">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="senateur.mandats && mandatOption.id === senateur.mandats.id ? senateur.mandats : mandatOption"
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
            :disabled="$v.senateur.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./senateur-update.component.ts"></script>

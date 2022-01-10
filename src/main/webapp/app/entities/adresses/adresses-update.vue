<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.adresses.home.createOrEditLabel"
          data-cy="AdressesCreateUpdateHeading"
          v-text="$t('irsenApp.adresses.home.createOrEditLabel')"
        >
          Create or edit a Adresses
        </h2>
        <div>
          <div class="form-group" v-if="adresses.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="adresses.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresses.adresseFiscale')" for="adresses-adresseFiscale"
              >Adresse Fiscale</label
            >
            <select
              class="form-control"
              id="adresses-adresseFiscale"
              data-cy="adresseFiscale"
              name="adresseFiscale"
              v-model="adresses.adresseFiscale"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  adresses.adresseFiscale && adresseFiscaleOption.id === adresses.adresseFiscale.id
                    ? adresses.adresseFiscale
                    : adresseFiscaleOption
                "
                v-for="adresseFiscaleOption in adresseFiscales"
                :key="adresseFiscaleOption.id"
              >
                {{ adresseFiscaleOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresses.adressePostale')" for="adresses-adressePostale"
              >Adresse Postale</label
            >
            <select
              class="form-control"
              id="adresses-adressePostale"
              data-cy="adressePostale"
              name="adressePostale"
              v-model="adresses.adressePostale"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  adresses.adressePostale && adressePostaleOption.id === adresses.adressePostale.id
                    ? adresses.adressePostale
                    : adressePostaleOption
                "
                v-for="adressePostaleOption in adressePostales"
                :key="adressePostaleOption.id"
              >
                {{ adressePostaleOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.adresses.adressePostale2')" for="adresses-adressePostale2"
              >Adresse Postale 2</label
            >
            <select
              class="form-control"
              id="adresses-adressePostale2"
              data-cy="adressePostale2"
              name="adressePostale2"
              v-model="adresses.adressePostale2"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  adresses.adressePostale2 && adressePostale2Option.id === adresses.adressePostale2.id
                    ? adresses.adressePostale2
                    : adressePostale2Option
                "
                v-for="adressePostale2Option in adressePostale2s"
                :key="adressePostale2Option.id"
              >
                {{ adressePostale2Option.id }}
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
            :disabled="$v.adresses.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./adresses-update.component.ts"></script>

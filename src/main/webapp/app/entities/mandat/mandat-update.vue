<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.mandat.home.createOrEditLabel"
          data-cy="MandatCreateUpdateHeading"
          v-text="$t('irsenApp.mandat.home.createOrEditLabel')"
        >
          Create or edit a Mandat
        </h2>
        <div>
          <div class="form-group" v-if="mandat.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="mandat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandat.mandatAncien')" for="mandat-mandatAncien">Mandat Ancien</label>
            <select class="form-control" id="mandat-mandatAncien" data-cy="mandatAncien" name="mandatAncien" v-model="mandat.mandatAncien">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  mandat.mandatAncien && mandatAncienOption.id === mandat.mandatAncien.id ? mandat.mandatAncien : mandatAncienOption
                "
                v-for="mandatAncienOption in mandatAnciens"
                :key="mandatAncienOption.id"
              >
                {{ mandatAncienOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.mandat.mandatEnCours')" for="mandat-mandatEnCours"
              >Mandat En Cours</label
            >
            <select
              class="form-control"
              id="mandat-mandatEnCours"
              data-cy="mandatEnCours"
              name="mandatEnCours"
              v-model="mandat.mandatEnCours"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  mandat.mandatEnCours && mandatEnCoursOption.id === mandat.mandatEnCours.id ? mandat.mandatEnCours : mandatEnCoursOption
                "
                v-for="mandatEnCoursOption in mandatEnCours"
                :key="mandatEnCoursOption.id"
              >
                {{ mandatEnCoursOption.id }}
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
            :disabled="$v.mandat.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./mandat-update.component.ts"></script>

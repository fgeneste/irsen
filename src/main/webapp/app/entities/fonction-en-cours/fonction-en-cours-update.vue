<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.fonctionEnCours.home.createOrEditLabel"
          data-cy="FonctionEnCoursCreateUpdateHeading"
          v-text="$t('irsenApp.fonctionEnCours.home.createOrEditLabel')"
        >
          Create or edit a FonctionEnCours
        </h2>
        <div>
          <div class="form-group" v-if="fonctionEnCours.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fonctionEnCours.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.libelle')" for="fonction-en-cours-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="fonction-en-cours-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.fonctionEnCours.libelle.$invalid, invalid: $v.fonctionEnCours.libelle.$invalid }"
              v-model="$v.fonctionEnCours.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.dateDebut')" for="fonction-en-cours-dateDebut"
              >Date Debut</label
            >
            <input
              type="text"
              class="form-control"
              name="dateDebut"
              id="fonction-en-cours-dateDebut"
              data-cy="dateDebut"
              :class="{ valid: !$v.fonctionEnCours.dateDebut.$invalid, invalid: $v.fonctionEnCours.dateDebut.$invalid }"
              v-model="$v.fonctionEnCours.dateDebut.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.dateFin')" for="fonction-en-cours-dateFin"
              >Date Fin</label
            >
            <input
              type="text"
              class="form-control"
              name="dateFin"
              id="fonction-en-cours-dateFin"
              data-cy="dateFin"
              :class="{ valid: !$v.fonctionEnCours.dateFin.$invalid, invalid: $v.fonctionEnCours.dateFin.$invalid }"
              v-model="$v.fonctionEnCours.dateFin.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.fonctionEnCours.mandatEnCours')" for="fonction-en-cours-mandatEnCours"
              >Mandat En Cours</label
            >
            <select
              class="form-control"
              id="fonction-en-cours-mandatEnCours"
              data-cy="mandatEnCours"
              name="mandatEnCours"
              v-model="fonctionEnCours.mandatEnCours"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  fonctionEnCours.mandatEnCours && mandatEnCoursOption.id === fonctionEnCours.mandatEnCours.id
                    ? fonctionEnCours.mandatEnCours
                    : mandatEnCoursOption
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
            :disabled="$v.fonctionEnCours.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./fonction-en-cours-update.component.ts"></script>

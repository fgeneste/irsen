<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.telephoneFixe.home.createOrEditLabel"
          data-cy="TelephoneFixeCreateUpdateHeading"
          v-text="$t('irsenApp.telephoneFixe.home.createOrEditLabel')"
        >
          Create or edit a TelephoneFixe
        </h2>
        <div>
          <div class="form-group" v-if="telephoneFixe.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="telephoneFixe.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephoneFixe.type')" for="telephone-fixe-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="telephone-fixe-type"
              data-cy="type"
              :class="{ valid: !$v.telephoneFixe.type.$invalid, invalid: $v.telephoneFixe.type.$invalid }"
              v-model="$v.telephoneFixe.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephoneFixe.numero')" for="telephone-fixe-numero">Numero</label>
            <input
              type="text"
              class="form-control"
              name="numero"
              id="telephone-fixe-numero"
              data-cy="numero"
              :class="{ valid: !$v.telephoneFixe.numero.$invalid, invalid: $v.telephoneFixe.numero.$invalid }"
              v-model="$v.telephoneFixe.numero.$model"
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
            :disabled="$v.telephoneFixe.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./telephone-fixe-update.component.ts"></script>

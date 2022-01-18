<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.telephonePortable.home.createOrEditLabel"
          data-cy="TelephonePortableCreateUpdateHeading"
          v-text="$t('irsenApp.telephonePortable.home.createOrEditLabel')"
        >
          Create or edit a TelephonePortable
        </h2>
        <div>
          <div class="form-group" v-if="telephonePortable.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="telephonePortable.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephonePortable.type')" for="telephone-portable-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="telephone-portable-type"
              data-cy="type"
              :class="{ valid: !$v.telephonePortable.type.$invalid, invalid: $v.telephonePortable.type.$invalid }"
              v-model="$v.telephonePortable.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephonePortable.numero')" for="telephone-portable-numero"
              >Numero</label
            >
            <input
              type="text"
              class="form-control"
              name="numero"
              id="telephone-portable-numero"
              data-cy="numero"
              :class="{ valid: !$v.telephonePortable.numero.$invalid, invalid: $v.telephonePortable.numero.$invalid }"
              v-model="$v.telephonePortable.numero.$model"
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
            :disabled="$v.telephonePortable.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./telephone-portable-update.component.ts"></script>

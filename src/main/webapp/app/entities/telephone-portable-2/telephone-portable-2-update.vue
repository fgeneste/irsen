<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.telephonePortable2.home.createOrEditLabel"
          data-cy="TelephonePortable2CreateUpdateHeading"
          v-text="$t('irsenApp.telephonePortable2.home.createOrEditLabel')"
        >
          Create or edit a TelephonePortable2
        </h2>
        <div>
          <div class="form-group" v-if="telephonePortable2.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="telephonePortable2.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephonePortable2.type')" for="telephone-portable-2-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="telephone-portable-2-type"
              data-cy="type"
              :class="{ valid: !$v.telephonePortable2.type.$invalid, invalid: $v.telephonePortable2.type.$invalid }"
              v-model="$v.telephonePortable2.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.telephonePortable2.numero')" for="telephone-portable-2-numero"
              >Numero</label
            >
            <input
              type="text"
              class="form-control"
              name="numero"
              id="telephone-portable-2-numero"
              data-cy="numero"
              :class="{ valid: !$v.telephonePortable2.numero.$invalid, invalid: $v.telephonePortable2.numero.$invalid }"
              v-model="$v.telephonePortable2.numero.$model"
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
            :disabled="$v.telephonePortable2.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./telephone-portable-2-update.component.ts"></script>

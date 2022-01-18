<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.decoration.home.createOrEditLabel"
          data-cy="DecorationCreateUpdateHeading"
          v-text="$t('irsenApp.decoration.home.createOrEditLabel')"
        >
          Create or edit a Decoration
        </h2>
        <div>
          <div class="form-group" v-if="decoration.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="decoration.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.decoration.type')" for="decoration-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="decoration-type"
              data-cy="type"
              :class="{ valid: !$v.decoration.type.$invalid, invalid: $v.decoration.type.$invalid }"
              v-model="$v.decoration.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.decoration.grade')" for="decoration-grade">Grade</label>
            <input
              type="text"
              class="form-control"
              name="grade"
              id="decoration-grade"
              data-cy="grade"
              :class="{ valid: !$v.decoration.grade.$invalid, invalid: $v.decoration.grade.$invalid }"
              v-model="$v.decoration.grade.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.decoration.senateur')" for="decoration-senateur">Senateur</label>
            <select class="form-control" id="decoration-senateur" data-cy="senateur" name="senateur" v-model="decoration.senateur">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="decoration.senateur && senateurOption.id === decoration.senateur.id ? decoration.senateur : senateurOption"
                v-for="senateurOption in senateurs"
                :key="senateurOption.id"
              >
                {{ senateurOption.id }}
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
            :disabled="$v.decoration.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./decoration-update.component.ts"></script>

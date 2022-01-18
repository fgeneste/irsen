<template>
  <div>
    <h2 id="page-heading" data-cy="DecorationHeading">
      <span v-text="$t('irsenApp.decoration.home.title')" id="decoration-heading">Decorations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.decoration.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DecorationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-decoration"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.decoration.home.createLabel')"> Create a new Decoration </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && decorations && decorations.length === 0">
      <span v-text="$t('irsenApp.decoration.home.notFound')">No decorations found</span>
    </div>
    <div class="table-responsive" v-if="decorations && decorations.length > 0">
      <table class="table table-striped" aria-describedby="decorations">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.decoration.type')">Type</span></th>
            <th scope="row"><span v-text="$t('irsenApp.decoration.grade')">Grade</span></th>
            <th scope="row"><span v-text="$t('irsenApp.decoration.senateur')">Senateur</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="decoration in decorations" :key="decoration.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DecorationView', params: { decorationId: decoration.id } }">{{ decoration.id }}</router-link>
            </td>
            <td>{{ decoration.type }}</td>
            <td>{{ decoration.grade }}</td>
            <td>
              <div v-if="decoration.senateur">
                <router-link :to="{ name: 'SenateurView', params: { senateurId: decoration.senateur.id } }">{{
                  decoration.senateur.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DecorationView', params: { decorationId: decoration.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DecorationEdit', params: { decorationId: decoration.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(decoration)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="irsenApp.decoration.delete.question" data-cy="decorationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-decoration-heading" v-text="$t('irsenApp.decoration.delete.question', { id: removeId })">
          Are you sure you want to delete this Decoration?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-decoration"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDecoration()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./decoration.component.ts"></script>

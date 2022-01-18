<template>
  <div>
    <h2 id="page-heading" data-cy="TelephonePortable2Heading">
      <span v-text="$t('irsenApp.telephonePortable2.home.title')" id="telephone-portable-2-heading">Telephone Portable 2 S</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.telephonePortable2.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TelephonePortable2Create' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-telephone-portable-2"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.telephonePortable2.home.createLabel')"> Create a new Telephone Portable 2 </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && telephonePortable2s && telephonePortable2s.length === 0">
      <span v-text="$t('irsenApp.telephonePortable2.home.notFound')">No telephonePortable2s found</span>
    </div>
    <div class="table-responsive" v-if="telephonePortable2s && telephonePortable2s.length > 0">
      <table class="table table-striped" aria-describedby="telephonePortable2s">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.telephonePortable2.type')">Type</span></th>
            <th scope="row"><span v-text="$t('irsenApp.telephonePortable2.numero')">Numero</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="telephonePortable2 in telephonePortable2s" :key="telephonePortable2.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TelephonePortable2View', params: { telephonePortable2Id: telephonePortable2.id } }">{{
                telephonePortable2.id
              }}</router-link>
            </td>
            <td>{{ telephonePortable2.type }}</td>
            <td>{{ telephonePortable2.numero }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TelephonePortable2View', params: { telephonePortable2Id: telephonePortable2.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TelephonePortable2Edit', params: { telephonePortable2Id: telephonePortable2.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(telephonePortable2)"
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
        ><span
          id="irsenApp.telephonePortable2.delete.question"
          data-cy="telephonePortable2DeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-telephonePortable2-heading" v-text="$t('irsenApp.telephonePortable2.delete.question', { id: removeId })">
          Are you sure you want to delete this Telephone Portable 2?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-telephonePortable2"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTelephonePortable2()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./telephone-portable-2.component.ts"></script>

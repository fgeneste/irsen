<template>
  <div>
    <h2 id="page-heading" data-cy="AdressesHeading">
      <span v-text="$t('irsenApp.adresses.home.title')" id="adresses-heading">Adresses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.adresses.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AdressesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-adresses"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.adresses.home.createLabel')"> Create a new Adresses </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && adresses && adresses.length === 0">
      <span v-text="$t('irsenApp.adresses.home.notFound')">No adresses found</span>
    </div>
    <div class="table-responsive" v-if="adresses && adresses.length > 0">
      <table class="table table-striped" aria-describedby="adresses">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresses.adresseFiscale')">Adresse Fiscale</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresses.adressePostale')">Adresse Postale</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresses.adressePostale2')">Adresse Postale 2</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="adresses in adresses" :key="adresses.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AdressesView', params: { adressesId: adresses.id } }">{{ adresses.id }}</router-link>
            </td>
            <td>
              <div v-if="adresses.adresseFiscale">
                <router-link :to="{ name: 'AdresseFiscaleView', params: { adresseFiscaleId: adresses.adresseFiscale.id } }">{{
                  adresses.adresseFiscale.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="adresses.adressePostale">
                <router-link :to="{ name: 'AdressePostaleView', params: { adressePostaleId: adresses.adressePostale.id } }">{{
                  adresses.adressePostale.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="adresses.adressePostale2">
                <router-link :to="{ name: 'AdressePostale2View', params: { adressePostale2Id: adresses.adressePostale2.id } }">{{
                  adresses.adressePostale2.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AdressesView', params: { adressesId: adresses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AdressesEdit', params: { adressesId: adresses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(adresses)"
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
        ><span id="irsenApp.adresses.delete.question" data-cy="adressesDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-adresses-heading" v-text="$t('irsenApp.adresses.delete.question', { id: removeId })">
          Are you sure you want to delete this Adresses?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-adresses"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAdresses()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./adresses.component.ts"></script>

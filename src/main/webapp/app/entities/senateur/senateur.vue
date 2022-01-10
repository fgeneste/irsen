<template>
  <div>
    <h2 id="page-heading" data-cy="SenateurHeading">
      <span v-text="$t('irsenApp.senateur.home.title')" id="senateur-heading">Senateurs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.senateur.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SenateurCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-senateur"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.senateur.home.createLabel')"> Create a new Senateur </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && senateurs && senateurs.length === 0">
      <span v-text="$t('irsenApp.senateur.home.notFound')">No senateurs found</span>
    </div>
    <div class="table-responsive" v-if="senateurs && senateurs.length > 0">
      <table class="table table-striped" aria-describedby="senateurs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.senateur.etatCivil')">Etat Civil</span></th>
            <th scope="row"><span v-text="$t('irsenApp.senateur.adresses')">Adresses</span></th>
            <th scope="row"><span v-text="$t('irsenApp.senateur.mandats')">Mandats</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="senateur in senateurs" :key="senateur.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SenateurView', params: { senateurId: senateur.id } }">{{ senateur.id }}</router-link>
            </td>
            <td>
              <div v-if="senateur.etatCivil">
                <router-link :to="{ name: 'EtatCivilView', params: { etatCivilId: senateur.etatCivil.id } }">{{
                  senateur.etatCivil.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="senateur.adresses">
                <router-link :to="{ name: 'AdressesView', params: { adressesId: senateur.adresses.id } }">{{
                  senateur.adresses.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="senateur.mandats">
                <router-link :to="{ name: 'MandatView', params: { mandatId: senateur.mandats.id } }">{{ senateur.mandats.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SenateurView', params: { senateurId: senateur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SenateurEdit', params: { senateurId: senateur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(senateur)"
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
        ><span id="irsenApp.senateur.delete.question" data-cy="senateurDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-senateur-heading" v-text="$t('irsenApp.senateur.delete.question', { id: removeId })">
          Are you sure you want to delete this Senateur?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-senateur"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSenateur()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./senateur.component.ts"></script>

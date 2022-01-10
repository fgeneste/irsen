<template>
  <div>
    <h2 id="page-heading" data-cy="AdressePostaleHeading">
      <span v-text="$t('irsenApp.adressePostale.home.title')" id="adresse-postale-heading">Adresse Postales</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.adressePostale.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AdressePostaleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-adresse-postale"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.adressePostale.home.createLabel')"> Create a new Adresse Postale </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && adressePostales && adressePostales.length === 0">
      <span v-text="$t('irsenApp.adressePostale.home.notFound')">No adressePostales found</span>
    </div>
    <div class="table-responsive" v-if="adressePostales && adressePostales.length > 0">
      <table class="table table-striped" aria-describedby="adressePostales">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.label')">Label</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.voie')">Voie</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.codePostal')">Code Postal</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.ville')">Ville</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.pays')">Pays</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.localisation')">Localisation</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.modeManuel')">Mode Manuel</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale.type')">Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="adressePostale in adressePostales" :key="adressePostale.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AdressePostaleView', params: { adressePostaleId: adressePostale.id } }">{{
                adressePostale.id
              }}</router-link>
            </td>
            <td>{{ adressePostale.label }}</td>
            <td>{{ adressePostale.numero }}</td>
            <td>{{ adressePostale.voie }}</td>
            <td>{{ adressePostale.codePostal }}</td>
            <td>{{ adressePostale.ville }}</td>
            <td>{{ adressePostale.pays }}</td>
            <td>{{ adressePostale.localisation }}</td>
            <td>{{ adressePostale.modeManuel }}</td>
            <td>{{ adressePostale.type }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AdressePostaleView', params: { adressePostaleId: adressePostale.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AdressePostaleEdit', params: { adressePostaleId: adressePostale.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(adressePostale)"
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
        ><span id="irsenApp.adressePostale.delete.question" data-cy="adressePostaleDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-adressePostale-heading" v-text="$t('irsenApp.adressePostale.delete.question', { id: removeId })">
          Are you sure you want to delete this Adresse Postale?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-adressePostale"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAdressePostale()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./adresse-postale.component.ts"></script>

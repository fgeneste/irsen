<template>
  <div>
    <h2 id="page-heading" data-cy="AdresseFiscaleHeading">
      <span v-text="$t('irsenApp.adresseFiscale.home.title')" id="adresse-fiscale-heading">Adresse Fiscales</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.adresseFiscale.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AdresseFiscaleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-adresse-fiscale"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.adresseFiscale.home.createLabel')"> Create a new Adresse Fiscale </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && adresseFiscales && adresseFiscales.length === 0">
      <span v-text="$t('irsenApp.adresseFiscale.home.notFound')">No adresseFiscales found</span>
    </div>
    <div class="table-responsive" v-if="adresseFiscales && adresseFiscales.length > 0">
      <table class="table table-striped" aria-describedby="adresseFiscales">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.bister')">Bister</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.complement1')">Complement 1</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.complement2')">Complement 2</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.typeVoie')">Type Voie</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.voie')">Voie</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.codePostal')">Code Postal</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.ville')">Ville</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.pays')">Pays</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.affichageInternet')">Affichage Internet</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adresseFiscale.affichageIntranet')">Affichage Intranet</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="adresseFiscale in adresseFiscales" :key="adresseFiscale.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AdresseFiscaleView', params: { adresseFiscaleId: adresseFiscale.id } }">{{
                adresseFiscale.id
              }}</router-link>
            </td>
            <td>{{ adresseFiscale.numero }}</td>
            <td>{{ adresseFiscale.bister }}</td>
            <td>{{ adresseFiscale.complement1 }}</td>
            <td>{{ adresseFiscale.complement2 }}</td>
            <td>{{ adresseFiscale.typeVoie }}</td>
            <td>{{ adresseFiscale.voie }}</td>
            <td>{{ adresseFiscale.codePostal }}</td>
            <td>{{ adresseFiscale.ville }}</td>
            <td>{{ adresseFiscale.pays }}</td>
            <td>{{ adresseFiscale.affichageInternet }}</td>
            <td>{{ adresseFiscale.affichageIntranet }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AdresseFiscaleView', params: { adresseFiscaleId: adresseFiscale.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AdresseFiscaleEdit', params: { adresseFiscaleId: adresseFiscale.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(adresseFiscale)"
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
        ><span id="irsenApp.adresseFiscale.delete.question" data-cy="adresseFiscaleDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-adresseFiscale-heading" v-text="$t('irsenApp.adresseFiscale.delete.question', { id: removeId })">
          Are you sure you want to delete this Adresse Fiscale?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-adresseFiscale"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAdresseFiscale()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./adresse-fiscale.component.ts"></script>

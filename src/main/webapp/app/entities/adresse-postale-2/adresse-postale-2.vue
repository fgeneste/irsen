<template>
  <div>
    <h2 id="page-heading" data-cy="AdressePostale2Heading">
      <span v-text="$t('irsenApp.adressePostale2.home.title')" id="adresse-postale-2-heading">Adresse Postale 2 S</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.adressePostale2.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AdressePostale2Create' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-adresse-postale-2"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.adressePostale2.home.createLabel')"> Create a new Adresse Postale 2 </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && adressePostale2s && adressePostale2s.length === 0">
      <span v-text="$t('irsenApp.adressePostale2.home.notFound')">No adressePostale2s found</span>
    </div>
    <div class="table-responsive" v-if="adressePostale2s && adressePostale2s.length > 0">
      <table class="table table-striped" aria-describedby="adressePostale2s">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.bister')">Bister</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.complement1')">Complement 1</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.complement2')">Complement 2</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.typeVoie')">Type Voie</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.voie')">Voie</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.codePostal')">Code Postal</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.ville')">Ville</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.pays')">Pays</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.affichageInternet')">Affichage Internet</span></th>
            <th scope="row"><span v-text="$t('irsenApp.adressePostale2.affichageIntranet')">Affichage Intranet</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="adressePostale2 in adressePostale2s" :key="adressePostale2.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AdressePostale2View', params: { adressePostale2Id: adressePostale2.id } }">{{
                adressePostale2.id
              }}</router-link>
            </td>
            <td>{{ adressePostale2.numero }}</td>
            <td>{{ adressePostale2.bister }}</td>
            <td>{{ adressePostale2.complement1 }}</td>
            <td>{{ adressePostale2.complement2 }}</td>
            <td>{{ adressePostale2.typeVoie }}</td>
            <td>{{ adressePostale2.voie }}</td>
            <td>{{ adressePostale2.codePostal }}</td>
            <td>{{ adressePostale2.ville }}</td>
            <td>{{ adressePostale2.pays }}</td>
            <td>{{ adressePostale2.affichageInternet }}</td>
            <td>{{ adressePostale2.affichageIntranet }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AdressePostale2View', params: { adressePostale2Id: adressePostale2.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AdressePostale2Edit', params: { adressePostale2Id: adressePostale2.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(adressePostale2)"
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
        ><span id="irsenApp.adressePostale2.delete.question" data-cy="adressePostale2DeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-adressePostale2-heading" v-text="$t('irsenApp.adressePostale2.delete.question', { id: removeId })">
          Are you sure you want to delete this Adresse Postale 2?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-adressePostale2"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAdressePostale2()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./adresse-postale-2.component.ts"></script>

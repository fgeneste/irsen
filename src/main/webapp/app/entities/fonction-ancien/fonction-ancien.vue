<template>
  <div>
    <h2 id="page-heading" data-cy="FonctionAncienHeading">
      <span v-text="$t('irsenApp.fonctionAncien.home.title')" id="fonction-ancien-heading">Fonction Anciens</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.fonctionAncien.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FonctionAncienCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-fonction-ancien"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.fonctionAncien.home.createLabel')"> Create a new Fonction Ancien </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fonctionAnciens && fonctionAnciens.length === 0">
      <span v-text="$t('irsenApp.fonctionAncien.home.notFound')">No fonctionAnciens found</span>
    </div>
    <div class="table-responsive" v-if="fonctionAnciens && fonctionAnciens.length > 0">
      <table class="table table-striped" aria-describedby="fonctionAnciens">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionAncien.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionAncien.dateDebut')">Date Debut</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionAncien.dateFin')">Date Fin</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionAncien.mandatAncien')">Mandat Ancien</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fonctionAncien in fonctionAnciens" :key="fonctionAncien.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FonctionAncienView', params: { fonctionAncienId: fonctionAncien.id } }">{{
                fonctionAncien.id
              }}</router-link>
            </td>
            <td>{{ fonctionAncien.libelle }}</td>
            <td>{{ fonctionAncien.dateDebut }}</td>
            <td>{{ fonctionAncien.dateFin }}</td>
            <td>
              <div v-if="fonctionAncien.mandatAncien">
                <router-link :to="{ name: 'MandatAncienView', params: { mandatAncienId: fonctionAncien.mandatAncien.id } }">{{
                  fonctionAncien.mandatAncien.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FonctionAncienView', params: { fonctionAncienId: fonctionAncien.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FonctionAncienEdit', params: { fonctionAncienId: fonctionAncien.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fonctionAncien)"
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
        ><span id="irsenApp.fonctionAncien.delete.question" data-cy="fonctionAncienDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fonctionAncien-heading" v-text="$t('irsenApp.fonctionAncien.delete.question', { id: removeId })">
          Are you sure you want to delete this Fonction Ancien?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fonctionAncien"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFonctionAncien()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./fonction-ancien.component.ts"></script>

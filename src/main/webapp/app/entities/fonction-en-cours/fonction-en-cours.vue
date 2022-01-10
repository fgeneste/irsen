<template>
  <div>
    <h2 id="page-heading" data-cy="FonctionEnCoursHeading">
      <span v-text="$t('irsenApp.fonctionEnCours.home.title')" id="fonction-en-cours-heading">Fonction En Cours</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.fonctionEnCours.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FonctionEnCoursCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-fonction-en-cours"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.fonctionEnCours.home.createLabel')"> Create a new Fonction En Cours </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fonctionEnCours && fonctionEnCours.length === 0">
      <span v-text="$t('irsenApp.fonctionEnCours.home.notFound')">No fonctionEnCours found</span>
    </div>
    <div class="table-responsive" v-if="fonctionEnCours && fonctionEnCours.length > 0">
      <table class="table table-striped" aria-describedby="fonctionEnCours">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionEnCours.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionEnCours.dateDebut')">Date Debut</span></th>
            <th scope="row"><span v-text="$t('irsenApp.fonctionEnCours.dateFin')">Date Fin</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fonctionEnCours in fonctionEnCours" :key="fonctionEnCours.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FonctionEnCoursView', params: { fonctionEnCoursId: fonctionEnCours.id } }">{{
                fonctionEnCours.id
              }}</router-link>
            </td>
            <td>{{ fonctionEnCours.libelle }}</td>
            <td>{{ fonctionEnCours.dateDebut }}</td>
            <td>{{ fonctionEnCours.dateFin }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FonctionEnCoursView', params: { fonctionEnCoursId: fonctionEnCours.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FonctionEnCoursEdit', params: { fonctionEnCoursId: fonctionEnCours.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fonctionEnCours)"
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
        ><span id="irsenApp.fonctionEnCours.delete.question" data-cy="fonctionEnCoursDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fonctionEnCours-heading" v-text="$t('irsenApp.fonctionEnCours.delete.question', { id: removeId })">
          Are you sure you want to delete this Fonction En Cours?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fonctionEnCours"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFonctionEnCours()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./fonction-en-cours.component.ts"></script>

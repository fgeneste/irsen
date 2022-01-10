<template>
  <div>
    <h2 id="page-heading" data-cy="MandatEnCoursHeading">
      <span v-text="$t('irsenApp.mandatEnCours.home.title')" id="mandat-en-cours-heading">Mandat En Cours</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.mandatEnCours.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MandatEnCoursCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-mandat-en-cours"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.mandatEnCours.home.createLabel')"> Create a new Mandat En Cours </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && mandatEnCours && mandatEnCours.length === 0">
      <span v-text="$t('irsenApp.mandatEnCours.home.notFound')">No mandatEnCours found</span>
    </div>
    <div class="table-responsive" v-if="mandatEnCours && mandatEnCours.length > 0">
      <table class="table table-striped" aria-describedby="mandatEnCours">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.idType')">Id Type</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.idFonction')">Id Fonction</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.code')">Code</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.libelleFonction')">Libelle Fonction</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.circonscription')">Circonscription</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.depuis')">Depuis</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.dateElection')">Date Election</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.population')">Population</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.libelleAffichage')">Libelle Affichage</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatEnCours.fonctionEnCours')">Fonction En Cours</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="mandatEnCours in mandatEnCours" :key="mandatEnCours.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MandatEnCoursView', params: { mandatEnCoursId: mandatEnCours.id } }">{{
                mandatEnCours.id
              }}</router-link>
            </td>
            <td>{{ mandatEnCours.idType }}</td>
            <td>{{ mandatEnCours.idFonction }}</td>
            <td>{{ mandatEnCours.code }}</td>
            <td>{{ mandatEnCours.libelle }}</td>
            <td>{{ mandatEnCours.libelleFonction }}</td>
            <td>{{ mandatEnCours.circonscription }}</td>
            <td>{{ mandatEnCours.depuis }}</td>
            <td>{{ mandatEnCours.dateElection }}</td>
            <td>{{ mandatEnCours.population }}</td>
            <td>{{ mandatEnCours.libelleAffichage }}</td>
            <td>
              <div v-if="mandatEnCours.fonctionEnCours">
                <router-link :to="{ name: 'FonctionEnCoursView', params: { fonctionEnCoursId: mandatEnCours.fonctionEnCours.id } }">{{
                  mandatEnCours.fonctionEnCours.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'MandatEnCoursView', params: { mandatEnCoursId: mandatEnCours.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'MandatEnCoursEdit', params: { mandatEnCoursId: mandatEnCours.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(mandatEnCours)"
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
        ><span id="irsenApp.mandatEnCours.delete.question" data-cy="mandatEnCoursDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-mandatEnCours-heading" v-text="$t('irsenApp.mandatEnCours.delete.question', { id: removeId })">
          Are you sure you want to delete this Mandat En Cours?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-mandatEnCours"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMandatEnCours()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./mandat-en-cours.component.ts"></script>

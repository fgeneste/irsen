<template>
  <div>
    <h2 id="page-heading" data-cy="MandatAncienHeading">
      <span v-text="$t('irsenApp.mandatAncien.home.title')" id="mandat-ancien-heading">Mandat Anciens</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.mandatAncien.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MandatAncienCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-mandat-ancien"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.mandatAncien.home.createLabel')"> Create a new Mandat Ancien </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && mandatAnciens && mandatAnciens.length === 0">
      <span v-text="$t('irsenApp.mandatAncien.home.notFound')">No mandatAnciens found</span>
    </div>
    <div class="table-responsive" v-if="mandatAnciens && mandatAnciens.length > 0">
      <table class="table table-striped" aria-describedby="mandatAnciens">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.idType')">Id Type</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.dateDebut')">Date Debut</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.dateFin')">Date Fin</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.circonscription')">Circonscription</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.libelleAffichage')">Libelle Affichage</span></th>
            <th scope="row"><span v-text="$t('irsenApp.mandatAncien.fonctionAncien')">Fonction Ancien</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="mandatAncien in mandatAnciens" :key="mandatAncien.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MandatAncienView', params: { mandatAncienId: mandatAncien.id } }">{{
                mandatAncien.id
              }}</router-link>
            </td>
            <td>{{ mandatAncien.idType }}</td>
            <td>{{ mandatAncien.libelle }}</td>
            <td>{{ mandatAncien.dateDebut }}</td>
            <td>{{ mandatAncien.dateFin }}</td>
            <td>{{ mandatAncien.circonscription }}</td>
            <td>{{ mandatAncien.libelleAffichage }}</td>
            <td>
              <div v-if="mandatAncien.fonctionAncien">
                <router-link :to="{ name: 'FonctionAncienView', params: { fonctionAncienId: mandatAncien.fonctionAncien.id } }">{{
                  mandatAncien.fonctionAncien.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MandatAncienView', params: { mandatAncienId: mandatAncien.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MandatAncienEdit', params: { mandatAncienId: mandatAncien.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(mandatAncien)"
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
        ><span id="irsenApp.mandatAncien.delete.question" data-cy="mandatAncienDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-mandatAncien-heading" v-text="$t('irsenApp.mandatAncien.delete.question', { id: removeId })">
          Are you sure you want to delete this Mandat Ancien?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-mandatAncien"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMandatAncien()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./mandat-ancien.component.ts"></script>

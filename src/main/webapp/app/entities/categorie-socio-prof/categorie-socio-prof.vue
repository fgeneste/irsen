<template>
  <div>
    <h2 id="page-heading" data-cy="CategorieSocioProfHeading">
      <span v-text="$t('irsenApp.categorieSocioProf.home.title')" id="categorie-socio-prof-heading">Categorie Socio Profs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.categorieSocioProf.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CategorieSocioProfCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-categorie-socio-prof"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.categorieSocioProf.home.createLabel')"> Create a new Categorie Socio Prof </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && categorieSocioProfs && categorieSocioProfs.length === 0">
      <span v-text="$t('irsenApp.categorieSocioProf.home.notFound')">No categorieSocioProfs found</span>
    </div>
    <div class="table-responsive" v-if="categorieSocioProfs && categorieSocioProfs.length > 0">
      <table class="table table-striped" aria-describedby="categorieSocioProfs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.categorieSocioProf.code')">Code</span></th>
            <th scope="row"><span v-text="$t('irsenApp.categorieSocioProf.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('irsenApp.categorieSocioProf.libelleComplet')">Libelle Complet</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="categorieSocioProf in categorieSocioProfs" :key="categorieSocioProf.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CategorieSocioProfView', params: { categorieSocioProfId: categorieSocioProf.id } }">{{
                categorieSocioProf.id
              }}</router-link>
            </td>
            <td>{{ categorieSocioProf.code }}</td>
            <td>{{ categorieSocioProf.libelle }}</td>
            <td>{{ categorieSocioProf.libelleComplet }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CategorieSocioProfView', params: { categorieSocioProfId: categorieSocioProf.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CategorieSocioProfEdit', params: { categorieSocioProfId: categorieSocioProf.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(categorieSocioProf)"
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
          id="irsenApp.categorieSocioProf.delete.question"
          data-cy="categorieSocioProfDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-categorieSocioProf-heading" v-text="$t('irsenApp.categorieSocioProf.delete.question', { id: removeId })">
          Are you sure you want to delete this Categorie Socio Prof?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-categorieSocioProf"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCategorieSocioProf()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./categorie-socio-prof.component.ts"></script>

<template>
  <div>
    <h2 id="page-heading" data-cy="EtatCivilHeading">
      <span v-text="$t('irsenApp.etatCivil.home.title')" id="etat-civil-heading">Etat Civils</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('irsenApp.etatCivil.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EtatCivilCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-etat-civil"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('irsenApp.etatCivil.home.createLabel')"> Create a new Etat Civil </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && etatCivils && etatCivils.length === 0">
      <span v-text="$t('irsenApp.etatCivil.home.notFound')">No etatCivils found</span>
    </div>
    <div class="table-responsive" v-if="etatCivils && etatCivils.length > 0">
      <table class="table table-striped" aria-describedby="etatCivils">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.matricule')">Matricule</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.civilite')">Civilite</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.titre')">Titre</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.nomFamille')">Nom Famille</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.nomMarital')">Nom Marital</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.nomUsuel')">Nom Usuel</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.prenoms')">Prenoms</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.prenomUsuel')">Prenom Usuel</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.dateNaissance')">Date Naissance</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.communeNaissance')">Commune Naissance</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.profession')">Profession</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.courriel')">Courriel</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.courriel2')">Courriel 2</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.telephonePortable')">Telephone Portable</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.telephonePortable2')">Telephone Portable 2</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.telephoneFixe')">Telephone Fixe</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.departementNaissance')">Departement Naissance</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.paysNaissance')">Pays Naissance</span></th>
            <th scope="row"><span v-text="$t('irsenApp.etatCivil.categorieSocioProf')">Categorie Socio Prof</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="etatCivil in etatCivils" :key="etatCivil.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EtatCivilView', params: { etatCivilId: etatCivil.id } }">{{ etatCivil.id }}</router-link>
            </td>
            <td>{{ etatCivil.matricule }}</td>
            <td>{{ etatCivil.civilite }}</td>
            <td>{{ etatCivil.titre }}</td>
            <td>{{ etatCivil.nomFamille }}</td>
            <td>{{ etatCivil.nomMarital }}</td>
            <td>{{ etatCivil.nomUsuel }}</td>
            <td>{{ etatCivil.prenoms }}</td>
            <td>{{ etatCivil.prenomUsuel }}</td>
            <td>{{ etatCivil.dateNaissance }}</td>
            <td>{{ etatCivil.communeNaissance }}</td>
            <td>{{ etatCivil.profession }}</td>
            <td>{{ etatCivil.courriel }}</td>
            <td>{{ etatCivil.courriel2 }}</td>
            <td>{{ etatCivil.telephonePortable }}</td>
            <td>{{ etatCivil.telephonePortable2 }}</td>
            <td>{{ etatCivil.telephoneFixe }}</td>
            <td>
              <div v-if="etatCivil.departementNaissance">
                <router-link
                  :to="{ name: 'DepartementNaissanceView', params: { departementNaissanceId: etatCivil.departementNaissance.id } }"
                  >{{ etatCivil.departementNaissance.id }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="etatCivil.paysNaissance">
                <router-link :to="{ name: 'PaysNaissanceView', params: { paysNaissanceId: etatCivil.paysNaissance.id } }">{{
                  etatCivil.paysNaissance.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="etatCivil.categorieSocioProf">
                <router-link :to="{ name: 'CategorieSocioProfView', params: { categorieSocioProfId: etatCivil.categorieSocioProf.id } }">{{
                  etatCivil.categorieSocioProf.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EtatCivilView', params: { etatCivilId: etatCivil.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EtatCivilEdit', params: { etatCivilId: etatCivil.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(etatCivil)"
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
        ><span id="irsenApp.etatCivil.delete.question" data-cy="etatCivilDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-etatCivil-heading" v-text="$t('irsenApp.etatCivil.delete.question', { id: removeId })">
          Are you sure you want to delete this Etat Civil?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-etatCivil"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEtatCivil()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./etat-civil.component.ts"></script>

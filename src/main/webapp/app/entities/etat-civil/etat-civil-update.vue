<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="irsenApp.etatCivil.home.createOrEditLabel"
          data-cy="EtatCivilCreateUpdateHeading"
          v-text="$t('irsenApp.etatCivil.home.createOrEditLabel')"
        >
          Create or edit a EtatCivil
        </h2>
        <div>
          <div class="form-group" v-if="etatCivil.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="etatCivil.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.matricule')" for="etat-civil-matricule">Matricule</label>
            <input
              type="text"
              class="form-control"
              name="matricule"
              id="etat-civil-matricule"
              data-cy="matricule"
              :class="{ valid: !$v.etatCivil.matricule.$invalid, invalid: $v.etatCivil.matricule.$invalid }"
              v-model="$v.etatCivil.matricule.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.civilite')" for="etat-civil-civilite">Civilite</label>
            <input
              type="text"
              class="form-control"
              name="civilite"
              id="etat-civil-civilite"
              data-cy="civilite"
              :class="{ valid: !$v.etatCivil.civilite.$invalid, invalid: $v.etatCivil.civilite.$invalid }"
              v-model="$v.etatCivil.civilite.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.titre')" for="etat-civil-titre">Titre</label>
            <input
              type="text"
              class="form-control"
              name="titre"
              id="etat-civil-titre"
              data-cy="titre"
              :class="{ valid: !$v.etatCivil.titre.$invalid, invalid: $v.etatCivil.titre.$invalid }"
              v-model="$v.etatCivil.titre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.nomFamille')" for="etat-civil-nomFamille">Nom Famille</label>
            <input
              type="text"
              class="form-control"
              name="nomFamille"
              id="etat-civil-nomFamille"
              data-cy="nomFamille"
              :class="{ valid: !$v.etatCivil.nomFamille.$invalid, invalid: $v.etatCivil.nomFamille.$invalid }"
              v-model="$v.etatCivil.nomFamille.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.nomMarital')" for="etat-civil-nomMarital">Nom Marital</label>
            <input
              type="text"
              class="form-control"
              name="nomMarital"
              id="etat-civil-nomMarital"
              data-cy="nomMarital"
              :class="{ valid: !$v.etatCivil.nomMarital.$invalid, invalid: $v.etatCivil.nomMarital.$invalid }"
              v-model="$v.etatCivil.nomMarital.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.nomUsuel')" for="etat-civil-nomUsuel">Nom Usuel</label>
            <input
              type="text"
              class="form-control"
              name="nomUsuel"
              id="etat-civil-nomUsuel"
              data-cy="nomUsuel"
              :class="{ valid: !$v.etatCivil.nomUsuel.$invalid, invalid: $v.etatCivil.nomUsuel.$invalid }"
              v-model="$v.etatCivil.nomUsuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.prenoms')" for="etat-civil-prenoms">Prenoms</label>
            <input
              type="text"
              class="form-control"
              name="prenoms"
              id="etat-civil-prenoms"
              data-cy="prenoms"
              :class="{ valid: !$v.etatCivil.prenoms.$invalid, invalid: $v.etatCivil.prenoms.$invalid }"
              v-model="$v.etatCivil.prenoms.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.prenomUsuel')" for="etat-civil-prenomUsuel"
              >Prenom Usuel</label
            >
            <input
              type="text"
              class="form-control"
              name="prenomUsuel"
              id="etat-civil-prenomUsuel"
              data-cy="prenomUsuel"
              :class="{ valid: !$v.etatCivil.prenomUsuel.$invalid, invalid: $v.etatCivil.prenomUsuel.$invalid }"
              v-model="$v.etatCivil.prenomUsuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.dateNaissance')" for="etat-civil-dateNaissance"
              >Date Naissance</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="etat-civil-dateNaissance"
                  v-model="$v.etatCivil.dateNaissance.$model"
                  name="dateNaissance"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="etat-civil-dateNaissance"
                data-cy="dateNaissance"
                type="text"
                class="form-control"
                name="dateNaissance"
                :class="{ valid: !$v.etatCivil.dateNaissance.$invalid, invalid: $v.etatCivil.dateNaissance.$invalid }"
                v-model="$v.etatCivil.dateNaissance.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.communeNaissance')" for="etat-civil-communeNaissance"
              >Commune Naissance</label
            >
            <input
              type="text"
              class="form-control"
              name="communeNaissance"
              id="etat-civil-communeNaissance"
              data-cy="communeNaissance"
              :class="{ valid: !$v.etatCivil.communeNaissance.$invalid, invalid: $v.etatCivil.communeNaissance.$invalid }"
              v-model="$v.etatCivil.communeNaissance.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.profession')" for="etat-civil-profession">Profession</label>
            <input
              type="text"
              class="form-control"
              name="profession"
              id="etat-civil-profession"
              data-cy="profession"
              :class="{ valid: !$v.etatCivil.profession.$invalid, invalid: $v.etatCivil.profession.$invalid }"
              v-model="$v.etatCivil.profession.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.courriel')" for="etat-civil-courriel">Courriel</label>
            <input
              type="text"
              class="form-control"
              name="courriel"
              id="etat-civil-courriel"
              data-cy="courriel"
              :class="{ valid: !$v.etatCivil.courriel.$invalid, invalid: $v.etatCivil.courriel.$invalid }"
              v-model="$v.etatCivil.courriel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.courriel2')" for="etat-civil-courriel2">Courriel 2</label>
            <input
              type="text"
              class="form-control"
              name="courriel2"
              id="etat-civil-courriel2"
              data-cy="courriel2"
              :class="{ valid: !$v.etatCivil.courriel2.$invalid, invalid: $v.etatCivil.courriel2.$invalid }"
              v-model="$v.etatCivil.courriel2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.departementNaissance')" for="etat-civil-departementNaissance"
              >Departement Naissance</label
            >
            <select
              class="form-control"
              id="etat-civil-departementNaissance"
              data-cy="departementNaissance"
              name="departementNaissance"
              v-model="etatCivil.departementNaissance"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.departementNaissance && departementNaissanceOption.id === etatCivil.departementNaissance.id
                    ? etatCivil.departementNaissance
                    : departementNaissanceOption
                "
                v-for="departementNaissanceOption in departementNaissances"
                :key="departementNaissanceOption.id"
              >
                {{ departementNaissanceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.paysNaissance')" for="etat-civil-paysNaissance"
              >Pays Naissance</label
            >
            <select
              class="form-control"
              id="etat-civil-paysNaissance"
              data-cy="paysNaissance"
              name="paysNaissance"
              v-model="etatCivil.paysNaissance"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.paysNaissance && paysNaissanceOption.id === etatCivil.paysNaissance.id
                    ? etatCivil.paysNaissance
                    : paysNaissanceOption
                "
                v-for="paysNaissanceOption in paysNaissances"
                :key="paysNaissanceOption.id"
              >
                {{ paysNaissanceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.categorieSocioProf')" for="etat-civil-categorieSocioProf"
              >Categorie Socio Prof</label
            >
            <select
              class="form-control"
              id="etat-civil-categorieSocioProf"
              data-cy="categorieSocioProf"
              name="categorieSocioProf"
              v-model="etatCivil.categorieSocioProf"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.categorieSocioProf && categorieSocioProfOption.id === etatCivil.categorieSocioProf.id
                    ? etatCivil.categorieSocioProf
                    : categorieSocioProfOption
                "
                v-for="categorieSocioProfOption in categorieSocioProfs"
                :key="categorieSocioProfOption.id"
              >
                {{ categorieSocioProfOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.telephonePortable')" for="etat-civil-telephonePortable"
              >Telephone Portable</label
            >
            <select
              class="form-control"
              id="etat-civil-telephonePortable"
              data-cy="telephonePortable"
              name="telephonePortable"
              v-model="etatCivil.telephonePortable"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.telephonePortable && telephonePortableOption.id === etatCivil.telephonePortable.id
                    ? etatCivil.telephonePortable
                    : telephonePortableOption
                "
                v-for="telephonePortableOption in telephonePortables"
                :key="telephonePortableOption.id"
              >
                {{ telephonePortableOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.telephonePortable2')" for="etat-civil-telephonePortable2"
              >Telephone Portable 2</label
            >
            <select
              class="form-control"
              id="etat-civil-telephonePortable2"
              data-cy="telephonePortable2"
              name="telephonePortable2"
              v-model="etatCivil.telephonePortable2"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.telephonePortable2 && telephonePortable2Option.id === etatCivil.telephonePortable2.id
                    ? etatCivil.telephonePortable2
                    : telephonePortable2Option
                "
                v-for="telephonePortable2Option in telephonePortable2s"
                :key="telephonePortable2Option.id"
              >
                {{ telephonePortable2Option.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('irsenApp.etatCivil.telephoneFixe')" for="etat-civil-telephoneFixe"
              >Telephone Fixe</label
            >
            <select
              class="form-control"
              id="etat-civil-telephoneFixe"
              data-cy="telephoneFixe"
              name="telephoneFixe"
              v-model="etatCivil.telephoneFixe"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  etatCivil.telephoneFixe && telephoneFixeOption.id === etatCivil.telephoneFixe.id
                    ? etatCivil.telephoneFixe
                    : telephoneFixeOption
                "
                v-for="telephoneFixeOption in telephoneFixes"
                :key="telephoneFixeOption.id"
              >
                {{ telephoneFixeOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.etatCivil.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./etat-civil-update.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="etatCivil">
        <h2 class="jh-entity-heading" data-cy="etatCivilDetailsHeading">
          <span v-text="$t('irsenApp.etatCivil.detail.title')">EtatCivil</span> {{ etatCivil.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.etatCivil.matricule')">Matricule</span>
          </dt>
          <dd>
            <span>{{ etatCivil.matricule }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.civilite')">Civilite</span>
          </dt>
          <dd>
            <span>{{ etatCivil.civilite }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.titre')">Titre</span>
          </dt>
          <dd>
            <span>{{ etatCivil.titre }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.nomFamille')">Nom Famille</span>
          </dt>
          <dd>
            <span>{{ etatCivil.nomFamille }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.nomMarital')">Nom Marital</span>
          </dt>
          <dd>
            <span>{{ etatCivil.nomMarital }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.nomUsuel')">Nom Usuel</span>
          </dt>
          <dd>
            <span>{{ etatCivil.nomUsuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.prenoms')">Prenoms</span>
          </dt>
          <dd>
            <span>{{ etatCivil.prenoms }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.prenomUsuel')">Prenom Usuel</span>
          </dt>
          <dd>
            <span>{{ etatCivil.prenomUsuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.dateNaissance')">Date Naissance</span>
          </dt>
          <dd>
            <span>{{ etatCivil.dateNaissance }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.communeNaissance')">Commune Naissance</span>
          </dt>
          <dd>
            <span>{{ etatCivil.communeNaissance }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.profession')">Profession</span>
          </dt>
          <dd>
            <span>{{ etatCivil.profession }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.courriel')">Courriel</span>
          </dt>
          <dd>
            <span>{{ etatCivil.courriel }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.courriel2')">Courriel 2</span>
          </dt>
          <dd>
            <span>{{ etatCivil.courriel2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.telephonePortable')">Telephone Portable</span>
          </dt>
          <dd>
            <span>{{ etatCivil.telephonePortable }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.telephonePortable2')">Telephone Portable 2</span>
          </dt>
          <dd>
            <span>{{ etatCivil.telephonePortable2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.telephoneFixe')">Telephone Fixe</span>
          </dt>
          <dd>
            <span>{{ etatCivil.telephoneFixe }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.departementNaissance')">Departement Naissance</span>
          </dt>
          <dd>
            <div v-if="etatCivil.departementNaissance">
              <router-link
                :to="{ name: 'DepartementNaissanceView', params: { departementNaissanceId: etatCivil.departementNaissance.id } }"
                >{{ etatCivil.departementNaissance.id }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.paysNaissance')">Pays Naissance</span>
          </dt>
          <dd>
            <div v-if="etatCivil.paysNaissance">
              <router-link :to="{ name: 'PaysNaissanceView', params: { paysNaissanceId: etatCivil.paysNaissance.id } }">{{
                etatCivil.paysNaissance.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.etatCivil.categorieSocioProf')">Categorie Socio Prof</span>
          </dt>
          <dd>
            <div v-if="etatCivil.categorieSocioProf">
              <router-link :to="{ name: 'CategorieSocioProfView', params: { categorieSocioProfId: etatCivil.categorieSocioProf.id } }">{{
                etatCivil.categorieSocioProf.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="etatCivil.id"
          :to="{ name: 'EtatCivilEdit', params: { etatCivilId: etatCivil.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./etat-civil-details.component.ts"></script>

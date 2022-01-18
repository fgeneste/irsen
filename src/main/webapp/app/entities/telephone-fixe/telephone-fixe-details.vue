<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="telephoneFixe">
        <h2 class="jh-entity-heading" data-cy="telephoneFixeDetailsHeading">
          <span v-text="$t('irsenApp.telephoneFixe.detail.title')">TelephoneFixe</span> {{ telephoneFixe.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.telephoneFixe.type')">Type</span>
          </dt>
          <dd>
            <span>{{ telephoneFixe.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.telephoneFixe.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ telephoneFixe.numero }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="telephoneFixe.id"
          :to="{ name: 'TelephoneFixeEdit', params: { telephoneFixeId: telephoneFixe.id } }"
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

<script lang="ts" src="./telephone-fixe-details.component.ts"></script>

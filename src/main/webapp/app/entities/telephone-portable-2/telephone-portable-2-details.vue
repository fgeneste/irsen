<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="telephonePortable2">
        <h2 class="jh-entity-heading" data-cy="telephonePortable2DetailsHeading">
          <span v-text="$t('irsenApp.telephonePortable2.detail.title')">TelephonePortable2</span> {{ telephonePortable2.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.telephonePortable2.type')">Type</span>
          </dt>
          <dd>
            <span>{{ telephonePortable2.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.telephonePortable2.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ telephonePortable2.numero }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="telephonePortable2.id"
          :to="{ name: 'TelephonePortable2Edit', params: { telephonePortable2Id: telephonePortable2.id } }"
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

<script lang="ts" src="./telephone-portable-2-details.component.ts"></script>

<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="decoration">
        <h2 class="jh-entity-heading" data-cy="decorationDetailsHeading">
          <span v-text="$t('irsenApp.decoration.detail.title')">Decoration</span> {{ decoration.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('irsenApp.decoration.type')">Type</span>
          </dt>
          <dd>
            <span>{{ decoration.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.decoration.grade')">Grade</span>
          </dt>
          <dd>
            <span>{{ decoration.grade }}</span>
          </dd>
          <dt>
            <span v-text="$t('irsenApp.decoration.senateur')">Senateur</span>
          </dt>
          <dd>
            <div v-if="decoration.senateur">
              <router-link :to="{ name: 'SenateurView', params: { senateurId: decoration.senateur.id } }">{{
                decoration.senateur.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="decoration.id"
          :to="{ name: 'DecorationEdit', params: { decorationId: decoration.id } }"
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

<script lang="ts" src="./decoration-details.component.ts"></script>

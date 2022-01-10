/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SenateurUpdateComponent from '@/entities/senateur/senateur-update.vue';
import SenateurClass from '@/entities/senateur/senateur-update.component';
import SenateurService from '@/entities/senateur/senateur.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';

import AdressesService from '@/entities/adresses/adresses.service';

import MandatService from '@/entities/mandat/mandat.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Senateur Management Update Component', () => {
    let wrapper: Wrapper<SenateurClass>;
    let comp: SenateurClass;
    let senateurServiceStub: SinonStubbedInstance<SenateurService>;

    beforeEach(() => {
      senateurServiceStub = sinon.createStubInstance<SenateurService>(SenateurService);

      wrapper = shallowMount<SenateurClass>(SenateurUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          senateurService: () => senateurServiceStub,
          alertService: () => new AlertService(),

          etatCivilService: () =>
            sinon.createStubInstance<EtatCivilService>(EtatCivilService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          adressesService: () =>
            sinon.createStubInstance<AdressesService>(AdressesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          mandatService: () =>
            sinon.createStubInstance<MandatService>(MandatService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.senateur = entity;
        senateurServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(senateurServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.senateur = entity;
        senateurServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(senateurServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSenateur = { id: 123 };
        senateurServiceStub.find.resolves(foundSenateur);
        senateurServiceStub.retrieve.resolves([foundSenateur]);

        // WHEN
        comp.beforeRouteEnter({ params: { senateurId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.senateur).toBe(foundSenateur);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

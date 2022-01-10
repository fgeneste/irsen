/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdressesUpdateComponent from '@/entities/adresses/adresses-update.vue';
import AdressesClass from '@/entities/adresses/adresses-update.component';
import AdressesService from '@/entities/adresses/adresses.service';

import AdresseFiscaleService from '@/entities/adresse-fiscale/adresse-fiscale.service';

import AdressePostaleService from '@/entities/adresse-postale/adresse-postale.service';

import AdressePostale2Service from '@/entities/adresse-postale-2/adresse-postale-2.service';

import SenateurService from '@/entities/senateur/senateur.service';
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
  describe('Adresses Management Update Component', () => {
    let wrapper: Wrapper<AdressesClass>;
    let comp: AdressesClass;
    let adressesServiceStub: SinonStubbedInstance<AdressesService>;

    beforeEach(() => {
      adressesServiceStub = sinon.createStubInstance<AdressesService>(AdressesService);

      wrapper = shallowMount<AdressesClass>(AdressesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          adressesService: () => adressesServiceStub,
          alertService: () => new AlertService(),

          adresseFiscaleService: () =>
            sinon.createStubInstance<AdresseFiscaleService>(AdresseFiscaleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          adressePostaleService: () =>
            sinon.createStubInstance<AdressePostaleService>(AdressePostaleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          adressePostale2Service: () =>
            sinon.createStubInstance<AdressePostale2Service>(AdressePostale2Service, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          senateurService: () =>
            sinon.createStubInstance<SenateurService>(SenateurService, {
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
        comp.adresses = entity;
        adressesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.adresses = entity;
        adressesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdresses = { id: 123 };
        adressesServiceStub.find.resolves(foundAdresses);
        adressesServiceStub.retrieve.resolves([foundAdresses]);

        // WHEN
        comp.beforeRouteEnter({ params: { adressesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.adresses).toBe(foundAdresses);
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

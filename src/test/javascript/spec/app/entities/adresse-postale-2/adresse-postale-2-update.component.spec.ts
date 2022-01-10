/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdressePostale2UpdateComponent from '@/entities/adresse-postale-2/adresse-postale-2-update.vue';
import AdressePostale2Class from '@/entities/adresse-postale-2/adresse-postale-2-update.component';
import AdressePostale2Service from '@/entities/adresse-postale-2/adresse-postale-2.service';

import AdressesService from '@/entities/adresses/adresses.service';
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
  describe('AdressePostale2 Management Update Component', () => {
    let wrapper: Wrapper<AdressePostale2Class>;
    let comp: AdressePostale2Class;
    let adressePostale2ServiceStub: SinonStubbedInstance<AdressePostale2Service>;

    beforeEach(() => {
      adressePostale2ServiceStub = sinon.createStubInstance<AdressePostale2Service>(AdressePostale2Service);

      wrapper = shallowMount<AdressePostale2Class>(AdressePostale2UpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          adressePostale2Service: () => adressePostale2ServiceStub,
          alertService: () => new AlertService(),

          adressesService: () =>
            sinon.createStubInstance<AdressesService>(AdressesService, {
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
        comp.adressePostale2 = entity;
        adressePostale2ServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressePostale2ServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.adressePostale2 = entity;
        adressePostale2ServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adressePostale2ServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdressePostale2 = { id: 123 };
        adressePostale2ServiceStub.find.resolves(foundAdressePostale2);
        adressePostale2ServiceStub.retrieve.resolves([foundAdressePostale2]);

        // WHEN
        comp.beforeRouteEnter({ params: { adressePostale2Id: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.adressePostale2).toBe(foundAdressePostale2);
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

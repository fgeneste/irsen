/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EtatCivilUpdateComponent from '@/entities/etat-civil/etat-civil-update.vue';
import EtatCivilClass from '@/entities/etat-civil/etat-civil-update.component';
import EtatCivilService from '@/entities/etat-civil/etat-civil.service';

import DepartementNaissanceService from '@/entities/departement-naissance/departement-naissance.service';

import PaysNaissanceService from '@/entities/pays-naissance/pays-naissance.service';

import CategorieSocioProfService from '@/entities/categorie-socio-prof/categorie-socio-prof.service';

import TelephonePortableService from '@/entities/telephone-portable/telephone-portable.service';

import TelephonePortable2Service from '@/entities/telephone-portable-2/telephone-portable-2.service';

import TelephoneFixeService from '@/entities/telephone-fixe/telephone-fixe.service';

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
  describe('EtatCivil Management Update Component', () => {
    let wrapper: Wrapper<EtatCivilClass>;
    let comp: EtatCivilClass;
    let etatCivilServiceStub: SinonStubbedInstance<EtatCivilService>;

    beforeEach(() => {
      etatCivilServiceStub = sinon.createStubInstance<EtatCivilService>(EtatCivilService);

      wrapper = shallowMount<EtatCivilClass>(EtatCivilUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          etatCivilService: () => etatCivilServiceStub,
          alertService: () => new AlertService(),

          departementNaissanceService: () =>
            sinon.createStubInstance<DepartementNaissanceService>(DepartementNaissanceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          paysNaissanceService: () =>
            sinon.createStubInstance<PaysNaissanceService>(PaysNaissanceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          categorieSocioProfService: () =>
            sinon.createStubInstance<CategorieSocioProfService>(CategorieSocioProfService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          telephonePortableService: () =>
            sinon.createStubInstance<TelephonePortableService>(TelephonePortableService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          telephonePortable2Service: () =>
            sinon.createStubInstance<TelephonePortable2Service>(TelephonePortable2Service, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          telephoneFixeService: () =>
            sinon.createStubInstance<TelephoneFixeService>(TelephoneFixeService, {
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
        comp.etatCivil = entity;
        etatCivilServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(etatCivilServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.etatCivil = entity;
        etatCivilServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(etatCivilServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEtatCivil = { id: 123 };
        etatCivilServiceStub.find.resolves(foundEtatCivil);
        etatCivilServiceStub.retrieve.resolves([foundEtatCivil]);

        // WHEN
        comp.beforeRouteEnter({ params: { etatCivilId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.etatCivil).toBe(foundEtatCivil);
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

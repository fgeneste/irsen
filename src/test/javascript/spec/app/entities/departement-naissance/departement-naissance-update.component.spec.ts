/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DepartementNaissanceUpdateComponent from '@/entities/departement-naissance/departement-naissance-update.vue';
import DepartementNaissanceClass from '@/entities/departement-naissance/departement-naissance-update.component';
import DepartementNaissanceService from '@/entities/departement-naissance/departement-naissance.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
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
  describe('DepartementNaissance Management Update Component', () => {
    let wrapper: Wrapper<DepartementNaissanceClass>;
    let comp: DepartementNaissanceClass;
    let departementNaissanceServiceStub: SinonStubbedInstance<DepartementNaissanceService>;

    beforeEach(() => {
      departementNaissanceServiceStub = sinon.createStubInstance<DepartementNaissanceService>(DepartementNaissanceService);

      wrapper = shallowMount<DepartementNaissanceClass>(DepartementNaissanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          departementNaissanceService: () => departementNaissanceServiceStub,
          alertService: () => new AlertService(),

          etatCivilService: () =>
            sinon.createStubInstance<EtatCivilService>(EtatCivilService, {
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
        comp.departementNaissance = entity;
        departementNaissanceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(departementNaissanceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.departementNaissance = entity;
        departementNaissanceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(departementNaissanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDepartementNaissance = { id: 123 };
        departementNaissanceServiceStub.find.resolves(foundDepartementNaissance);
        departementNaissanceServiceStub.retrieve.resolves([foundDepartementNaissance]);

        // WHEN
        comp.beforeRouteEnter({ params: { departementNaissanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.departementNaissance).toBe(foundDepartementNaissance);
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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PaysNaissanceUpdateComponent from '@/entities/pays-naissance/pays-naissance-update.vue';
import PaysNaissanceClass from '@/entities/pays-naissance/pays-naissance-update.component';
import PaysNaissanceService from '@/entities/pays-naissance/pays-naissance.service';

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
  describe('PaysNaissance Management Update Component', () => {
    let wrapper: Wrapper<PaysNaissanceClass>;
    let comp: PaysNaissanceClass;
    let paysNaissanceServiceStub: SinonStubbedInstance<PaysNaissanceService>;

    beforeEach(() => {
      paysNaissanceServiceStub = sinon.createStubInstance<PaysNaissanceService>(PaysNaissanceService);

      wrapper = shallowMount<PaysNaissanceClass>(PaysNaissanceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          paysNaissanceService: () => paysNaissanceServiceStub,
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
        comp.paysNaissance = entity;
        paysNaissanceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paysNaissanceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.paysNaissance = entity;
        paysNaissanceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paysNaissanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPaysNaissance = { id: 123 };
        paysNaissanceServiceStub.find.resolves(foundPaysNaissance);
        paysNaissanceServiceStub.retrieve.resolves([foundPaysNaissance]);

        // WHEN
        comp.beforeRouteEnter({ params: { paysNaissanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.paysNaissance).toBe(foundPaysNaissance);
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

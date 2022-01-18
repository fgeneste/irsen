/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephoneFixeUpdateComponent from '@/entities/telephone-fixe/telephone-fixe-update.vue';
import TelephoneFixeClass from '@/entities/telephone-fixe/telephone-fixe-update.component';
import TelephoneFixeService from '@/entities/telephone-fixe/telephone-fixe.service';

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
  describe('TelephoneFixe Management Update Component', () => {
    let wrapper: Wrapper<TelephoneFixeClass>;
    let comp: TelephoneFixeClass;
    let telephoneFixeServiceStub: SinonStubbedInstance<TelephoneFixeService>;

    beforeEach(() => {
      telephoneFixeServiceStub = sinon.createStubInstance<TelephoneFixeService>(TelephoneFixeService);

      wrapper = shallowMount<TelephoneFixeClass>(TelephoneFixeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          telephoneFixeService: () => telephoneFixeServiceStub,
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
        comp.telephoneFixe = entity;
        telephoneFixeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephoneFixeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.telephoneFixe = entity;
        telephoneFixeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephoneFixeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTelephoneFixe = { id: 123 };
        telephoneFixeServiceStub.find.resolves(foundTelephoneFixe);
        telephoneFixeServiceStub.retrieve.resolves([foundTelephoneFixe]);

        // WHEN
        comp.beforeRouteEnter({ params: { telephoneFixeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.telephoneFixe).toBe(foundTelephoneFixe);
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

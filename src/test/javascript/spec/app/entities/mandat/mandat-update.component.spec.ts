/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MandatUpdateComponent from '@/entities/mandat/mandat-update.vue';
import MandatClass from '@/entities/mandat/mandat-update.component';
import MandatService from '@/entities/mandat/mandat.service';

import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';

import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';

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
  describe('Mandat Management Update Component', () => {
    let wrapper: Wrapper<MandatClass>;
    let comp: MandatClass;
    let mandatServiceStub: SinonStubbedInstance<MandatService>;

    beforeEach(() => {
      mandatServiceStub = sinon.createStubInstance<MandatService>(MandatService);

      wrapper = shallowMount<MandatClass>(MandatUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          mandatService: () => mandatServiceStub,
          alertService: () => new AlertService(),

          mandatAncienService: () =>
            sinon.createStubInstance<MandatAncienService>(MandatAncienService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          mandatEnCoursService: () =>
            sinon.createStubInstance<MandatEnCoursService>(MandatEnCoursService, {
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
        comp.mandat = entity;
        mandatServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.mandat = entity;
        mandatServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandat = { id: 123 };
        mandatServiceStub.find.resolves(foundMandat);
        mandatServiceStub.retrieve.resolves([foundMandat]);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandat).toBe(foundMandat);
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

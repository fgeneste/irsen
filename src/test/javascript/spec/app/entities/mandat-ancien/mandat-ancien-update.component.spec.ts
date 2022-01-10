/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MandatAncienUpdateComponent from '@/entities/mandat-ancien/mandat-ancien-update.vue';
import MandatAncienClass from '@/entities/mandat-ancien/mandat-ancien-update.component';
import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';

import FonctionAncienService from '@/entities/fonction-ancien/fonction-ancien.service';

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
  describe('MandatAncien Management Update Component', () => {
    let wrapper: Wrapper<MandatAncienClass>;
    let comp: MandatAncienClass;
    let mandatAncienServiceStub: SinonStubbedInstance<MandatAncienService>;

    beforeEach(() => {
      mandatAncienServiceStub = sinon.createStubInstance<MandatAncienService>(MandatAncienService);

      wrapper = shallowMount<MandatAncienClass>(MandatAncienUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          mandatAncienService: () => mandatAncienServiceStub,
          alertService: () => new AlertService(),

          fonctionAncienService: () =>
            sinon.createStubInstance<FonctionAncienService>(FonctionAncienService, {
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
        comp.mandatAncien = entity;
        mandatAncienServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatAncienServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.mandatAncien = entity;
        mandatAncienServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatAncienServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandatAncien = { id: 123 };
        mandatAncienServiceStub.find.resolves(foundMandatAncien);
        mandatAncienServiceStub.retrieve.resolves([foundMandatAncien]);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatAncienId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandatAncien).toBe(foundMandatAncien);
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

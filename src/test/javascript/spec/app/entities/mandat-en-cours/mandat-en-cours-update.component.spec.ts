/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MandatEnCoursUpdateComponent from '@/entities/mandat-en-cours/mandat-en-cours-update.vue';
import MandatEnCoursClass from '@/entities/mandat-en-cours/mandat-en-cours-update.component';
import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';

import FonctionEnCoursService from '@/entities/fonction-en-cours/fonction-en-cours.service';

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
  describe('MandatEnCours Management Update Component', () => {
    let wrapper: Wrapper<MandatEnCoursClass>;
    let comp: MandatEnCoursClass;
    let mandatEnCoursServiceStub: SinonStubbedInstance<MandatEnCoursService>;

    beforeEach(() => {
      mandatEnCoursServiceStub = sinon.createStubInstance<MandatEnCoursService>(MandatEnCoursService);

      wrapper = shallowMount<MandatEnCoursClass>(MandatEnCoursUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          mandatEnCoursService: () => mandatEnCoursServiceStub,
          alertService: () => new AlertService(),

          fonctionEnCoursService: () =>
            sinon.createStubInstance<FonctionEnCoursService>(FonctionEnCoursService, {
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
        comp.mandatEnCours = entity;
        mandatEnCoursServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatEnCoursServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.mandatEnCours = entity;
        mandatEnCoursServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(mandatEnCoursServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandatEnCours = { id: 123 };
        mandatEnCoursServiceStub.find.resolves(foundMandatEnCours);
        mandatEnCoursServiceStub.retrieve.resolves([foundMandatEnCours]);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatEnCoursId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandatEnCours).toBe(foundMandatEnCours);
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

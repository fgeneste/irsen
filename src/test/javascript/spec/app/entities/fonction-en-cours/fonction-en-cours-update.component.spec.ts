/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FonctionEnCoursUpdateComponent from '@/entities/fonction-en-cours/fonction-en-cours-update.vue';
import FonctionEnCoursClass from '@/entities/fonction-en-cours/fonction-en-cours-update.component';
import FonctionEnCoursService from '@/entities/fonction-en-cours/fonction-en-cours.service';

import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';
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
  describe('FonctionEnCours Management Update Component', () => {
    let wrapper: Wrapper<FonctionEnCoursClass>;
    let comp: FonctionEnCoursClass;
    let fonctionEnCoursServiceStub: SinonStubbedInstance<FonctionEnCoursService>;

    beforeEach(() => {
      fonctionEnCoursServiceStub = sinon.createStubInstance<FonctionEnCoursService>(FonctionEnCoursService);

      wrapper = shallowMount<FonctionEnCoursClass>(FonctionEnCoursUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          fonctionEnCoursService: () => fonctionEnCoursServiceStub,
          alertService: () => new AlertService(),

          mandatEnCoursService: () =>
            sinon.createStubInstance<MandatEnCoursService>(MandatEnCoursService, {
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
        comp.fonctionEnCours = entity;
        fonctionEnCoursServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fonctionEnCoursServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fonctionEnCours = entity;
        fonctionEnCoursServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fonctionEnCoursServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFonctionEnCours = { id: 123 };
        fonctionEnCoursServiceStub.find.resolves(foundFonctionEnCours);
        fonctionEnCoursServiceStub.retrieve.resolves([foundFonctionEnCours]);

        // WHEN
        comp.beforeRouteEnter({ params: { fonctionEnCoursId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionEnCours).toBe(foundFonctionEnCours);
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

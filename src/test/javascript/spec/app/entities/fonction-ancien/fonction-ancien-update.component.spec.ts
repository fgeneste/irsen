/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FonctionAncienUpdateComponent from '@/entities/fonction-ancien/fonction-ancien-update.vue';
import FonctionAncienClass from '@/entities/fonction-ancien/fonction-ancien-update.component';
import FonctionAncienService from '@/entities/fonction-ancien/fonction-ancien.service';

import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
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
  describe('FonctionAncien Management Update Component', () => {
    let wrapper: Wrapper<FonctionAncienClass>;
    let comp: FonctionAncienClass;
    let fonctionAncienServiceStub: SinonStubbedInstance<FonctionAncienService>;

    beforeEach(() => {
      fonctionAncienServiceStub = sinon.createStubInstance<FonctionAncienService>(FonctionAncienService);

      wrapper = shallowMount<FonctionAncienClass>(FonctionAncienUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          fonctionAncienService: () => fonctionAncienServiceStub,
          alertService: () => new AlertService(),

          mandatAncienService: () =>
            sinon.createStubInstance<MandatAncienService>(MandatAncienService, {
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
        comp.fonctionAncien = entity;
        fonctionAncienServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fonctionAncienServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fonctionAncien = entity;
        fonctionAncienServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fonctionAncienServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFonctionAncien = { id: 123 };
        fonctionAncienServiceStub.find.resolves(foundFonctionAncien);
        fonctionAncienServiceStub.retrieve.resolves([foundFonctionAncien]);

        // WHEN
        comp.beforeRouteEnter({ params: { fonctionAncienId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionAncien).toBe(foundFonctionAncien);
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

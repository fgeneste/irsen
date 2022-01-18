/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DecorationUpdateComponent from '@/entities/decoration/decoration-update.vue';
import DecorationClass from '@/entities/decoration/decoration-update.component';
import DecorationService from '@/entities/decoration/decoration.service';

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
  describe('Decoration Management Update Component', () => {
    let wrapper: Wrapper<DecorationClass>;
    let comp: DecorationClass;
    let decorationServiceStub: SinonStubbedInstance<DecorationService>;

    beforeEach(() => {
      decorationServiceStub = sinon.createStubInstance<DecorationService>(DecorationService);

      wrapper = shallowMount<DecorationClass>(DecorationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          decorationService: () => decorationServiceStub,
          alertService: () => new AlertService(),

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
        comp.decoration = entity;
        decorationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(decorationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.decoration = entity;
        decorationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(decorationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDecoration = { id: 123 };
        decorationServiceStub.find.resolves(foundDecoration);
        decorationServiceStub.retrieve.resolves([foundDecoration]);

        // WHEN
        comp.beforeRouteEnter({ params: { decorationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.decoration).toBe(foundDecoration);
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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephonePortableUpdateComponent from '@/entities/telephone-portable/telephone-portable-update.vue';
import TelephonePortableClass from '@/entities/telephone-portable/telephone-portable-update.component';
import TelephonePortableService from '@/entities/telephone-portable/telephone-portable.service';

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
  describe('TelephonePortable Management Update Component', () => {
    let wrapper: Wrapper<TelephonePortableClass>;
    let comp: TelephonePortableClass;
    let telephonePortableServiceStub: SinonStubbedInstance<TelephonePortableService>;

    beforeEach(() => {
      telephonePortableServiceStub = sinon.createStubInstance<TelephonePortableService>(TelephonePortableService);

      wrapper = shallowMount<TelephonePortableClass>(TelephonePortableUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          telephonePortableService: () => telephonePortableServiceStub,
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
        comp.telephonePortable = entity;
        telephonePortableServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephonePortableServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.telephonePortable = entity;
        telephonePortableServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephonePortableServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTelephonePortable = { id: 123 };
        telephonePortableServiceStub.find.resolves(foundTelephonePortable);
        telephonePortableServiceStub.retrieve.resolves([foundTelephonePortable]);

        // WHEN
        comp.beforeRouteEnter({ params: { telephonePortableId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.telephonePortable).toBe(foundTelephonePortable);
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

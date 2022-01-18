/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephonePortable2UpdateComponent from '@/entities/telephone-portable-2/telephone-portable-2-update.vue';
import TelephonePortable2Class from '@/entities/telephone-portable-2/telephone-portable-2-update.component';
import TelephonePortable2Service from '@/entities/telephone-portable-2/telephone-portable-2.service';

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
  describe('TelephonePortable2 Management Update Component', () => {
    let wrapper: Wrapper<TelephonePortable2Class>;
    let comp: TelephonePortable2Class;
    let telephonePortable2ServiceStub: SinonStubbedInstance<TelephonePortable2Service>;

    beforeEach(() => {
      telephonePortable2ServiceStub = sinon.createStubInstance<TelephonePortable2Service>(TelephonePortable2Service);

      wrapper = shallowMount<TelephonePortable2Class>(TelephonePortable2UpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          telephonePortable2Service: () => telephonePortable2ServiceStub,
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
        comp.telephonePortable2 = entity;
        telephonePortable2ServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephonePortable2ServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.telephonePortable2 = entity;
        telephonePortable2ServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(telephonePortable2ServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTelephonePortable2 = { id: 123 };
        telephonePortable2ServiceStub.find.resolves(foundTelephonePortable2);
        telephonePortable2ServiceStub.retrieve.resolves([foundTelephonePortable2]);

        // WHEN
        comp.beforeRouteEnter({ params: { telephonePortable2Id: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.telephonePortable2).toBe(foundTelephonePortable2);
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

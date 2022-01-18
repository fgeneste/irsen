/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephoneFixeComponent from '@/entities/telephone-fixe/telephone-fixe.vue';
import TelephoneFixeClass from '@/entities/telephone-fixe/telephone-fixe.component';
import TelephoneFixeService from '@/entities/telephone-fixe/telephone-fixe.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('TelephoneFixe Management Component', () => {
    let wrapper: Wrapper<TelephoneFixeClass>;
    let comp: TelephoneFixeClass;
    let telephoneFixeServiceStub: SinonStubbedInstance<TelephoneFixeService>;

    beforeEach(() => {
      telephoneFixeServiceStub = sinon.createStubInstance<TelephoneFixeService>(TelephoneFixeService);
      telephoneFixeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TelephoneFixeClass>(TelephoneFixeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          telephoneFixeService: () => telephoneFixeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      telephoneFixeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTelephoneFixes();
      await comp.$nextTick();

      // THEN
      expect(telephoneFixeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.telephoneFixes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      telephoneFixeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(telephoneFixeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTelephoneFixe();
      await comp.$nextTick();

      // THEN
      expect(telephoneFixeServiceStub.delete.called).toBeTruthy();
      expect(telephoneFixeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

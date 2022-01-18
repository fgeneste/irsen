/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephonePortable2Component from '@/entities/telephone-portable-2/telephone-portable-2.vue';
import TelephonePortable2Class from '@/entities/telephone-portable-2/telephone-portable-2.component';
import TelephonePortable2Service from '@/entities/telephone-portable-2/telephone-portable-2.service';
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
  describe('TelephonePortable2 Management Component', () => {
    let wrapper: Wrapper<TelephonePortable2Class>;
    let comp: TelephonePortable2Class;
    let telephonePortable2ServiceStub: SinonStubbedInstance<TelephonePortable2Service>;

    beforeEach(() => {
      telephonePortable2ServiceStub = sinon.createStubInstance<TelephonePortable2Service>(TelephonePortable2Service);
      telephonePortable2ServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TelephonePortable2Class>(TelephonePortable2Component, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          telephonePortable2Service: () => telephonePortable2ServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      telephonePortable2ServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTelephonePortable2s();
      await comp.$nextTick();

      // THEN
      expect(telephonePortable2ServiceStub.retrieve.called).toBeTruthy();
      expect(comp.telephonePortable2s[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      telephonePortable2ServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(telephonePortable2ServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTelephonePortable2();
      await comp.$nextTick();

      // THEN
      expect(telephonePortable2ServiceStub.delete.called).toBeTruthy();
      expect(telephonePortable2ServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

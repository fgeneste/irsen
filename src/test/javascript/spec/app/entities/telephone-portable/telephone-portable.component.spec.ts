/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TelephonePortableComponent from '@/entities/telephone-portable/telephone-portable.vue';
import TelephonePortableClass from '@/entities/telephone-portable/telephone-portable.component';
import TelephonePortableService from '@/entities/telephone-portable/telephone-portable.service';
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
  describe('TelephonePortable Management Component', () => {
    let wrapper: Wrapper<TelephonePortableClass>;
    let comp: TelephonePortableClass;
    let telephonePortableServiceStub: SinonStubbedInstance<TelephonePortableService>;

    beforeEach(() => {
      telephonePortableServiceStub = sinon.createStubInstance<TelephonePortableService>(TelephonePortableService);
      telephonePortableServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TelephonePortableClass>(TelephonePortableComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          telephonePortableService: () => telephonePortableServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      telephonePortableServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTelephonePortables();
      await comp.$nextTick();

      // THEN
      expect(telephonePortableServiceStub.retrieve.called).toBeTruthy();
      expect(comp.telephonePortables[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      telephonePortableServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(telephonePortableServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTelephonePortable();
      await comp.$nextTick();

      // THEN
      expect(telephonePortableServiceStub.delete.called).toBeTruthy();
      expect(telephonePortableServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdressesComponent from '@/entities/adresses/adresses.vue';
import AdressesClass from '@/entities/adresses/adresses.component';
import AdressesService from '@/entities/adresses/adresses.service';
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
  describe('Adresses Management Component', () => {
    let wrapper: Wrapper<AdressesClass>;
    let comp: AdressesClass;
    let adressesServiceStub: SinonStubbedInstance<AdressesService>;

    beforeEach(() => {
      adressesServiceStub = sinon.createStubInstance<AdressesService>(AdressesService);
      adressesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdressesClass>(AdressesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          adressesService: () => adressesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adressesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdressess();
      await comp.$nextTick();

      // THEN
      expect(adressesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adresses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      adressesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(adressesServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAdresses();
      await comp.$nextTick();

      // THEN
      expect(adressesServiceStub.delete.called).toBeTruthy();
      expect(adressesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

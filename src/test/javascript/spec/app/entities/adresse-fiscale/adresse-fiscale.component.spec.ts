/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdresseFiscaleComponent from '@/entities/adresse-fiscale/adresse-fiscale.vue';
import AdresseFiscaleClass from '@/entities/adresse-fiscale/adresse-fiscale.component';
import AdresseFiscaleService from '@/entities/adresse-fiscale/adresse-fiscale.service';
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
  describe('AdresseFiscale Management Component', () => {
    let wrapper: Wrapper<AdresseFiscaleClass>;
    let comp: AdresseFiscaleClass;
    let adresseFiscaleServiceStub: SinonStubbedInstance<AdresseFiscaleService>;

    beforeEach(() => {
      adresseFiscaleServiceStub = sinon.createStubInstance<AdresseFiscaleService>(AdresseFiscaleService);
      adresseFiscaleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdresseFiscaleClass>(AdresseFiscaleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          adresseFiscaleService: () => adresseFiscaleServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adresseFiscaleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdresseFiscales();
      await comp.$nextTick();

      // THEN
      expect(adresseFiscaleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adresseFiscales[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      adresseFiscaleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(adresseFiscaleServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAdresseFiscale();
      await comp.$nextTick();

      // THEN
      expect(adresseFiscaleServiceStub.delete.called).toBeTruthy();
      expect(adresseFiscaleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

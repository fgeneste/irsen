/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdressePostaleComponent from '@/entities/adresse-postale/adresse-postale.vue';
import AdressePostaleClass from '@/entities/adresse-postale/adresse-postale.component';
import AdressePostaleService from '@/entities/adresse-postale/adresse-postale.service';
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
  describe('AdressePostale Management Component', () => {
    let wrapper: Wrapper<AdressePostaleClass>;
    let comp: AdressePostaleClass;
    let adressePostaleServiceStub: SinonStubbedInstance<AdressePostaleService>;

    beforeEach(() => {
      adressePostaleServiceStub = sinon.createStubInstance<AdressePostaleService>(AdressePostaleService);
      adressePostaleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdressePostaleClass>(AdressePostaleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          adressePostaleService: () => adressePostaleServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adressePostaleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdressePostales();
      await comp.$nextTick();

      // THEN
      expect(adressePostaleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adressePostales[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      adressePostaleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(adressePostaleServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAdressePostale();
      await comp.$nextTick();

      // THEN
      expect(adressePostaleServiceStub.delete.called).toBeTruthy();
      expect(adressePostaleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

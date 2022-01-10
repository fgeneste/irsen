/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdressePostale2Component from '@/entities/adresse-postale-2/adresse-postale-2.vue';
import AdressePostale2Class from '@/entities/adresse-postale-2/adresse-postale-2.component';
import AdressePostale2Service from '@/entities/adresse-postale-2/adresse-postale-2.service';
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
  describe('AdressePostale2 Management Component', () => {
    let wrapper: Wrapper<AdressePostale2Class>;
    let comp: AdressePostale2Class;
    let adressePostale2ServiceStub: SinonStubbedInstance<AdressePostale2Service>;

    beforeEach(() => {
      adressePostale2ServiceStub = sinon.createStubInstance<AdressePostale2Service>(AdressePostale2Service);
      adressePostale2ServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdressePostale2Class>(AdressePostale2Component, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          adressePostale2Service: () => adressePostale2ServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adressePostale2ServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdressePostale2s();
      await comp.$nextTick();

      // THEN
      expect(adressePostale2ServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adressePostale2s[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      adressePostale2ServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(adressePostale2ServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAdressePostale2();
      await comp.$nextTick();

      // THEN
      expect(adressePostale2ServiceStub.delete.called).toBeTruthy();
      expect(adressePostale2ServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

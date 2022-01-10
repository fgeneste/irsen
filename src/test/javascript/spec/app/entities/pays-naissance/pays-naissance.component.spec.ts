/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PaysNaissanceComponent from '@/entities/pays-naissance/pays-naissance.vue';
import PaysNaissanceClass from '@/entities/pays-naissance/pays-naissance.component';
import PaysNaissanceService from '@/entities/pays-naissance/pays-naissance.service';
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
  describe('PaysNaissance Management Component', () => {
    let wrapper: Wrapper<PaysNaissanceClass>;
    let comp: PaysNaissanceClass;
    let paysNaissanceServiceStub: SinonStubbedInstance<PaysNaissanceService>;

    beforeEach(() => {
      paysNaissanceServiceStub = sinon.createStubInstance<PaysNaissanceService>(PaysNaissanceService);
      paysNaissanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PaysNaissanceClass>(PaysNaissanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          paysNaissanceService: () => paysNaissanceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      paysNaissanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPaysNaissances();
      await comp.$nextTick();

      // THEN
      expect(paysNaissanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.paysNaissances[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      paysNaissanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(paysNaissanceServiceStub.retrieve.callCount).toEqual(1);

      comp.removePaysNaissance();
      await comp.$nextTick();

      // THEN
      expect(paysNaissanceServiceStub.delete.called).toBeTruthy();
      expect(paysNaissanceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

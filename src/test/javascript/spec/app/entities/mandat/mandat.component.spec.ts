/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MandatComponent from '@/entities/mandat/mandat.vue';
import MandatClass from '@/entities/mandat/mandat.component';
import MandatService from '@/entities/mandat/mandat.service';
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
  describe('Mandat Management Component', () => {
    let wrapper: Wrapper<MandatClass>;
    let comp: MandatClass;
    let mandatServiceStub: SinonStubbedInstance<MandatService>;

    beforeEach(() => {
      mandatServiceStub = sinon.createStubInstance<MandatService>(MandatService);
      mandatServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MandatClass>(MandatComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          mandatService: () => mandatServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      mandatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMandats();
      await comp.$nextTick();

      // THEN
      expect(mandatServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mandats[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      mandatServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(mandatServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMandat();
      await comp.$nextTick();

      // THEN
      expect(mandatServiceStub.delete.called).toBeTruthy();
      expect(mandatServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MandatAncienComponent from '@/entities/mandat-ancien/mandat-ancien.vue';
import MandatAncienClass from '@/entities/mandat-ancien/mandat-ancien.component';
import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
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
  describe('MandatAncien Management Component', () => {
    let wrapper: Wrapper<MandatAncienClass>;
    let comp: MandatAncienClass;
    let mandatAncienServiceStub: SinonStubbedInstance<MandatAncienService>;

    beforeEach(() => {
      mandatAncienServiceStub = sinon.createStubInstance<MandatAncienService>(MandatAncienService);
      mandatAncienServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MandatAncienClass>(MandatAncienComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          mandatAncienService: () => mandatAncienServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      mandatAncienServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMandatAnciens();
      await comp.$nextTick();

      // THEN
      expect(mandatAncienServiceStub.retrieve.called).toBeTruthy();
      expect(comp.mandatAnciens[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      mandatAncienServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(mandatAncienServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMandatAncien();
      await comp.$nextTick();

      // THEN
      expect(mandatAncienServiceStub.delete.called).toBeTruthy();
      expect(mandatAncienServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

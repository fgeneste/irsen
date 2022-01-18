/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DecorationComponent from '@/entities/decoration/decoration.vue';
import DecorationClass from '@/entities/decoration/decoration.component';
import DecorationService from '@/entities/decoration/decoration.service';
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
  describe('Decoration Management Component', () => {
    let wrapper: Wrapper<DecorationClass>;
    let comp: DecorationClass;
    let decorationServiceStub: SinonStubbedInstance<DecorationService>;

    beforeEach(() => {
      decorationServiceStub = sinon.createStubInstance<DecorationService>(DecorationService);
      decorationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DecorationClass>(DecorationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          decorationService: () => decorationServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      decorationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDecorations();
      await comp.$nextTick();

      // THEN
      expect(decorationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.decorations[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      decorationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(decorationServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDecoration();
      await comp.$nextTick();

      // THEN
      expect(decorationServiceStub.delete.called).toBeTruthy();
      expect(decorationServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FonctionAncienComponent from '@/entities/fonction-ancien/fonction-ancien.vue';
import FonctionAncienClass from '@/entities/fonction-ancien/fonction-ancien.component';
import FonctionAncienService from '@/entities/fonction-ancien/fonction-ancien.service';
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
  describe('FonctionAncien Management Component', () => {
    let wrapper: Wrapper<FonctionAncienClass>;
    let comp: FonctionAncienClass;
    let fonctionAncienServiceStub: SinonStubbedInstance<FonctionAncienService>;

    beforeEach(() => {
      fonctionAncienServiceStub = sinon.createStubInstance<FonctionAncienService>(FonctionAncienService);
      fonctionAncienServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FonctionAncienClass>(FonctionAncienComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          fonctionAncienService: () => fonctionAncienServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      fonctionAncienServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFonctionAnciens();
      await comp.$nextTick();

      // THEN
      expect(fonctionAncienServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fonctionAnciens[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      fonctionAncienServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(fonctionAncienServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFonctionAncien();
      await comp.$nextTick();

      // THEN
      expect(fonctionAncienServiceStub.delete.called).toBeTruthy();
      expect(fonctionAncienServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

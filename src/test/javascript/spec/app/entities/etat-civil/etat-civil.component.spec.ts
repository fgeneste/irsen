/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EtatCivilComponent from '@/entities/etat-civil/etat-civil.vue';
import EtatCivilClass from '@/entities/etat-civil/etat-civil.component';
import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
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
  describe('EtatCivil Management Component', () => {
    let wrapper: Wrapper<EtatCivilClass>;
    let comp: EtatCivilClass;
    let etatCivilServiceStub: SinonStubbedInstance<EtatCivilService>;

    beforeEach(() => {
      etatCivilServiceStub = sinon.createStubInstance<EtatCivilService>(EtatCivilService);
      etatCivilServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EtatCivilClass>(EtatCivilComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          etatCivilService: () => etatCivilServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      etatCivilServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEtatCivils();
      await comp.$nextTick();

      // THEN
      expect(etatCivilServiceStub.retrieve.called).toBeTruthy();
      expect(comp.etatCivils[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      etatCivilServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(etatCivilServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEtatCivil();
      await comp.$nextTick();

      // THEN
      expect(etatCivilServiceStub.delete.called).toBeTruthy();
      expect(etatCivilServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SenateurComponent from '@/entities/senateur/senateur.vue';
import SenateurClass from '@/entities/senateur/senateur.component';
import SenateurService from '@/entities/senateur/senateur.service';
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
  describe('Senateur Management Component', () => {
    let wrapper: Wrapper<SenateurClass>;
    let comp: SenateurClass;
    let senateurServiceStub: SinonStubbedInstance<SenateurService>;

    beforeEach(() => {
      senateurServiceStub = sinon.createStubInstance<SenateurService>(SenateurService);
      senateurServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SenateurClass>(SenateurComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          senateurService: () => senateurServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      senateurServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSenateurs();
      await comp.$nextTick();

      // THEN
      expect(senateurServiceStub.retrieve.called).toBeTruthy();
      expect(comp.senateurs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      senateurServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(senateurServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSenateur();
      await comp.$nextTick();

      // THEN
      expect(senateurServiceStub.delete.called).toBeTruthy();
      expect(senateurServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

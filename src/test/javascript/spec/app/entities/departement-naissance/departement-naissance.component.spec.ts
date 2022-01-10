/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DepartementNaissanceComponent from '@/entities/departement-naissance/departement-naissance.vue';
import DepartementNaissanceClass from '@/entities/departement-naissance/departement-naissance.component';
import DepartementNaissanceService from '@/entities/departement-naissance/departement-naissance.service';
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
  describe('DepartementNaissance Management Component', () => {
    let wrapper: Wrapper<DepartementNaissanceClass>;
    let comp: DepartementNaissanceClass;
    let departementNaissanceServiceStub: SinonStubbedInstance<DepartementNaissanceService>;

    beforeEach(() => {
      departementNaissanceServiceStub = sinon.createStubInstance<DepartementNaissanceService>(DepartementNaissanceService);
      departementNaissanceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DepartementNaissanceClass>(DepartementNaissanceComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          departementNaissanceService: () => departementNaissanceServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      departementNaissanceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDepartementNaissances();
      await comp.$nextTick();

      // THEN
      expect(departementNaissanceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.departementNaissances[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      departementNaissanceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(departementNaissanceServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDepartementNaissance();
      await comp.$nextTick();

      // THEN
      expect(departementNaissanceServiceStub.delete.called).toBeTruthy();
      expect(departementNaissanceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

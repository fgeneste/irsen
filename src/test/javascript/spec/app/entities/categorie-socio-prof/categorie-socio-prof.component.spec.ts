/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CategorieSocioProfComponent from '@/entities/categorie-socio-prof/categorie-socio-prof.vue';
import CategorieSocioProfClass from '@/entities/categorie-socio-prof/categorie-socio-prof.component';
import CategorieSocioProfService from '@/entities/categorie-socio-prof/categorie-socio-prof.service';
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
  describe('CategorieSocioProf Management Component', () => {
    let wrapper: Wrapper<CategorieSocioProfClass>;
    let comp: CategorieSocioProfClass;
    let categorieSocioProfServiceStub: SinonStubbedInstance<CategorieSocioProfService>;

    beforeEach(() => {
      categorieSocioProfServiceStub = sinon.createStubInstance<CategorieSocioProfService>(CategorieSocioProfService);
      categorieSocioProfServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CategorieSocioProfClass>(CategorieSocioProfComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          categorieSocioProfService: () => categorieSocioProfServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      categorieSocioProfServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCategorieSocioProfs();
      await comp.$nextTick();

      // THEN
      expect(categorieSocioProfServiceStub.retrieve.called).toBeTruthy();
      expect(comp.categorieSocioProfs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      categorieSocioProfServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(categorieSocioProfServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCategorieSocioProf();
      await comp.$nextTick();

      // THEN
      expect(categorieSocioProfServiceStub.delete.called).toBeTruthy();
      expect(categorieSocioProfServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

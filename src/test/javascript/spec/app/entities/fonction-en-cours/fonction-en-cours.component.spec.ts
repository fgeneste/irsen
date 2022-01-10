/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FonctionEnCoursComponent from '@/entities/fonction-en-cours/fonction-en-cours.vue';
import FonctionEnCoursClass from '@/entities/fonction-en-cours/fonction-en-cours.component';
import FonctionEnCoursService from '@/entities/fonction-en-cours/fonction-en-cours.service';
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
  describe('FonctionEnCours Management Component', () => {
    let wrapper: Wrapper<FonctionEnCoursClass>;
    let comp: FonctionEnCoursClass;
    let fonctionEnCoursServiceStub: SinonStubbedInstance<FonctionEnCoursService>;

    beforeEach(() => {
      fonctionEnCoursServiceStub = sinon.createStubInstance<FonctionEnCoursService>(FonctionEnCoursService);
      fonctionEnCoursServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FonctionEnCoursClass>(FonctionEnCoursComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          fonctionEnCoursService: () => fonctionEnCoursServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      fonctionEnCoursServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFonctionEnCourss();
      await comp.$nextTick();

      // THEN
      expect(fonctionEnCoursServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fonctionEnCours[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      fonctionEnCoursServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(fonctionEnCoursServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFonctionEnCours();
      await comp.$nextTick();

      // THEN
      expect(fonctionEnCoursServiceStub.delete.called).toBeTruthy();
      expect(fonctionEnCoursServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

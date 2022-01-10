/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FonctionEnCoursDetailComponent from '@/entities/fonction-en-cours/fonction-en-cours-details.vue';
import FonctionEnCoursClass from '@/entities/fonction-en-cours/fonction-en-cours-details.component';
import FonctionEnCoursService from '@/entities/fonction-en-cours/fonction-en-cours.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FonctionEnCours Management Detail Component', () => {
    let wrapper: Wrapper<FonctionEnCoursClass>;
    let comp: FonctionEnCoursClass;
    let fonctionEnCoursServiceStub: SinonStubbedInstance<FonctionEnCoursService>;

    beforeEach(() => {
      fonctionEnCoursServiceStub = sinon.createStubInstance<FonctionEnCoursService>(FonctionEnCoursService);

      wrapper = shallowMount<FonctionEnCoursClass>(FonctionEnCoursDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { fonctionEnCoursService: () => fonctionEnCoursServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFonctionEnCours = { id: 123 };
        fonctionEnCoursServiceStub.find.resolves(foundFonctionEnCours);

        // WHEN
        comp.retrieveFonctionEnCours(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionEnCours).toBe(foundFonctionEnCours);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFonctionEnCours = { id: 123 };
        fonctionEnCoursServiceStub.find.resolves(foundFonctionEnCours);

        // WHEN
        comp.beforeRouteEnter({ params: { fonctionEnCoursId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionEnCours).toBe(foundFonctionEnCours);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

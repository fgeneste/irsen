/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MandatEnCoursDetailComponent from '@/entities/mandat-en-cours/mandat-en-cours-details.vue';
import MandatEnCoursClass from '@/entities/mandat-en-cours/mandat-en-cours-details.component';
import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';
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
  describe('MandatEnCours Management Detail Component', () => {
    let wrapper: Wrapper<MandatEnCoursClass>;
    let comp: MandatEnCoursClass;
    let mandatEnCoursServiceStub: SinonStubbedInstance<MandatEnCoursService>;

    beforeEach(() => {
      mandatEnCoursServiceStub = sinon.createStubInstance<MandatEnCoursService>(MandatEnCoursService);

      wrapper = shallowMount<MandatEnCoursClass>(MandatEnCoursDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { mandatEnCoursService: () => mandatEnCoursServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMandatEnCours = { id: 123 };
        mandatEnCoursServiceStub.find.resolves(foundMandatEnCours);

        // WHEN
        comp.retrieveMandatEnCours(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mandatEnCours).toBe(foundMandatEnCours);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandatEnCours = { id: 123 };
        mandatEnCoursServiceStub.find.resolves(foundMandatEnCours);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatEnCoursId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandatEnCours).toBe(foundMandatEnCours);
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

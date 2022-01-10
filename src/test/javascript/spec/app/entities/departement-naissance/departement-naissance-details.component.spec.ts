/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DepartementNaissanceDetailComponent from '@/entities/departement-naissance/departement-naissance-details.vue';
import DepartementNaissanceClass from '@/entities/departement-naissance/departement-naissance-details.component';
import DepartementNaissanceService from '@/entities/departement-naissance/departement-naissance.service';
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
  describe('DepartementNaissance Management Detail Component', () => {
    let wrapper: Wrapper<DepartementNaissanceClass>;
    let comp: DepartementNaissanceClass;
    let departementNaissanceServiceStub: SinonStubbedInstance<DepartementNaissanceService>;

    beforeEach(() => {
      departementNaissanceServiceStub = sinon.createStubInstance<DepartementNaissanceService>(DepartementNaissanceService);

      wrapper = shallowMount<DepartementNaissanceClass>(DepartementNaissanceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { departementNaissanceService: () => departementNaissanceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDepartementNaissance = { id: 123 };
        departementNaissanceServiceStub.find.resolves(foundDepartementNaissance);

        // WHEN
        comp.retrieveDepartementNaissance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.departementNaissance).toBe(foundDepartementNaissance);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDepartementNaissance = { id: 123 };
        departementNaissanceServiceStub.find.resolves(foundDepartementNaissance);

        // WHEN
        comp.beforeRouteEnter({ params: { departementNaissanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.departementNaissance).toBe(foundDepartementNaissance);
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

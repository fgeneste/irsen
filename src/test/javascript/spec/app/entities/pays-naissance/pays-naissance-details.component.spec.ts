/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PaysNaissanceDetailComponent from '@/entities/pays-naissance/pays-naissance-details.vue';
import PaysNaissanceClass from '@/entities/pays-naissance/pays-naissance-details.component';
import PaysNaissanceService from '@/entities/pays-naissance/pays-naissance.service';
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
  describe('PaysNaissance Management Detail Component', () => {
    let wrapper: Wrapper<PaysNaissanceClass>;
    let comp: PaysNaissanceClass;
    let paysNaissanceServiceStub: SinonStubbedInstance<PaysNaissanceService>;

    beforeEach(() => {
      paysNaissanceServiceStub = sinon.createStubInstance<PaysNaissanceService>(PaysNaissanceService);

      wrapper = shallowMount<PaysNaissanceClass>(PaysNaissanceDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { paysNaissanceService: () => paysNaissanceServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPaysNaissance = { id: 123 };
        paysNaissanceServiceStub.find.resolves(foundPaysNaissance);

        // WHEN
        comp.retrievePaysNaissance(123);
        await comp.$nextTick();

        // THEN
        expect(comp.paysNaissance).toBe(foundPaysNaissance);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPaysNaissance = { id: 123 };
        paysNaissanceServiceStub.find.resolves(foundPaysNaissance);

        // WHEN
        comp.beforeRouteEnter({ params: { paysNaissanceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.paysNaissance).toBe(foundPaysNaissance);
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

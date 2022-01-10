/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MandatDetailComponent from '@/entities/mandat/mandat-details.vue';
import MandatClass from '@/entities/mandat/mandat-details.component';
import MandatService from '@/entities/mandat/mandat.service';
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
  describe('Mandat Management Detail Component', () => {
    let wrapper: Wrapper<MandatClass>;
    let comp: MandatClass;
    let mandatServiceStub: SinonStubbedInstance<MandatService>;

    beforeEach(() => {
      mandatServiceStub = sinon.createStubInstance<MandatService>(MandatService);

      wrapper = shallowMount<MandatClass>(MandatDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { mandatService: () => mandatServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMandat = { id: 123 };
        mandatServiceStub.find.resolves(foundMandat);

        // WHEN
        comp.retrieveMandat(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mandat).toBe(foundMandat);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandat = { id: 123 };
        mandatServiceStub.find.resolves(foundMandat);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandat).toBe(foundMandat);
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

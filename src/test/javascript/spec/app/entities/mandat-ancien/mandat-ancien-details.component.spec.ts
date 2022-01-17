/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MandatAncienDetailComponent from '@/entities/mandat-ancien/mandat-ancien-details.vue';
import MandatAncienClass from '@/entities/mandat-ancien/mandat-ancien-details.component';
import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
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
  describe('MandatAncien Management Detail Component', () => {
    let wrapper: Wrapper<MandatAncienClass>;
    let comp: MandatAncienClass;
    let mandatAncienServiceStub: SinonStubbedInstance<MandatAncienService>;

    beforeEach(() => {
      mandatAncienServiceStub = sinon.createStubInstance<MandatAncienService>(MandatAncienService);

      wrapper = shallowMount<MandatAncienClass>(MandatAncienDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { mandatAncienService: () => mandatAncienServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMandatAncien = { id: 123 };
        mandatAncienServiceStub.find.resolves(foundMandatAncien);

        // WHEN
        comp.retrieveMandatAncien(123);
        await comp.$nextTick();

        // THEN
        expect(comp.mandatAncien).toBe(foundMandatAncien);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMandatAncien = { id: 123 };
        mandatAncienServiceStub.find.resolves(foundMandatAncien);

        // WHEN
        comp.beforeRouteEnter({ params: { mandatAncienId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.mandatAncien).toBe(foundMandatAncien);
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

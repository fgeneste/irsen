/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EtatCivilDetailComponent from '@/entities/etat-civil/etat-civil-details.vue';
import EtatCivilClass from '@/entities/etat-civil/etat-civil-details.component';
import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
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
  describe('EtatCivil Management Detail Component', () => {
    let wrapper: Wrapper<EtatCivilClass>;
    let comp: EtatCivilClass;
    let etatCivilServiceStub: SinonStubbedInstance<EtatCivilService>;

    beforeEach(() => {
      etatCivilServiceStub = sinon.createStubInstance<EtatCivilService>(EtatCivilService);

      wrapper = shallowMount<EtatCivilClass>(EtatCivilDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { etatCivilService: () => etatCivilServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEtatCivil = { id: 123 };
        etatCivilServiceStub.find.resolves(foundEtatCivil);

        // WHEN
        comp.retrieveEtatCivil(123);
        await comp.$nextTick();

        // THEN
        expect(comp.etatCivil).toBe(foundEtatCivil);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEtatCivil = { id: 123 };
        etatCivilServiceStub.find.resolves(foundEtatCivil);

        // WHEN
        comp.beforeRouteEnter({ params: { etatCivilId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.etatCivil).toBe(foundEtatCivil);
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

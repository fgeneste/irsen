/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AdressesDetailComponent from '@/entities/adresses/adresses-details.vue';
import AdressesClass from '@/entities/adresses/adresses-details.component';
import AdressesService from '@/entities/adresses/adresses.service';
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
  describe('Adresses Management Detail Component', () => {
    let wrapper: Wrapper<AdressesClass>;
    let comp: AdressesClass;
    let adressesServiceStub: SinonStubbedInstance<AdressesService>;

    beforeEach(() => {
      adressesServiceStub = sinon.createStubInstance<AdressesService>(AdressesService);

      wrapper = shallowMount<AdressesClass>(AdressesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { adressesService: () => adressesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdresses = { id: 123 };
        adressesServiceStub.find.resolves(foundAdresses);

        // WHEN
        comp.retrieveAdresses(123);
        await comp.$nextTick();

        // THEN
        expect(comp.adresses).toBe(foundAdresses);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdresses = { id: 123 };
        adressesServiceStub.find.resolves(foundAdresses);

        // WHEN
        comp.beforeRouteEnter({ params: { adressesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.adresses).toBe(foundAdresses);
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

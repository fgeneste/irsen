/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AdressePostale2DetailComponent from '@/entities/adresse-postale-2/adresse-postale-2-details.vue';
import AdressePostale2Class from '@/entities/adresse-postale-2/adresse-postale-2-details.component';
import AdressePostale2Service from '@/entities/adresse-postale-2/adresse-postale-2.service';
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
  describe('AdressePostale2 Management Detail Component', () => {
    let wrapper: Wrapper<AdressePostale2Class>;
    let comp: AdressePostale2Class;
    let adressePostale2ServiceStub: SinonStubbedInstance<AdressePostale2Service>;

    beforeEach(() => {
      adressePostale2ServiceStub = sinon.createStubInstance<AdressePostale2Service>(AdressePostale2Service);

      wrapper = shallowMount<AdressePostale2Class>(AdressePostale2DetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { adressePostale2Service: () => adressePostale2ServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdressePostale2 = { id: 123 };
        adressePostale2ServiceStub.find.resolves(foundAdressePostale2);

        // WHEN
        comp.retrieveAdressePostale2(123);
        await comp.$nextTick();

        // THEN
        expect(comp.adressePostale2).toBe(foundAdressePostale2);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdressePostale2 = { id: 123 };
        adressePostale2ServiceStub.find.resolves(foundAdressePostale2);

        // WHEN
        comp.beforeRouteEnter({ params: { adressePostale2Id: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.adressePostale2).toBe(foundAdressePostale2);
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

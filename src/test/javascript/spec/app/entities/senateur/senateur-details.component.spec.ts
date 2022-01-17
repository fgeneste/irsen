/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SenateurDetailComponent from '@/entities/senateur/senateur-details.vue';
import SenateurClass from '@/entities/senateur/senateur-details.component';
import SenateurService from '@/entities/senateur/senateur.service';
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
  describe('Senateur Management Detail Component', () => {
    let wrapper: Wrapper<SenateurClass>;
    let comp: SenateurClass;
    let senateurServiceStub: SinonStubbedInstance<SenateurService>;

    beforeEach(() => {
      senateurServiceStub = sinon.createStubInstance<SenateurService>(SenateurService);

      wrapper = shallowMount<SenateurClass>(SenateurDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { senateurService: () => senateurServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSenateur = { id: 123 };
        senateurServiceStub.find.resolves(foundSenateur);

        // WHEN
        comp.retrieveSenateur(123);
        await comp.$nextTick();

        // THEN
        expect(comp.senateur).toBe(foundSenateur);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSenateur = { id: 123 };
        senateurServiceStub.find.resolves(foundSenateur);

        // WHEN
        comp.beforeRouteEnter({ params: { senateurId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.senateur).toBe(foundSenateur);
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

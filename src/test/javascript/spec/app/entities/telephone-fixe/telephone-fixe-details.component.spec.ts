/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TelephoneFixeDetailComponent from '@/entities/telephone-fixe/telephone-fixe-details.vue';
import TelephoneFixeClass from '@/entities/telephone-fixe/telephone-fixe-details.component';
import TelephoneFixeService from '@/entities/telephone-fixe/telephone-fixe.service';
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
  describe('TelephoneFixe Management Detail Component', () => {
    let wrapper: Wrapper<TelephoneFixeClass>;
    let comp: TelephoneFixeClass;
    let telephoneFixeServiceStub: SinonStubbedInstance<TelephoneFixeService>;

    beforeEach(() => {
      telephoneFixeServiceStub = sinon.createStubInstance<TelephoneFixeService>(TelephoneFixeService);

      wrapper = shallowMount<TelephoneFixeClass>(TelephoneFixeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { telephoneFixeService: () => telephoneFixeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTelephoneFixe = { id: 123 };
        telephoneFixeServiceStub.find.resolves(foundTelephoneFixe);

        // WHEN
        comp.retrieveTelephoneFixe(123);
        await comp.$nextTick();

        // THEN
        expect(comp.telephoneFixe).toBe(foundTelephoneFixe);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTelephoneFixe = { id: 123 };
        telephoneFixeServiceStub.find.resolves(foundTelephoneFixe);

        // WHEN
        comp.beforeRouteEnter({ params: { telephoneFixeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.telephoneFixe).toBe(foundTelephoneFixe);
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

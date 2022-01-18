/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TelephonePortable2DetailComponent from '@/entities/telephone-portable-2/telephone-portable-2-details.vue';
import TelephonePortable2Class from '@/entities/telephone-portable-2/telephone-portable-2-details.component';
import TelephonePortable2Service from '@/entities/telephone-portable-2/telephone-portable-2.service';
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
  describe('TelephonePortable2 Management Detail Component', () => {
    let wrapper: Wrapper<TelephonePortable2Class>;
    let comp: TelephonePortable2Class;
    let telephonePortable2ServiceStub: SinonStubbedInstance<TelephonePortable2Service>;

    beforeEach(() => {
      telephonePortable2ServiceStub = sinon.createStubInstance<TelephonePortable2Service>(TelephonePortable2Service);

      wrapper = shallowMount<TelephonePortable2Class>(TelephonePortable2DetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { telephonePortable2Service: () => telephonePortable2ServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTelephonePortable2 = { id: 123 };
        telephonePortable2ServiceStub.find.resolves(foundTelephonePortable2);

        // WHEN
        comp.retrieveTelephonePortable2(123);
        await comp.$nextTick();

        // THEN
        expect(comp.telephonePortable2).toBe(foundTelephonePortable2);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTelephonePortable2 = { id: 123 };
        telephonePortable2ServiceStub.find.resolves(foundTelephonePortable2);

        // WHEN
        comp.beforeRouteEnter({ params: { telephonePortable2Id: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.telephonePortable2).toBe(foundTelephonePortable2);
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

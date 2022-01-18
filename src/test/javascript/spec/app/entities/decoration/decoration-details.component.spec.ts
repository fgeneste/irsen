/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DecorationDetailComponent from '@/entities/decoration/decoration-details.vue';
import DecorationClass from '@/entities/decoration/decoration-details.component';
import DecorationService from '@/entities/decoration/decoration.service';
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
  describe('Decoration Management Detail Component', () => {
    let wrapper: Wrapper<DecorationClass>;
    let comp: DecorationClass;
    let decorationServiceStub: SinonStubbedInstance<DecorationService>;

    beforeEach(() => {
      decorationServiceStub = sinon.createStubInstance<DecorationService>(DecorationService);

      wrapper = shallowMount<DecorationClass>(DecorationDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { decorationService: () => decorationServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDecoration = { id: 123 };
        decorationServiceStub.find.resolves(foundDecoration);

        // WHEN
        comp.retrieveDecoration(123);
        await comp.$nextTick();

        // THEN
        expect(comp.decoration).toBe(foundDecoration);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDecoration = { id: 123 };
        decorationServiceStub.find.resolves(foundDecoration);

        // WHEN
        comp.beforeRouteEnter({ params: { decorationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.decoration).toBe(foundDecoration);
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

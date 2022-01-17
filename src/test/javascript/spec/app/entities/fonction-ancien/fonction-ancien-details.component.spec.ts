/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FonctionAncienDetailComponent from '@/entities/fonction-ancien/fonction-ancien-details.vue';
import FonctionAncienClass from '@/entities/fonction-ancien/fonction-ancien-details.component';
import FonctionAncienService from '@/entities/fonction-ancien/fonction-ancien.service';
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
  describe('FonctionAncien Management Detail Component', () => {
    let wrapper: Wrapper<FonctionAncienClass>;
    let comp: FonctionAncienClass;
    let fonctionAncienServiceStub: SinonStubbedInstance<FonctionAncienService>;

    beforeEach(() => {
      fonctionAncienServiceStub = sinon.createStubInstance<FonctionAncienService>(FonctionAncienService);

      wrapper = shallowMount<FonctionAncienClass>(FonctionAncienDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { fonctionAncienService: () => fonctionAncienServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFonctionAncien = { id: 123 };
        fonctionAncienServiceStub.find.resolves(foundFonctionAncien);

        // WHEN
        comp.retrieveFonctionAncien(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionAncien).toBe(foundFonctionAncien);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFonctionAncien = { id: 123 };
        fonctionAncienServiceStub.find.resolves(foundFonctionAncien);

        // WHEN
        comp.beforeRouteEnter({ params: { fonctionAncienId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fonctionAncien).toBe(foundFonctionAncien);
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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CategorieSocioProfDetailComponent from '@/entities/categorie-socio-prof/categorie-socio-prof-details.vue';
import CategorieSocioProfClass from '@/entities/categorie-socio-prof/categorie-socio-prof-details.component';
import CategorieSocioProfService from '@/entities/categorie-socio-prof/categorie-socio-prof.service';
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
  describe('CategorieSocioProf Management Detail Component', () => {
    let wrapper: Wrapper<CategorieSocioProfClass>;
    let comp: CategorieSocioProfClass;
    let categorieSocioProfServiceStub: SinonStubbedInstance<CategorieSocioProfService>;

    beforeEach(() => {
      categorieSocioProfServiceStub = sinon.createStubInstance<CategorieSocioProfService>(CategorieSocioProfService);

      wrapper = shallowMount<CategorieSocioProfClass>(CategorieSocioProfDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { categorieSocioProfService: () => categorieSocioProfServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCategorieSocioProf = { id: 123 };
        categorieSocioProfServiceStub.find.resolves(foundCategorieSocioProf);

        // WHEN
        comp.retrieveCategorieSocioProf(123);
        await comp.$nextTick();

        // THEN
        expect(comp.categorieSocioProf).toBe(foundCategorieSocioProf);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCategorieSocioProf = { id: 123 };
        categorieSocioProfServiceStub.find.resolves(foundCategorieSocioProf);

        // WHEN
        comp.beforeRouteEnter({ params: { categorieSocioProfId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.categorieSocioProf).toBe(foundCategorieSocioProf);
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

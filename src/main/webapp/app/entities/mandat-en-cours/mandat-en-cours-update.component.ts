import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import MandatService from '@/entities/mandat/mandat.service';
import { IMandat } from '@/shared/model/mandat.model';

import FonctionEnCoursService from '@/entities/fonction-en-cours/fonction-en-cours.service';
import { IFonctionEnCours } from '@/shared/model/fonction-en-cours.model';

import { IMandatEnCours, MandatEnCours } from '@/shared/model/mandat-en-cours.model';
import MandatEnCoursService from './mandat-en-cours.service';

const validations: any = {
  mandatEnCours: {
    idType: {},
    idFonction: {},
    code: {},
    libelle: {},
    libelleFonction: {},
    circonscription: {},
    depuis: {},
    dateElection: {},
    population: {},
    libelleAffichage: {},
  },
};

@Component({
  validations,
})
export default class MandatEnCoursUpdate extends Vue {
  @Inject('mandatEnCoursService') private mandatEnCoursService: () => MandatEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandatEnCours: IMandatEnCours = new MandatEnCours();

  @Inject('mandatService') private mandatService: () => MandatService;

  public mandats: IMandat[] = [];

  @Inject('fonctionEnCoursService') private fonctionEnCoursService: () => FonctionEnCoursService;

  public fonctionEnCours: IFonctionEnCours[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatEnCoursId) {
        vm.retrieveMandatEnCours(to.params.mandatEnCoursId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.mandatEnCours.id) {
      this.mandatEnCoursService()
        .update(this.mandatEnCours)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandatEnCours.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.mandatEnCoursService()
        .create(this.mandatEnCours)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandatEnCours.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveMandatEnCours(mandatEnCoursId): void {
    this.mandatEnCoursService()
      .find(mandatEnCoursId)
      .then(res => {
        this.mandatEnCours = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.mandatService()
      .retrieve()
      .then(res => {
        this.mandats = res.data;
      });
    this.fonctionEnCoursService()
      .retrieve()
      .then(res => {
        this.fonctionEnCours = res.data;
      });
  }
}

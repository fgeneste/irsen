import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { IPaysNaissance, PaysNaissance } from '@/shared/model/pays-naissance.model';
import PaysNaissanceService from './pays-naissance.service';

const validations: any = {
  paysNaissance: {
    code: {},
    libelle: {},
    avecConseilDepartemental: {},
    article: {},
  },
};

@Component({
  validations,
})
export default class PaysNaissanceUpdate extends Vue {
  @Inject('paysNaissanceService') private paysNaissanceService: () => PaysNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public paysNaissance: IPaysNaissance = new PaysNaissance();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.paysNaissanceId) {
        vm.retrievePaysNaissance(to.params.paysNaissanceId);
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
    if (this.paysNaissance.id) {
      this.paysNaissanceService()
        .update(this.paysNaissance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.paysNaissance.updated', { param: param.id });
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
      this.paysNaissanceService()
        .create(this.paysNaissance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.paysNaissance.created', { param: param.id });
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

  public retrievePaysNaissance(paysNaissanceId): void {
    this.paysNaissanceService()
      .find(paysNaissanceId)
      .then(res => {
        this.paysNaissance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.etatCivilService()
      .retrieve()
      .then(res => {
        this.etatCivils = res.data;
      });
  }
}

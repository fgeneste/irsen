import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { IDepartementNaissance, DepartementNaissance } from '@/shared/model/departement-naissance.model';
import DepartementNaissanceService from './departement-naissance.service';

const validations: any = {
  departementNaissance: {
    code: {},
    libelle: {},
    avecConseilDepartemental: {},
    article: {},
    codeSirpas: {},
    codeComparaison: {},
    libelleComplet: {},
    libelleAvecArticle: {},
  },
};

@Component({
  validations,
})
export default class DepartementNaissanceUpdate extends Vue {
  @Inject('departementNaissanceService') private departementNaissanceService: () => DepartementNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public departementNaissance: IDepartementNaissance = new DepartementNaissance();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.departementNaissanceId) {
        vm.retrieveDepartementNaissance(to.params.departementNaissanceId);
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
    if (this.departementNaissance.id) {
      this.departementNaissanceService()
        .update(this.departementNaissance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.departementNaissance.updated', { param: param.id });
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
      this.departementNaissanceService()
        .create(this.departementNaissance)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.departementNaissance.created', { param: param.id });
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

  public retrieveDepartementNaissance(departementNaissanceId): void {
    this.departementNaissanceService()
      .find(departementNaissanceId)
      .then(res => {
        this.departementNaissance = res;
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

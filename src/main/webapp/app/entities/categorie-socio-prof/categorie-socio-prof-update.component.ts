import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { ICategorieSocioProf, CategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';
import CategorieSocioProfService from './categorie-socio-prof.service';

const validations: any = {
  categorieSocioProf: {
    code: {},
    libelle: {},
    libelleComplet: {},
  },
};

@Component({
  validations,
})
export default class CategorieSocioProfUpdate extends Vue {
  @Inject('categorieSocioProfService') private categorieSocioProfService: () => CategorieSocioProfService;
  @Inject('alertService') private alertService: () => AlertService;

  public categorieSocioProf: ICategorieSocioProf = new CategorieSocioProf();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categorieSocioProfId) {
        vm.retrieveCategorieSocioProf(to.params.categorieSocioProfId);
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
    if (this.categorieSocioProf.id) {
      this.categorieSocioProfService()
        .update(this.categorieSocioProf)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.categorieSocioProf.updated', { param: param.id });
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
      this.categorieSocioProfService()
        .create(this.categorieSocioProf)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.categorieSocioProf.created', { param: param.id });
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

  public retrieveCategorieSocioProf(categorieSocioProfId): void {
    this.categorieSocioProfService()
      .find(categorieSocioProfId)
      .then(res => {
        this.categorieSocioProf = res;
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

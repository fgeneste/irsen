import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';
import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

import { IFonctionEnCours, FonctionEnCours } from '@/shared/model/fonction-en-cours.model';
import FonctionEnCoursService from './fonction-en-cours.service';

const validations: any = {
  fonctionEnCours: {
    libelle: {},
    dateDebut: {},
    dateFin: {},
  },
};

@Component({
  validations,
})
export default class FonctionEnCoursUpdate extends Vue {
  @Inject('fonctionEnCoursService') private fonctionEnCoursService: () => FonctionEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  public fonctionEnCours: IFonctionEnCours = new FonctionEnCours();

  @Inject('mandatEnCoursService') private mandatEnCoursService: () => MandatEnCoursService;

  public mandatEnCours: IMandatEnCours[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fonctionEnCoursId) {
        vm.retrieveFonctionEnCours(to.params.fonctionEnCoursId);
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
    if (this.fonctionEnCours.id) {
      this.fonctionEnCoursService()
        .update(this.fonctionEnCours)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.fonctionEnCours.updated', { param: param.id });
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
      this.fonctionEnCoursService()
        .create(this.fonctionEnCours)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.fonctionEnCours.created', { param: param.id });
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

  public retrieveFonctionEnCours(fonctionEnCoursId): void {
    this.fonctionEnCoursService()
      .find(fonctionEnCoursId)
      .then(res => {
        this.fonctionEnCours = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.mandatEnCoursService()
      .retrieve()
      .then(res => {
        this.mandatEnCours = res.data;
      });
  }
}

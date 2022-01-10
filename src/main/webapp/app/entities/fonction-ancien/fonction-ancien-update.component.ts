import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
import { IMandatAncien } from '@/shared/model/mandat-ancien.model';

import { IFonctionAncien, FonctionAncien } from '@/shared/model/fonction-ancien.model';
import FonctionAncienService from './fonction-ancien.service';

const validations: any = {
  fonctionAncien: {
    libelle: {},
    dateDebut: {},
    dateFin: {},
  },
};

@Component({
  validations,
})
export default class FonctionAncienUpdate extends Vue {
  @Inject('fonctionAncienService') private fonctionAncienService: () => FonctionAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  public fonctionAncien: IFonctionAncien = new FonctionAncien();

  @Inject('mandatAncienService') private mandatAncienService: () => MandatAncienService;

  public mandatAnciens: IMandatAncien[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fonctionAncienId) {
        vm.retrieveFonctionAncien(to.params.fonctionAncienId);
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
    if (this.fonctionAncien.id) {
      this.fonctionAncienService()
        .update(this.fonctionAncien)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.fonctionAncien.updated', { param: param.id });
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
      this.fonctionAncienService()
        .create(this.fonctionAncien)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.fonctionAncien.created', { param: param.id });
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

  public retrieveFonctionAncien(fonctionAncienId): void {
    this.fonctionAncienService()
      .find(fonctionAncienId)
      .then(res => {
        this.fonctionAncien = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.mandatAncienService()
      .retrieve()
      .then(res => {
        this.mandatAnciens = res.data;
      });
  }
}

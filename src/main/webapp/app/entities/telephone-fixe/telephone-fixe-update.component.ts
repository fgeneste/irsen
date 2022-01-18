import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { ITelephoneFixe, TelephoneFixe } from '@/shared/model/telephone-fixe.model';
import TelephoneFixeService from './telephone-fixe.service';

const validations: any = {
  telephoneFixe: {
    type: {},
    numero: {},
  },
};

@Component({
  validations,
})
export default class TelephoneFixeUpdate extends Vue {
  @Inject('telephoneFixeService') private telephoneFixeService: () => TelephoneFixeService;
  @Inject('alertService') private alertService: () => AlertService;

  public telephoneFixe: ITelephoneFixe = new TelephoneFixe();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephoneFixeId) {
        vm.retrieveTelephoneFixe(to.params.telephoneFixeId);
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
    if (this.telephoneFixe.id) {
      this.telephoneFixeService()
        .update(this.telephoneFixe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephoneFixe.updated', { param: param.id });
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
      this.telephoneFixeService()
        .create(this.telephoneFixe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephoneFixe.created', { param: param.id });
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

  public retrieveTelephoneFixe(telephoneFixeId): void {
    this.telephoneFixeService()
      .find(telephoneFixeId)
      .then(res => {
        this.telephoneFixe = res;
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

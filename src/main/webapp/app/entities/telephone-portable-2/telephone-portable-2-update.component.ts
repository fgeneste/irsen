import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { ITelephonePortable2, TelephonePortable2 } from '@/shared/model/telephone-portable-2.model';
import TelephonePortable2Service from './telephone-portable-2.service';

const validations: any = {
  telephonePortable2: {
    type: {},
    numero: {},
  },
};

@Component({
  validations,
})
export default class TelephonePortable2Update extends Vue {
  @Inject('telephonePortable2Service') private telephonePortable2Service: () => TelephonePortable2Service;
  @Inject('alertService') private alertService: () => AlertService;

  public telephonePortable2: ITelephonePortable2 = new TelephonePortable2();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephonePortable2Id) {
        vm.retrieveTelephonePortable2(to.params.telephonePortable2Id);
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
    if (this.telephonePortable2.id) {
      this.telephonePortable2Service()
        .update(this.telephonePortable2)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephonePortable2.updated', { param: param.id });
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
      this.telephonePortable2Service()
        .create(this.telephonePortable2)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephonePortable2.created', { param: param.id });
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

  public retrieveTelephonePortable2(telephonePortable2Id): void {
    this.telephonePortable2Service()
      .find(telephonePortable2Id)
      .then(res => {
        this.telephonePortable2 = res;
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

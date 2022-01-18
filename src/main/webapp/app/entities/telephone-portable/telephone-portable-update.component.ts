import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import { ITelephonePortable, TelephonePortable } from '@/shared/model/telephone-portable.model';
import TelephonePortableService from './telephone-portable.service';

const validations: any = {
  telephonePortable: {
    type: {},
    numero: {},
  },
};

@Component({
  validations,
})
export default class TelephonePortableUpdate extends Vue {
  @Inject('telephonePortableService') private telephonePortableService: () => TelephonePortableService;
  @Inject('alertService') private alertService: () => AlertService;

  public telephonePortable: ITelephonePortable = new TelephonePortable();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephonePortableId) {
        vm.retrieveTelephonePortable(to.params.telephonePortableId);
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
    if (this.telephonePortable.id) {
      this.telephonePortableService()
        .update(this.telephonePortable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephonePortable.updated', { param: param.id });
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
      this.telephonePortableService()
        .create(this.telephonePortable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.telephonePortable.created', { param: param.id });
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

  public retrieveTelephonePortable(telephonePortableId): void {
    this.telephonePortableService()
      .find(telephonePortableId)
      .then(res => {
        this.telephonePortable = res;
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

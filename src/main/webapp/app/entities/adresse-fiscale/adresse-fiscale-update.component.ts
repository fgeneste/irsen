import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AdressesService from '@/entities/adresses/adresses.service';
import { IAdresses } from '@/shared/model/adresses.model';

import { IAdresseFiscale, AdresseFiscale } from '@/shared/model/adresse-fiscale.model';
import AdresseFiscaleService from './adresse-fiscale.service';

const validations: any = {
  adresseFiscale: {
    numero: {},
    bister: {},
    complement1: {},
    complement2: {},
    typeVoie: {},
    voie: {},
    codePostal: {},
    ville: {},
    pays: {},
    affichageInternet: {},
    affichageIntranet: {},
  },
};

@Component({
  validations,
})
export default class AdresseFiscaleUpdate extends Vue {
  @Inject('adresseFiscaleService') private adresseFiscaleService: () => AdresseFiscaleService;
  @Inject('alertService') private alertService: () => AlertService;

  public adresseFiscale: IAdresseFiscale = new AdresseFiscale();

  @Inject('adressesService') private adressesService: () => AdressesService;

  public adresses: IAdresses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adresseFiscaleId) {
        vm.retrieveAdresseFiscale(to.params.adresseFiscaleId);
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
    if (this.adresseFiscale.id) {
      this.adresseFiscaleService()
        .update(this.adresseFiscale)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adresseFiscale.updated', { param: param.id });
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
      this.adresseFiscaleService()
        .create(this.adresseFiscale)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adresseFiscale.created', { param: param.id });
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

  public retrieveAdresseFiscale(adresseFiscaleId): void {
    this.adresseFiscaleService()
      .find(adresseFiscaleId)
      .then(res => {
        this.adresseFiscale = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.adressesService()
      .retrieve()
      .then(res => {
        this.adresses = res.data;
      });
  }
}

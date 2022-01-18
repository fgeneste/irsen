import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AdressesService from '@/entities/adresses/adresses.service';
import { IAdresses } from '@/shared/model/adresses.model';

import { IAdressePostale, AdressePostale } from '@/shared/model/adresse-postale.model';
import AdressePostaleService from './adresse-postale.service';

const validations: any = {
  adressePostale: {
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
export default class AdressePostaleUpdate extends Vue {
  @Inject('adressePostaleService') private adressePostaleService: () => AdressePostaleService;
  @Inject('alertService') private alertService: () => AlertService;

  public adressePostale: IAdressePostale = new AdressePostale();

  @Inject('adressesService') private adressesService: () => AdressesService;

  public adresses: IAdresses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressePostaleId) {
        vm.retrieveAdressePostale(to.params.adressePostaleId);
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
    if (this.adressePostale.id) {
      this.adressePostaleService()
        .update(this.adressePostale)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adressePostale.updated', { param: param.id });
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
      this.adressePostaleService()
        .create(this.adressePostale)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adressePostale.created', { param: param.id });
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

  public retrieveAdressePostale(adressePostaleId): void {
    this.adressePostaleService()
      .find(adressePostaleId)
      .then(res => {
        this.adressePostale = res;
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

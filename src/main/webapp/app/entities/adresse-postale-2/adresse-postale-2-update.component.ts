import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AdressesService from '@/entities/adresses/adresses.service';
import { IAdresses } from '@/shared/model/adresses.model';

import { IAdressePostale2, AdressePostale2 } from '@/shared/model/adresse-postale-2.model';
import AdressePostale2Service from './adresse-postale-2.service';

const validations: any = {
  adressePostale2: {
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
export default class AdressePostale2Update extends Vue {
  @Inject('adressePostale2Service') private adressePostale2Service: () => AdressePostale2Service;
  @Inject('alertService') private alertService: () => AlertService;

  public adressePostale2: IAdressePostale2 = new AdressePostale2();

  @Inject('adressesService') private adressesService: () => AdressesService;

  public adresses: IAdresses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressePostale2Id) {
        vm.retrieveAdressePostale2(to.params.adressePostale2Id);
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
    if (this.adressePostale2.id) {
      this.adressePostale2Service()
        .update(this.adressePostale2)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adressePostale2.updated', { param: param.id });
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
      this.adressePostale2Service()
        .create(this.adressePostale2)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adressePostale2.created', { param: param.id });
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

  public retrieveAdressePostale2(adressePostale2Id): void {
    this.adressePostale2Service()
      .find(adressePostale2Id)
      .then(res => {
        this.adressePostale2 = res;
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

import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AdresseFiscaleService from '@/entities/adresse-fiscale/adresse-fiscale.service';
import { IAdresseFiscale } from '@/shared/model/adresse-fiscale.model';

import AdressePostaleService from '@/entities/adresse-postale/adresse-postale.service';
import { IAdressePostale } from '@/shared/model/adresse-postale.model';

import AdressePostale2Service from '@/entities/adresse-postale-2/adresse-postale-2.service';
import { IAdressePostale2 } from '@/shared/model/adresse-postale-2.model';

import SenateurService from '@/entities/senateur/senateur.service';
import { ISenateur } from '@/shared/model/senateur.model';

import { IAdresses, Adresses } from '@/shared/model/adresses.model';
import AdressesService from './adresses.service';

const validations: any = {
  adresses: {},
};

@Component({
  validations,
})
export default class AdressesUpdate extends Vue {
  @Inject('adressesService') private adressesService: () => AdressesService;
  @Inject('alertService') private alertService: () => AlertService;

  public adresses: IAdresses = new Adresses();

  @Inject('adresseFiscaleService') private adresseFiscaleService: () => AdresseFiscaleService;

  public adresseFiscales: IAdresseFiscale[] = [];

  @Inject('adressePostaleService') private adressePostaleService: () => AdressePostaleService;

  public adressePostales: IAdressePostale[] = [];

  @Inject('adressePostale2Service') private adressePostale2Service: () => AdressePostale2Service;

  public adressePostale2s: IAdressePostale2[] = [];

  @Inject('senateurService') private senateurService: () => SenateurService;

  public senateurs: ISenateur[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressesId) {
        vm.retrieveAdresses(to.params.adressesId);
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
    if (this.adresses.id) {
      this.adressesService()
        .update(this.adresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adresses.updated', { param: param.id });
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
      this.adressesService()
        .create(this.adresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.adresses.created', { param: param.id });
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

  public retrieveAdresses(adressesId): void {
    this.adressesService()
      .find(adressesId)
      .then(res => {
        this.adresses = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.adresseFiscaleService()
      .retrieve()
      .then(res => {
        this.adresseFiscales = res.data;
      });
    this.adressePostaleService()
      .retrieve()
      .then(res => {
        this.adressePostales = res.data;
      });
    this.adressePostale2Service()
      .retrieve()
      .then(res => {
        this.adressePostale2s = res.data;
      });
    this.senateurService()
      .retrieve()
      .then(res => {
        this.senateurs = res.data;
      });
  }
}

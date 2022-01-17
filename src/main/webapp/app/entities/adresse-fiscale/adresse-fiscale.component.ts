import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdresseFiscale } from '@/shared/model/adresse-fiscale.model';

import AdresseFiscaleService from './adresse-fiscale.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdresseFiscale extends Vue {
  @Inject('adresseFiscaleService') private adresseFiscaleService: () => AdresseFiscaleService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public adresseFiscales: IAdresseFiscale[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdresseFiscales();
  }

  public clear(): void {
    this.retrieveAllAdresseFiscales();
  }

  public retrieveAllAdresseFiscales(): void {
    this.isFetching = true;
    this.adresseFiscaleService()
      .retrieve()
      .then(
        res => {
          this.adresseFiscales = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IAdresseFiscale): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdresseFiscale(): void {
    this.adresseFiscaleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.adresseFiscale.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAdresseFiscales();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}

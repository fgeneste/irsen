import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdressePostale } from '@/shared/model/adresse-postale.model';

import AdressePostaleService from './adresse-postale.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdressePostale extends Vue {
  @Inject('adressePostaleService') private adressePostaleService: () => AdressePostaleService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public adressePostales: IAdressePostale[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdressePostales();
  }

  public clear(): void {
    this.retrieveAllAdressePostales();
  }

  public retrieveAllAdressePostales(): void {
    this.isFetching = true;
    this.adressePostaleService()
      .retrieve()
      .then(
        res => {
          this.adressePostales = res.data;
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

  public prepareRemove(instance: IAdressePostale): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdressePostale(): void {
    this.adressePostaleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.adressePostale.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAdressePostales();
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

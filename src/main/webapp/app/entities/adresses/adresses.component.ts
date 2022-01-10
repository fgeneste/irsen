import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdresses } from '@/shared/model/adresses.model';

import AdressesService from './adresses.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Adresses extends Vue {
  @Inject('adressesService') private adressesService: () => AdressesService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public adresses: IAdresses[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdressess();
  }

  public clear(): void {
    this.retrieveAllAdressess();
  }

  public retrieveAllAdressess(): void {
    this.isFetching = true;
    this.adressesService()
      .retrieve()
      .then(
        res => {
          this.adresses = res.data;
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

  public prepareRemove(instance: IAdresses): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdresses(): void {
    this.adressesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.adresses.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAdressess();
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

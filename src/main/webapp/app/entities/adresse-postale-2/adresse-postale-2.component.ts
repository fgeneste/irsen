import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdressePostale2 } from '@/shared/model/adresse-postale-2.model';

import AdressePostale2Service from './adresse-postale-2.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AdressePostale2 extends Vue {
  @Inject('adressePostale2Service') private adressePostale2Service: () => AdressePostale2Service;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public adressePostale2s: IAdressePostale2[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdressePostale2s();
  }

  public clear(): void {
    this.retrieveAllAdressePostale2s();
  }

  public retrieveAllAdressePostale2s(): void {
    this.isFetching = true;
    this.adressePostale2Service()
      .retrieve()
      .then(
        res => {
          this.adressePostale2s = res.data;
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

  public prepareRemove(instance: IAdressePostale2): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdressePostale2(): void {
    this.adressePostale2Service()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.adressePostale2.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAdressePostale2s();
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

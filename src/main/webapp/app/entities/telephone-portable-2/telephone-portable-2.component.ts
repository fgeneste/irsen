import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITelephonePortable2 } from '@/shared/model/telephone-portable-2.model';

import TelephonePortable2Service from './telephone-portable-2.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TelephonePortable2 extends Vue {
  @Inject('telephonePortable2Service') private telephonePortable2Service: () => TelephonePortable2Service;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public telephonePortable2s: ITelephonePortable2[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTelephonePortable2s();
  }

  public clear(): void {
    this.retrieveAllTelephonePortable2s();
  }

  public retrieveAllTelephonePortable2s(): void {
    this.isFetching = true;
    this.telephonePortable2Service()
      .retrieve()
      .then(
        res => {
          this.telephonePortable2s = res.data;
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

  public prepareRemove(instance: ITelephonePortable2): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTelephonePortable2(): void {
    this.telephonePortable2Service()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.telephonePortable2.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTelephonePortable2s();
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

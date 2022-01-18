import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITelephonePortable } from '@/shared/model/telephone-portable.model';

import TelephonePortableService from './telephone-portable.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TelephonePortable extends Vue {
  @Inject('telephonePortableService') private telephonePortableService: () => TelephonePortableService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public telephonePortables: ITelephonePortable[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTelephonePortables();
  }

  public clear(): void {
    this.retrieveAllTelephonePortables();
  }

  public retrieveAllTelephonePortables(): void {
    this.isFetching = true;
    this.telephonePortableService()
      .retrieve()
      .then(
        res => {
          this.telephonePortables = res.data;
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

  public prepareRemove(instance: ITelephonePortable): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTelephonePortable(): void {
    this.telephonePortableService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.telephonePortable.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTelephonePortables();
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

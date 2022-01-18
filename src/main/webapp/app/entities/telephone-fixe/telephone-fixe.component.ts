import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITelephoneFixe } from '@/shared/model/telephone-fixe.model';

import TelephoneFixeService from './telephone-fixe.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TelephoneFixe extends Vue {
  @Inject('telephoneFixeService') private telephoneFixeService: () => TelephoneFixeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public telephoneFixes: ITelephoneFixe[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTelephoneFixes();
  }

  public clear(): void {
    this.retrieveAllTelephoneFixes();
  }

  public retrieveAllTelephoneFixes(): void {
    this.isFetching = true;
    this.telephoneFixeService()
      .retrieve()
      .then(
        res => {
          this.telephoneFixes = res.data;
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

  public prepareRemove(instance: ITelephoneFixe): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTelephoneFixe(): void {
    this.telephoneFixeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.telephoneFixe.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTelephoneFixes();
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

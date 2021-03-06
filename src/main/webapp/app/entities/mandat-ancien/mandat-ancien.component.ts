import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMandatAncien } from '@/shared/model/mandat-ancien.model';

import MandatAncienService from './mandat-ancien.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MandatAncien extends Vue {
  @Inject('mandatAncienService') private mandatAncienService: () => MandatAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public mandatAnciens: IMandatAncien[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMandatAnciens();
  }

  public clear(): void {
    this.retrieveAllMandatAnciens();
  }

  public retrieveAllMandatAnciens(): void {
    this.isFetching = true;
    this.mandatAncienService()
      .retrieve()
      .then(
        res => {
          this.mandatAnciens = res.data;
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

  public prepareRemove(instance: IMandatAncien): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMandatAncien(): void {
    this.mandatAncienService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.mandatAncien.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMandatAnciens();
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

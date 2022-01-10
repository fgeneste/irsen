import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMandat } from '@/shared/model/mandat.model';

import MandatService from './mandat.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Mandat extends Vue {
  @Inject('mandatService') private mandatService: () => MandatService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public mandats: IMandat[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMandats();
  }

  public clear(): void {
    this.retrieveAllMandats();
  }

  public retrieveAllMandats(): void {
    this.isFetching = true;
    this.mandatService()
      .retrieve()
      .then(
        res => {
          this.mandats = res.data;
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

  public prepareRemove(instance: IMandat): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMandat(): void {
    this.mandatService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.mandat.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMandats();
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

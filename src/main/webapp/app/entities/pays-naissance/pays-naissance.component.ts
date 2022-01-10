import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPaysNaissance } from '@/shared/model/pays-naissance.model';

import PaysNaissanceService from './pays-naissance.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PaysNaissance extends Vue {
  @Inject('paysNaissanceService') private paysNaissanceService: () => PaysNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public paysNaissances: IPaysNaissance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPaysNaissances();
  }

  public clear(): void {
    this.retrieveAllPaysNaissances();
  }

  public retrieveAllPaysNaissances(): void {
    this.isFetching = true;
    this.paysNaissanceService()
      .retrieve()
      .then(
        res => {
          this.paysNaissances = res.data;
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

  public prepareRemove(instance: IPaysNaissance): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePaysNaissance(): void {
    this.paysNaissanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.paysNaissance.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPaysNaissances();
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

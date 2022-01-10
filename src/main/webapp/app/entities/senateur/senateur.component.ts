import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISenateur } from '@/shared/model/senateur.model';

import SenateurService from './senateur.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Senateur extends Vue {
  @Inject('senateurService') private senateurService: () => SenateurService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public senateurs: ISenateur[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSenateurs();
  }

  public clear(): void {
    this.retrieveAllSenateurs();
  }

  public retrieveAllSenateurs(): void {
    this.isFetching = true;
    this.senateurService()
      .retrieve()
      .then(
        res => {
          this.senateurs = res.data;
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

  public prepareRemove(instance: ISenateur): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSenateur(): void {
    this.senateurService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.senateur.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSenateurs();
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

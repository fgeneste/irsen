import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFonctionAncien } from '@/shared/model/fonction-ancien.model';

import FonctionAncienService from './fonction-ancien.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class FonctionAncien extends Vue {
  @Inject('fonctionAncienService') private fonctionAncienService: () => FonctionAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public fonctionAnciens: IFonctionAncien[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFonctionAnciens();
  }

  public clear(): void {
    this.retrieveAllFonctionAnciens();
  }

  public retrieveAllFonctionAnciens(): void {
    this.isFetching = true;
    this.fonctionAncienService()
      .retrieve()
      .then(
        res => {
          this.fonctionAnciens = res.data;
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

  public prepareRemove(instance: IFonctionAncien): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFonctionAncien(): void {
    this.fonctionAncienService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.fonctionAncien.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFonctionAnciens();
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

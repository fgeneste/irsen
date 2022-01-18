import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDecoration } from '@/shared/model/decoration.model';

import DecorationService from './decoration.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Decoration extends Vue {
  @Inject('decorationService') private decorationService: () => DecorationService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public decorations: IDecoration[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDecorations();
  }

  public clear(): void {
    this.retrieveAllDecorations();
  }

  public retrieveAllDecorations(): void {
    this.isFetching = true;
    this.decorationService()
      .retrieve()
      .then(
        res => {
          this.decorations = res.data;
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

  public prepareRemove(instance: IDecoration): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDecoration(): void {
    this.decorationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.decoration.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDecorations();
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

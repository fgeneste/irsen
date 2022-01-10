import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

import MandatEnCoursService from './mandat-en-cours.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MandatEnCours extends Vue {
  @Inject('mandatEnCoursService') private mandatEnCoursService: () => MandatEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public mandatEnCours: IMandatEnCours[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMandatEnCourss();
  }

  public clear(): void {
    this.retrieveAllMandatEnCourss();
  }

  public retrieveAllMandatEnCourss(): void {
    this.isFetching = true;
    this.mandatEnCoursService()
      .retrieve()
      .then(
        res => {
          this.mandatEnCours = res.data;
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

  public prepareRemove(instance: IMandatEnCours): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMandatEnCours(): void {
    this.mandatEnCoursService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.mandatEnCours.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMandatEnCourss();
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

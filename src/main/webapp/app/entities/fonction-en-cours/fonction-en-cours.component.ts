import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFonctionEnCours } from '@/shared/model/fonction-en-cours.model';

import FonctionEnCoursService from './fonction-en-cours.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class FonctionEnCours extends Vue {
  @Inject('fonctionEnCoursService') private fonctionEnCoursService: () => FonctionEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public fonctionEnCours: IFonctionEnCours[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFonctionEnCourss();
  }

  public clear(): void {
    this.retrieveAllFonctionEnCourss();
  }

  public retrieveAllFonctionEnCourss(): void {
    this.isFetching = true;
    this.fonctionEnCoursService()
      .retrieve()
      .then(
        res => {
          this.fonctionEnCours = res.data;
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

  public prepareRemove(instance: IFonctionEnCours): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFonctionEnCours(): void {
    this.fonctionEnCoursService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.fonctionEnCours.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFonctionEnCourss();
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

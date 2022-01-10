import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';

import CategorieSocioProfService from './categorie-socio-prof.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CategorieSocioProf extends Vue {
  @Inject('categorieSocioProfService') private categorieSocioProfService: () => CategorieSocioProfService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public categorieSocioProfs: ICategorieSocioProf[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCategorieSocioProfs();
  }

  public clear(): void {
    this.retrieveAllCategorieSocioProfs();
  }

  public retrieveAllCategorieSocioProfs(): void {
    this.isFetching = true;
    this.categorieSocioProfService()
      .retrieve()
      .then(
        res => {
          this.categorieSocioProfs = res.data;
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

  public prepareRemove(instance: ICategorieSocioProf): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCategorieSocioProf(): void {
    this.categorieSocioProfService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.categorieSocioProf.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCategorieSocioProfs();
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

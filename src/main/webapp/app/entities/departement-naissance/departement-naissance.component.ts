import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDepartementNaissance } from '@/shared/model/departement-naissance.model';

import DepartementNaissanceService from './departement-naissance.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DepartementNaissance extends Vue {
  @Inject('departementNaissanceService') private departementNaissanceService: () => DepartementNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public departementNaissances: IDepartementNaissance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDepartementNaissances();
  }

  public clear(): void {
    this.retrieveAllDepartementNaissances();
  }

  public retrieveAllDepartementNaissances(): void {
    this.isFetching = true;
    this.departementNaissanceService()
      .retrieve()
      .then(
        res => {
          this.departementNaissances = res.data;
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

  public prepareRemove(instance: IDepartementNaissance): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDepartementNaissance(): void {
    this.departementNaissanceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.departementNaissance.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDepartementNaissances();
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

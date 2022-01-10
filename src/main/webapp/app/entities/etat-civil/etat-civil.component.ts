import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import EtatCivilService from './etat-civil.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class EtatCivil extends Vue {
  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public etatCivils: IEtatCivil[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEtatCivils();
  }

  public clear(): void {
    this.retrieveAllEtatCivils();
  }

  public retrieveAllEtatCivils(): void {
    this.isFetching = true;
    this.etatCivilService()
      .retrieve()
      .then(
        res => {
          this.etatCivils = res.data;
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

  public prepareRemove(instance: IEtatCivil): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEtatCivil(): void {
    this.etatCivilService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('irsenApp.etatCivil.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEtatCivils();
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

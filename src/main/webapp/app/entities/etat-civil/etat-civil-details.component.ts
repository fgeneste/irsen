import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEtatCivil } from '@/shared/model/etat-civil.model';
import EtatCivilService from './etat-civil.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EtatCivilDetails extends Vue {
  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;
  @Inject('alertService') private alertService: () => AlertService;

  public etatCivil: IEtatCivil = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.etatCivilId) {
        vm.retrieveEtatCivil(to.params.etatCivilId);
      }
    });
  }

  public retrieveEtatCivil(etatCivilId) {
    this.etatCivilService()
      .find(etatCivilId)
      .then(res => {
        this.etatCivil = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPaysNaissance } from '@/shared/model/pays-naissance.model';
import PaysNaissanceService from './pays-naissance.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PaysNaissanceDetails extends Vue {
  @Inject('paysNaissanceService') private paysNaissanceService: () => PaysNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public paysNaissance: IPaysNaissance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.paysNaissanceId) {
        vm.retrievePaysNaissance(to.params.paysNaissanceId);
      }
    });
  }

  public retrievePaysNaissance(paysNaissanceId) {
    this.paysNaissanceService()
      .find(paysNaissanceId)
      .then(res => {
        this.paysNaissance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

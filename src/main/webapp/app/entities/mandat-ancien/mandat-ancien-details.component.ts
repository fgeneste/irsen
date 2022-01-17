import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMandatAncien } from '@/shared/model/mandat-ancien.model';
import MandatAncienService from './mandat-ancien.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MandatAncienDetails extends Vue {
  @Inject('mandatAncienService') private mandatAncienService: () => MandatAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandatAncien: IMandatAncien = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatAncienId) {
        vm.retrieveMandatAncien(to.params.mandatAncienId);
      }
    });
  }

  public retrieveMandatAncien(mandatAncienId) {
    this.mandatAncienService()
      .find(mandatAncienId)
      .then(res => {
        this.mandatAncien = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

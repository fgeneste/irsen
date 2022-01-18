import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITelephoneFixe } from '@/shared/model/telephone-fixe.model';
import TelephoneFixeService from './telephone-fixe.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TelephoneFixeDetails extends Vue {
  @Inject('telephoneFixeService') private telephoneFixeService: () => TelephoneFixeService;
  @Inject('alertService') private alertService: () => AlertService;

  public telephoneFixe: ITelephoneFixe = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephoneFixeId) {
        vm.retrieveTelephoneFixe(to.params.telephoneFixeId);
      }
    });
  }

  public retrieveTelephoneFixe(telephoneFixeId) {
    this.telephoneFixeService()
      .find(telephoneFixeId)
      .then(res => {
        this.telephoneFixe = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

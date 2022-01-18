import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITelephonePortable2 } from '@/shared/model/telephone-portable-2.model';
import TelephonePortable2Service from './telephone-portable-2.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TelephonePortable2Details extends Vue {
  @Inject('telephonePortable2Service') private telephonePortable2Service: () => TelephonePortable2Service;
  @Inject('alertService') private alertService: () => AlertService;

  public telephonePortable2: ITelephonePortable2 = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephonePortable2Id) {
        vm.retrieveTelephonePortable2(to.params.telephonePortable2Id);
      }
    });
  }

  public retrieveTelephonePortable2(telephonePortable2Id) {
    this.telephonePortable2Service()
      .find(telephonePortable2Id)
      .then(res => {
        this.telephonePortable2 = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

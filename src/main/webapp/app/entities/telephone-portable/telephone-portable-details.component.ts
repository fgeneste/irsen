import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITelephonePortable } from '@/shared/model/telephone-portable.model';
import TelephonePortableService from './telephone-portable.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TelephonePortableDetails extends Vue {
  @Inject('telephonePortableService') private telephonePortableService: () => TelephonePortableService;
  @Inject('alertService') private alertService: () => AlertService;

  public telephonePortable: ITelephonePortable = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.telephonePortableId) {
        vm.retrieveTelephonePortable(to.params.telephonePortableId);
      }
    });
  }

  public retrieveTelephonePortable(telephonePortableId) {
    this.telephonePortableService()
      .find(telephonePortableId)
      .then(res => {
        this.telephonePortable = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

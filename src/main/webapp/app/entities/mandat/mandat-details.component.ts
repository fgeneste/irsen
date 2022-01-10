import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMandat } from '@/shared/model/mandat.model';
import MandatService from './mandat.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MandatDetails extends Vue {
  @Inject('mandatService') private mandatService: () => MandatService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandat: IMandat = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatId) {
        vm.retrieveMandat(to.params.mandatId);
      }
    });
  }

  public retrieveMandat(mandatId) {
    this.mandatService()
      .find(mandatId)
      .then(res => {
        this.mandat = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

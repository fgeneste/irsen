import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';
import MandatEnCoursService from './mandat-en-cours.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MandatEnCoursDetails extends Vue {
  @Inject('mandatEnCoursService') private mandatEnCoursService: () => MandatEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandatEnCours: IMandatEnCours = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatEnCoursId) {
        vm.retrieveMandatEnCours(to.params.mandatEnCoursId);
      }
    });
  }

  public retrieveMandatEnCours(mandatEnCoursId) {
    this.mandatEnCoursService()
      .find(mandatEnCoursId)
      .then(res => {
        this.mandatEnCours = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

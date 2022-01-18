import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDecoration } from '@/shared/model/decoration.model';
import DecorationService from './decoration.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DecorationDetails extends Vue {
  @Inject('decorationService') private decorationService: () => DecorationService;
  @Inject('alertService') private alertService: () => AlertService;

  public decoration: IDecoration = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.decorationId) {
        vm.retrieveDecoration(to.params.decorationId);
      }
    });
  }

  public retrieveDecoration(decorationId) {
    this.decorationService()
      .find(decorationId)
      .then(res => {
        this.decoration = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

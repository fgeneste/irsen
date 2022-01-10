import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISenateur } from '@/shared/model/senateur.model';
import SenateurService from './senateur.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SenateurDetails extends Vue {
  @Inject('senateurService') private senateurService: () => SenateurService;
  @Inject('alertService') private alertService: () => AlertService;

  public senateur: ISenateur = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.senateurId) {
        vm.retrieveSenateur(to.params.senateurId);
      }
    });
  }

  public retrieveSenateur(senateurId) {
    this.senateurService()
      .find(senateurId)
      .then(res => {
        this.senateur = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

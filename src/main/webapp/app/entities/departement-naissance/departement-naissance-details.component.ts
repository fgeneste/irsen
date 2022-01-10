import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDepartementNaissance } from '@/shared/model/departement-naissance.model';
import DepartementNaissanceService from './departement-naissance.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DepartementNaissanceDetails extends Vue {
  @Inject('departementNaissanceService') private departementNaissanceService: () => DepartementNaissanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public departementNaissance: IDepartementNaissance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.departementNaissanceId) {
        vm.retrieveDepartementNaissance(to.params.departementNaissanceId);
      }
    });
  }

  public retrieveDepartementNaissance(departementNaissanceId) {
    this.departementNaissanceService()
      .find(departementNaissanceId)
      .then(res => {
        this.departementNaissance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

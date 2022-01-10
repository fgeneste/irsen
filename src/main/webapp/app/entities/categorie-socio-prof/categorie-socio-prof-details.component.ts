import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';
import CategorieSocioProfService from './categorie-socio-prof.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CategorieSocioProfDetails extends Vue {
  @Inject('categorieSocioProfService') private categorieSocioProfService: () => CategorieSocioProfService;
  @Inject('alertService') private alertService: () => AlertService;

  public categorieSocioProf: ICategorieSocioProf = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categorieSocioProfId) {
        vm.retrieveCategorieSocioProf(to.params.categorieSocioProfId);
      }
    });
  }

  public retrieveCategorieSocioProf(categorieSocioProfId) {
    this.categorieSocioProfService()
      .find(categorieSocioProfId)
      .then(res => {
        this.categorieSocioProf = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

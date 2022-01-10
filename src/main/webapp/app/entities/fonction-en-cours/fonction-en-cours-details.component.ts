import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFonctionEnCours } from '@/shared/model/fonction-en-cours.model';
import FonctionEnCoursService from './fonction-en-cours.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FonctionEnCoursDetails extends Vue {
  @Inject('fonctionEnCoursService') private fonctionEnCoursService: () => FonctionEnCoursService;
  @Inject('alertService') private alertService: () => AlertService;

  public fonctionEnCours: IFonctionEnCours = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fonctionEnCoursId) {
        vm.retrieveFonctionEnCours(to.params.fonctionEnCoursId);
      }
    });
  }

  public retrieveFonctionEnCours(fonctionEnCoursId) {
    this.fonctionEnCoursService()
      .find(fonctionEnCoursId)
      .then(res => {
        this.fonctionEnCours = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

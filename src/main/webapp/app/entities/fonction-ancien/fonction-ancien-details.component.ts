import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFonctionAncien } from '@/shared/model/fonction-ancien.model';
import FonctionAncienService from './fonction-ancien.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FonctionAncienDetails extends Vue {
  @Inject('fonctionAncienService') private fonctionAncienService: () => FonctionAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  public fonctionAncien: IFonctionAncien = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fonctionAncienId) {
        vm.retrieveFonctionAncien(to.params.fonctionAncienId);
      }
    });
  }

  public retrieveFonctionAncien(fonctionAncienId) {
    this.fonctionAncienService()
      .find(fonctionAncienId)
      .then(res => {
        this.fonctionAncien = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

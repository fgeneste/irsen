import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdressePostale2 } from '@/shared/model/adresse-postale-2.model';
import AdressePostale2Service from './adresse-postale-2.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AdressePostale2Details extends Vue {
  @Inject('adressePostale2Service') private adressePostale2Service: () => AdressePostale2Service;
  @Inject('alertService') private alertService: () => AlertService;

  public adressePostale2: IAdressePostale2 = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressePostale2Id) {
        vm.retrieveAdressePostale2(to.params.adressePostale2Id);
      }
    });
  }

  public retrieveAdressePostale2(adressePostale2Id) {
    this.adressePostale2Service()
      .find(adressePostale2Id)
      .then(res => {
        this.adressePostale2 = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

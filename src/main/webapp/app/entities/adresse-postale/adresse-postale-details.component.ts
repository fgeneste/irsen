import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdressePostale } from '@/shared/model/adresse-postale.model';
import AdressePostaleService from './adresse-postale.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AdressePostaleDetails extends Vue {
  @Inject('adressePostaleService') private adressePostaleService: () => AdressePostaleService;
  @Inject('alertService') private alertService: () => AlertService;

  public adressePostale: IAdressePostale = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressePostaleId) {
        vm.retrieveAdressePostale(to.params.adressePostaleId);
      }
    });
  }

  public retrieveAdressePostale(adressePostaleId) {
    this.adressePostaleService()
      .find(adressePostaleId)
      .then(res => {
        this.adressePostale = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

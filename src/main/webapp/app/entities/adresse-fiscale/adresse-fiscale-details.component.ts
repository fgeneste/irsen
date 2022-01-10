import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdresseFiscale } from '@/shared/model/adresse-fiscale.model';
import AdresseFiscaleService from './adresse-fiscale.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AdresseFiscaleDetails extends Vue {
  @Inject('adresseFiscaleService') private adresseFiscaleService: () => AdresseFiscaleService;
  @Inject('alertService') private alertService: () => AlertService;

  public adresseFiscale: IAdresseFiscale = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adresseFiscaleId) {
        vm.retrieveAdresseFiscale(to.params.adresseFiscaleId);
      }
    });
  }

  public retrieveAdresseFiscale(adresseFiscaleId) {
    this.adresseFiscaleService()
      .find(adresseFiscaleId)
      .then(res => {
        this.adresseFiscale = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

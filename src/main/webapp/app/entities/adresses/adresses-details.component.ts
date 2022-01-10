import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdresses } from '@/shared/model/adresses.model';
import AdressesService from './adresses.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AdressesDetails extends Vue {
  @Inject('adressesService') private adressesService: () => AdressesService;
  @Inject('alertService') private alertService: () => AlertService;

  public adresses: IAdresses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adressesId) {
        vm.retrieveAdresses(to.params.adressesId);
      }
    });
  }

  public retrieveAdresses(adressesId) {
    this.adressesService()
      .find(adressesId)
      .then(res => {
        this.adresses = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

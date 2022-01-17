import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import SenateurService from '@/entities/senateur/senateur.service';
import { ISenateur } from '@/shared/model/senateur.model';

import MandatAncienService from '@/entities/mandat-ancien/mandat-ancien.service';
import { IMandatAncien } from '@/shared/model/mandat-ancien.model';

import MandatEnCoursService from '@/entities/mandat-en-cours/mandat-en-cours.service';
import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

import { IMandat, Mandat } from '@/shared/model/mandat.model';
import MandatService from './mandat.service';

const validations: any = {
  mandat: {},
};

@Component({
  validations,
})
export default class MandatUpdate extends Vue {
  @Inject('mandatService') private mandatService: () => MandatService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandat: IMandat = new Mandat();

  @Inject('senateurService') private senateurService: () => SenateurService;

  public senateurs: ISenateur[] = [];

  @Inject('mandatAncienService') private mandatAncienService: () => MandatAncienService;

  public mandatAnciens: IMandatAncien[] = [];

  @Inject('mandatEnCoursService') private mandatEnCoursService: () => MandatEnCoursService;

  public mandatEnCours: IMandatEnCours[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatId) {
        vm.retrieveMandat(to.params.mandatId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.mandat.id) {
      this.mandatService()
        .update(this.mandat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandat.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.mandatService()
        .create(this.mandat)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandat.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveMandat(mandatId): void {
    this.mandatService()
      .find(mandatId)
      .then(res => {
        this.mandat = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.senateurService()
      .retrieve()
      .then(res => {
        this.senateurs = res.data;
      });
    this.mandatAncienService()
      .retrieve()
      .then(res => {
        this.mandatAnciens = res.data;
      });
    this.mandatEnCoursService()
      .retrieve()
      .then(res => {
        this.mandatEnCours = res.data;
      });
  }
}

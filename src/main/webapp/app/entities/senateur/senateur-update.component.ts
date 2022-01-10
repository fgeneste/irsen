import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EtatCivilService from '@/entities/etat-civil/etat-civil.service';
import { IEtatCivil } from '@/shared/model/etat-civil.model';

import AdressesService from '@/entities/adresses/adresses.service';
import { IAdresses } from '@/shared/model/adresses.model';

import MandatService from '@/entities/mandat/mandat.service';
import { IMandat } from '@/shared/model/mandat.model';

import { ISenateur, Senateur } from '@/shared/model/senateur.model';
import SenateurService from './senateur.service';

const validations: any = {
  senateur: {},
};

@Component({
  validations,
})
export default class SenateurUpdate extends Vue {
  @Inject('senateurService') private senateurService: () => SenateurService;
  @Inject('alertService') private alertService: () => AlertService;

  public senateur: ISenateur = new Senateur();

  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;

  public etatCivils: IEtatCivil[] = [];

  @Inject('adressesService') private adressesService: () => AdressesService;

  public adresses: IAdresses[] = [];

  @Inject('mandatService') private mandatService: () => MandatService;

  public mandats: IMandat[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.senateurId) {
        vm.retrieveSenateur(to.params.senateurId);
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
    if (this.senateur.id) {
      this.senateurService()
        .update(this.senateur)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.senateur.updated', { param: param.id });
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
      this.senateurService()
        .create(this.senateur)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.senateur.created', { param: param.id });
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

  public retrieveSenateur(senateurId): void {
    this.senateurService()
      .find(senateurId)
      .then(res => {
        this.senateur = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.etatCivilService()
      .retrieve()
      .then(res => {
        this.etatCivils = res.data;
      });
    this.adressesService()
      .retrieve()
      .then(res => {
        this.adresses = res.data;
      });
    this.mandatService()
      .retrieve()
      .then(res => {
        this.mandats = res.data;
      });
  }
}

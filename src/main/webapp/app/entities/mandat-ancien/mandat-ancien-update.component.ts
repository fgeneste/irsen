import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import FonctionAncienService from '@/entities/fonction-ancien/fonction-ancien.service';
import { IFonctionAncien } from '@/shared/model/fonction-ancien.model';

import MandatService from '@/entities/mandat/mandat.service';
import { IMandat } from '@/shared/model/mandat.model';

import { IMandatAncien, MandatAncien } from '@/shared/model/mandat-ancien.model';
import MandatAncienService from './mandat-ancien.service';

const validations: any = {
  mandatAncien: {
    idType: {},
    libelle: {},
    dateDebut: {},
    dateFin: {},
    circonscription: {},
    libelleAffichage: {},
  },
};

@Component({
  validations,
})
export default class MandatAncienUpdate extends Vue {
  @Inject('mandatAncienService') private mandatAncienService: () => MandatAncienService;
  @Inject('alertService') private alertService: () => AlertService;

  public mandatAncien: IMandatAncien = new MandatAncien();

  @Inject('fonctionAncienService') private fonctionAncienService: () => FonctionAncienService;

  public fonctionAnciens: IFonctionAncien[] = [];

  @Inject('mandatService') private mandatService: () => MandatService;

  public mandats: IMandat[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mandatAncienId) {
        vm.retrieveMandatAncien(to.params.mandatAncienId);
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
    if (this.mandatAncien.id) {
      this.mandatAncienService()
        .update(this.mandatAncien)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandatAncien.updated', { param: param.id });
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
      this.mandatAncienService()
        .create(this.mandatAncien)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.mandatAncien.created', { param: param.id });
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

  public retrieveMandatAncien(mandatAncienId): void {
    this.mandatAncienService()
      .find(mandatAncienId)
      .then(res => {
        this.mandatAncien = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.fonctionAncienService()
      .retrieve()
      .then(res => {
        this.fonctionAnciens = res.data;
      });
    this.mandatService()
      .retrieve()
      .then(res => {
        this.mandats = res.data;
      });
  }
}

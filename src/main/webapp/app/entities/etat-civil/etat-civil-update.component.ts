import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import DepartementNaissanceService from '@/entities/departement-naissance/departement-naissance.service';
import { IDepartementNaissance } from '@/shared/model/departement-naissance.model';

import PaysNaissanceService from '@/entities/pays-naissance/pays-naissance.service';
import { IPaysNaissance } from '@/shared/model/pays-naissance.model';

import CategorieSocioProfService from '@/entities/categorie-socio-prof/categorie-socio-prof.service';
import { ICategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';

import TelephonePortableService from '@/entities/telephone-portable/telephone-portable.service';
import { ITelephonePortable } from '@/shared/model/telephone-portable.model';

import TelephonePortable2Service from '@/entities/telephone-portable-2/telephone-portable-2.service';
import { ITelephonePortable2 } from '@/shared/model/telephone-portable-2.model';

import TelephoneFixeService from '@/entities/telephone-fixe/telephone-fixe.service';
import { ITelephoneFixe } from '@/shared/model/telephone-fixe.model';

import SenateurService from '@/entities/senateur/senateur.service';
import { ISenateur } from '@/shared/model/senateur.model';

import { IEtatCivil, EtatCivil } from '@/shared/model/etat-civil.model';
import EtatCivilService from './etat-civil.service';

const validations: any = {
  etatCivil: {
    matricule: {},
    civilite: {},
    titre: {},
    nomFamille: {},
    nomMarital: {},
    nomUsuel: {},
    prenoms: {},
    prenomUsuel: {},
    dateNaissance: {},
    communeNaissance: {},
    profession: {},
    courriel: {},
    courriel2: {},
  },
};

@Component({
  validations,
})
export default class EtatCivilUpdate extends Vue {
  @Inject('etatCivilService') private etatCivilService: () => EtatCivilService;
  @Inject('alertService') private alertService: () => AlertService;

  public etatCivil: IEtatCivil = new EtatCivil();

  @Inject('departementNaissanceService') private departementNaissanceService: () => DepartementNaissanceService;

  public departementNaissances: IDepartementNaissance[] = [];

  @Inject('paysNaissanceService') private paysNaissanceService: () => PaysNaissanceService;

  public paysNaissances: IPaysNaissance[] = [];

  @Inject('categorieSocioProfService') private categorieSocioProfService: () => CategorieSocioProfService;

  public categorieSocioProfs: ICategorieSocioProf[] = [];

  @Inject('telephonePortableService') private telephonePortableService: () => TelephonePortableService;

  public telephonePortables: ITelephonePortable[] = [];

  @Inject('telephonePortable2Service') private telephonePortable2Service: () => TelephonePortable2Service;

  public telephonePortable2s: ITelephonePortable2[] = [];

  @Inject('telephoneFixeService') private telephoneFixeService: () => TelephoneFixeService;

  public telephoneFixes: ITelephoneFixe[] = [];

  @Inject('senateurService') private senateurService: () => SenateurService;

  public senateurs: ISenateur[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.etatCivilId) {
        vm.retrieveEtatCivil(to.params.etatCivilId);
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
    if (this.etatCivil.id) {
      this.etatCivilService()
        .update(this.etatCivil)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.etatCivil.updated', { param: param.id });
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
      this.etatCivilService()
        .create(this.etatCivil)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.etatCivil.created', { param: param.id });
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

  public retrieveEtatCivil(etatCivilId): void {
    this.etatCivilService()
      .find(etatCivilId)
      .then(res => {
        this.etatCivil = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.departementNaissanceService()
      .retrieve()
      .then(res => {
        this.departementNaissances = res.data;
      });
    this.paysNaissanceService()
      .retrieve()
      .then(res => {
        this.paysNaissances = res.data;
      });
    this.categorieSocioProfService()
      .retrieve()
      .then(res => {
        this.categorieSocioProfs = res.data;
      });
    this.telephonePortableService()
      .retrieve()
      .then(res => {
        this.telephonePortables = res.data;
      });
    this.telephonePortable2Service()
      .retrieve()
      .then(res => {
        this.telephonePortable2s = res.data;
      });
    this.telephoneFixeService()
      .retrieve()
      .then(res => {
        this.telephoneFixes = res.data;
      });
    this.senateurService()
      .retrieve()
      .then(res => {
        this.senateurs = res.data;
      });
  }
}

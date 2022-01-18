import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import SenateurService from '@/entities/senateur/senateur.service';
import { ISenateur } from '@/shared/model/senateur.model';

import { IDecoration, Decoration } from '@/shared/model/decoration.model';
import DecorationService from './decoration.service';

const validations: any = {
  decoration: {
    type: {},
    grade: {},
  },
};

@Component({
  validations,
})
export default class DecorationUpdate extends Vue {
  @Inject('decorationService') private decorationService: () => DecorationService;
  @Inject('alertService') private alertService: () => AlertService;

  public decoration: IDecoration = new Decoration();

  @Inject('senateurService') private senateurService: () => SenateurService;

  public senateurs: ISenateur[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.decorationId) {
        vm.retrieveDecoration(to.params.decorationId);
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
    if (this.decoration.id) {
      this.decorationService()
        .update(this.decoration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.decoration.updated', { param: param.id });
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
      this.decorationService()
        .create(this.decoration)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('irsenApp.decoration.created', { param: param.id });
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

  public retrieveDecoration(decorationId): void {
    this.decorationService()
      .find(decorationId)
      .then(res => {
        this.decoration = res;
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
  }
}

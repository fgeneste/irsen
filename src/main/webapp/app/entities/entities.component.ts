import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import SenateurService from './senateur/senateur.service';
import DepartementNaissanceService from './departement-naissance/departement-naissance.service';
import PaysNaissanceService from './pays-naissance/pays-naissance.service';
import CategorieSocioProfService from './categorie-socio-prof/categorie-socio-prof.service';
import EtatCivilService from './etat-civil/etat-civil.service';
import AdresseFiscaleService from './adresse-fiscale/adresse-fiscale.service';
import AdressePostaleService from './adresse-postale/adresse-postale.service';
import AdressePostale2Service from './adresse-postale-2/adresse-postale-2.service';
import AdressesService from './adresses/adresses.service';
import FonctionAncienService from './fonction-ancien/fonction-ancien.service';
import FonctionEnCoursService from './fonction-en-cours/fonction-en-cours.service';
import MandatService from './mandat/mandat.service';
import MandatAncienService from './mandat-ancien/mandat-ancien.service';
import MandatEnCoursService from './mandat-en-cours/mandat-en-cours.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('senateurService') private senateurService = () => new SenateurService();
  @Provide('departementNaissanceService') private departementNaissanceService = () => new DepartementNaissanceService();
  @Provide('paysNaissanceService') private paysNaissanceService = () => new PaysNaissanceService();
  @Provide('categorieSocioProfService') private categorieSocioProfService = () => new CategorieSocioProfService();
  @Provide('etatCivilService') private etatCivilService = () => new EtatCivilService();
  @Provide('adresseFiscaleService') private adresseFiscaleService = () => new AdresseFiscaleService();
  @Provide('adressePostaleService') private adressePostaleService = () => new AdressePostaleService();
  @Provide('adressePostale2Service') private adressePostale2Service = () => new AdressePostale2Service();
  @Provide('adressesService') private adressesService = () => new AdressesService();
  @Provide('fonctionAncienService') private fonctionAncienService = () => new FonctionAncienService();
  @Provide('fonctionEnCoursService') private fonctionEnCoursService = () => new FonctionEnCoursService();
  @Provide('mandatService') private mandatService = () => new MandatService();
  @Provide('mandatAncienService') private mandatAncienService = () => new MandatAncienService();
  @Provide('mandatEnCoursService') private mandatEnCoursService = () => new MandatEnCoursService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}

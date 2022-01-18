import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Senateur = () => import('@/entities/senateur/senateur.vue');
// prettier-ignore
const SenateurUpdate = () => import('@/entities/senateur/senateur-update.vue');
// prettier-ignore
const SenateurDetails = () => import('@/entities/senateur/senateur-details.vue');
// prettier-ignore
const DepartementNaissance = () => import('@/entities/departement-naissance/departement-naissance.vue');
// prettier-ignore
const DepartementNaissanceUpdate = () => import('@/entities/departement-naissance/departement-naissance-update.vue');
// prettier-ignore
const DepartementNaissanceDetails = () => import('@/entities/departement-naissance/departement-naissance-details.vue');
// prettier-ignore
const PaysNaissance = () => import('@/entities/pays-naissance/pays-naissance.vue');
// prettier-ignore
const PaysNaissanceUpdate = () => import('@/entities/pays-naissance/pays-naissance-update.vue');
// prettier-ignore
const PaysNaissanceDetails = () => import('@/entities/pays-naissance/pays-naissance-details.vue');
// prettier-ignore
const CategorieSocioProf = () => import('@/entities/categorie-socio-prof/categorie-socio-prof.vue');
// prettier-ignore
const CategorieSocioProfUpdate = () => import('@/entities/categorie-socio-prof/categorie-socio-prof-update.vue');
// prettier-ignore
const CategorieSocioProfDetails = () => import('@/entities/categorie-socio-prof/categorie-socio-prof-details.vue');
// prettier-ignore
const EtatCivil = () => import('@/entities/etat-civil/etat-civil.vue');
// prettier-ignore
const EtatCivilUpdate = () => import('@/entities/etat-civil/etat-civil-update.vue');
// prettier-ignore
const EtatCivilDetails = () => import('@/entities/etat-civil/etat-civil-details.vue');
// prettier-ignore
const AdresseFiscale = () => import('@/entities/adresse-fiscale/adresse-fiscale.vue');
// prettier-ignore
const AdresseFiscaleUpdate = () => import('@/entities/adresse-fiscale/adresse-fiscale-update.vue');
// prettier-ignore
const AdresseFiscaleDetails = () => import('@/entities/adresse-fiscale/adresse-fiscale-details.vue');
// prettier-ignore
const AdressePostale = () => import('@/entities/adresse-postale/adresse-postale.vue');
// prettier-ignore
const AdressePostaleUpdate = () => import('@/entities/adresse-postale/adresse-postale-update.vue');
// prettier-ignore
const AdressePostaleDetails = () => import('@/entities/adresse-postale/adresse-postale-details.vue');
// prettier-ignore
const AdressePostale2 = () => import('@/entities/adresse-postale-2/adresse-postale-2.vue');
// prettier-ignore
const AdressePostale2Update = () => import('@/entities/adresse-postale-2/adresse-postale-2-update.vue');
// prettier-ignore
const AdressePostale2Details = () => import('@/entities/adresse-postale-2/adresse-postale-2-details.vue');
// prettier-ignore
const Adresses = () => import('@/entities/adresses/adresses.vue');
// prettier-ignore
const AdressesUpdate = () => import('@/entities/adresses/adresses-update.vue');
// prettier-ignore
const AdressesDetails = () => import('@/entities/adresses/adresses-details.vue');
// prettier-ignore
const FonctionAncien = () => import('@/entities/fonction-ancien/fonction-ancien.vue');
// prettier-ignore
const FonctionAncienUpdate = () => import('@/entities/fonction-ancien/fonction-ancien-update.vue');
// prettier-ignore
const FonctionAncienDetails = () => import('@/entities/fonction-ancien/fonction-ancien-details.vue');
// prettier-ignore
const FonctionEnCours = () => import('@/entities/fonction-en-cours/fonction-en-cours.vue');
// prettier-ignore
const FonctionEnCoursUpdate = () => import('@/entities/fonction-en-cours/fonction-en-cours-update.vue');
// prettier-ignore
const FonctionEnCoursDetails = () => import('@/entities/fonction-en-cours/fonction-en-cours-details.vue');
// prettier-ignore
const Mandat = () => import('@/entities/mandat/mandat.vue');
// prettier-ignore
const MandatUpdate = () => import('@/entities/mandat/mandat-update.vue');
// prettier-ignore
const MandatDetails = () => import('@/entities/mandat/mandat-details.vue');
// prettier-ignore
const MandatAncien = () => import('@/entities/mandat-ancien/mandat-ancien.vue');
// prettier-ignore
const MandatAncienUpdate = () => import('@/entities/mandat-ancien/mandat-ancien-update.vue');
// prettier-ignore
const MandatAncienDetails = () => import('@/entities/mandat-ancien/mandat-ancien-details.vue');
// prettier-ignore
const MandatEnCours = () => import('@/entities/mandat-en-cours/mandat-en-cours.vue');
// prettier-ignore
const MandatEnCoursUpdate = () => import('@/entities/mandat-en-cours/mandat-en-cours-update.vue');
// prettier-ignore
const MandatEnCoursDetails = () => import('@/entities/mandat-en-cours/mandat-en-cours-details.vue');
// prettier-ignore
const Decoration = () => import('@/entities/decoration/decoration.vue');
// prettier-ignore
const DecorationUpdate = () => import('@/entities/decoration/decoration-update.vue');
// prettier-ignore
const DecorationDetails = () => import('@/entities/decoration/decoration-details.vue');
// prettier-ignore
const TelephonePortable = () => import('@/entities/telephone-portable/telephone-portable.vue');
// prettier-ignore
const TelephonePortableUpdate = () => import('@/entities/telephone-portable/telephone-portable-update.vue');
// prettier-ignore
const TelephonePortableDetails = () => import('@/entities/telephone-portable/telephone-portable-details.vue');
// prettier-ignore
const TelephonePortable2 = () => import('@/entities/telephone-portable-2/telephone-portable-2.vue');
// prettier-ignore
const TelephonePortable2Update = () => import('@/entities/telephone-portable-2/telephone-portable-2-update.vue');
// prettier-ignore
const TelephonePortable2Details = () => import('@/entities/telephone-portable-2/telephone-portable-2-details.vue');
// prettier-ignore
const TelephoneFixe = () => import('@/entities/telephone-fixe/telephone-fixe.vue');
// prettier-ignore
const TelephoneFixeUpdate = () => import('@/entities/telephone-fixe/telephone-fixe-update.vue');
// prettier-ignore
const TelephoneFixeDetails = () => import('@/entities/telephone-fixe/telephone-fixe-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'senateur',
      name: 'Senateur',
      component: Senateur,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'senateur/new',
      name: 'SenateurCreate',
      component: SenateurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'senateur/:senateurId/edit',
      name: 'SenateurEdit',
      component: SenateurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'senateur/:senateurId/view',
      name: 'SenateurView',
      component: SenateurDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'departement-naissance',
      name: 'DepartementNaissance',
      component: DepartementNaissance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'departement-naissance/new',
      name: 'DepartementNaissanceCreate',
      component: DepartementNaissanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'departement-naissance/:departementNaissanceId/edit',
      name: 'DepartementNaissanceEdit',
      component: DepartementNaissanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'departement-naissance/:departementNaissanceId/view',
      name: 'DepartementNaissanceView',
      component: DepartementNaissanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pays-naissance',
      name: 'PaysNaissance',
      component: PaysNaissance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pays-naissance/new',
      name: 'PaysNaissanceCreate',
      component: PaysNaissanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pays-naissance/:paysNaissanceId/edit',
      name: 'PaysNaissanceEdit',
      component: PaysNaissanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pays-naissance/:paysNaissanceId/view',
      name: 'PaysNaissanceView',
      component: PaysNaissanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie-socio-prof',
      name: 'CategorieSocioProf',
      component: CategorieSocioProf,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie-socio-prof/new',
      name: 'CategorieSocioProfCreate',
      component: CategorieSocioProfUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie-socio-prof/:categorieSocioProfId/edit',
      name: 'CategorieSocioProfEdit',
      component: CategorieSocioProfUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie-socio-prof/:categorieSocioProfId/view',
      name: 'CategorieSocioProfView',
      component: CategorieSocioProfDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'etat-civil',
      name: 'EtatCivil',
      component: EtatCivil,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'etat-civil/new',
      name: 'EtatCivilCreate',
      component: EtatCivilUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'etat-civil/:etatCivilId/edit',
      name: 'EtatCivilEdit',
      component: EtatCivilUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'etat-civil/:etatCivilId/view',
      name: 'EtatCivilView',
      component: EtatCivilDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-fiscale',
      name: 'AdresseFiscale',
      component: AdresseFiscale,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-fiscale/new',
      name: 'AdresseFiscaleCreate',
      component: AdresseFiscaleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-fiscale/:adresseFiscaleId/edit',
      name: 'AdresseFiscaleEdit',
      component: AdresseFiscaleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-fiscale/:adresseFiscaleId/view',
      name: 'AdresseFiscaleView',
      component: AdresseFiscaleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale',
      name: 'AdressePostale',
      component: AdressePostale,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale/new',
      name: 'AdressePostaleCreate',
      component: AdressePostaleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale/:adressePostaleId/edit',
      name: 'AdressePostaleEdit',
      component: AdressePostaleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale/:adressePostaleId/view',
      name: 'AdressePostaleView',
      component: AdressePostaleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale-2',
      name: 'AdressePostale2',
      component: AdressePostale2,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale-2/new',
      name: 'AdressePostale2Create',
      component: AdressePostale2Update,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale-2/:adressePostale2Id/edit',
      name: 'AdressePostale2Edit',
      component: AdressePostale2Update,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresse-postale-2/:adressePostale2Id/view',
      name: 'AdressePostale2View',
      component: AdressePostale2Details,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresses',
      name: 'Adresses',
      component: Adresses,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresses/new',
      name: 'AdressesCreate',
      component: AdressesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresses/:adressesId/edit',
      name: 'AdressesEdit',
      component: AdressesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'adresses/:adressesId/view',
      name: 'AdressesView',
      component: AdressesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-ancien',
      name: 'FonctionAncien',
      component: FonctionAncien,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-ancien/new',
      name: 'FonctionAncienCreate',
      component: FonctionAncienUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-ancien/:fonctionAncienId/edit',
      name: 'FonctionAncienEdit',
      component: FonctionAncienUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-ancien/:fonctionAncienId/view',
      name: 'FonctionAncienView',
      component: FonctionAncienDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-en-cours',
      name: 'FonctionEnCours',
      component: FonctionEnCours,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-en-cours/new',
      name: 'FonctionEnCoursCreate',
      component: FonctionEnCoursUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-en-cours/:fonctionEnCoursId/edit',
      name: 'FonctionEnCoursEdit',
      component: FonctionEnCoursUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'fonction-en-cours/:fonctionEnCoursId/view',
      name: 'FonctionEnCoursView',
      component: FonctionEnCoursDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat',
      name: 'Mandat',
      component: Mandat,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat/new',
      name: 'MandatCreate',
      component: MandatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat/:mandatId/edit',
      name: 'MandatEdit',
      component: MandatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat/:mandatId/view',
      name: 'MandatView',
      component: MandatDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-ancien',
      name: 'MandatAncien',
      component: MandatAncien,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-ancien/new',
      name: 'MandatAncienCreate',
      component: MandatAncienUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-ancien/:mandatAncienId/edit',
      name: 'MandatAncienEdit',
      component: MandatAncienUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-ancien/:mandatAncienId/view',
      name: 'MandatAncienView',
      component: MandatAncienDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-en-cours',
      name: 'MandatEnCours',
      component: MandatEnCours,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-en-cours/new',
      name: 'MandatEnCoursCreate',
      component: MandatEnCoursUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-en-cours/:mandatEnCoursId/edit',
      name: 'MandatEnCoursEdit',
      component: MandatEnCoursUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'mandat-en-cours/:mandatEnCoursId/view',
      name: 'MandatEnCoursView',
      component: MandatEnCoursDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'decoration',
      name: 'Decoration',
      component: Decoration,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'decoration/new',
      name: 'DecorationCreate',
      component: DecorationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'decoration/:decorationId/edit',
      name: 'DecorationEdit',
      component: DecorationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'decoration/:decorationId/view',
      name: 'DecorationView',
      component: DecorationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable',
      name: 'TelephonePortable',
      component: TelephonePortable,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable/new',
      name: 'TelephonePortableCreate',
      component: TelephonePortableUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable/:telephonePortableId/edit',
      name: 'TelephonePortableEdit',
      component: TelephonePortableUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable/:telephonePortableId/view',
      name: 'TelephonePortableView',
      component: TelephonePortableDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable-2',
      name: 'TelephonePortable2',
      component: TelephonePortable2,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable-2/new',
      name: 'TelephonePortable2Create',
      component: TelephonePortable2Update,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable-2/:telephonePortable2Id/edit',
      name: 'TelephonePortable2Edit',
      component: TelephonePortable2Update,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-portable-2/:telephonePortable2Id/view',
      name: 'TelephonePortable2View',
      component: TelephonePortable2Details,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-fixe',
      name: 'TelephoneFixe',
      component: TelephoneFixe,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-fixe/new',
      name: 'TelephoneFixeCreate',
      component: TelephoneFixeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-fixe/:telephoneFixeId/edit',
      name: 'TelephoneFixeEdit',
      component: TelephoneFixeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'telephone-fixe/:telephoneFixeId/view',
      name: 'TelephoneFixeView',
      component: TelephoneFixeDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};

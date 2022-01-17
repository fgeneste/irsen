import { IMandat } from '@/shared/model/mandat.model';
import { IFonctionEnCours } from '@/shared/model/fonction-en-cours.model';

export interface IMandatEnCours {
  id?: number;
  idType?: number | null;
  idFonction?: number | null;
  code?: string | null;
  libelle?: string | null;
  libelleFonction?: string | null;
  circonscription?: string | null;
  depuis?: string | null;
  dateElection?: string | null;
  population?: string | null;
  libelleAffichage?: string | null;
  mandat?: IMandat | null;
  fonctions?: IFonctionEnCours[] | null;
}

export class MandatEnCours implements IMandatEnCours {
  constructor(
    public id?: number,
    public idType?: number | null,
    public idFonction?: number | null,
    public code?: string | null,
    public libelle?: string | null,
    public libelleFonction?: string | null,
    public circonscription?: string | null,
    public depuis?: string | null,
    public dateElection?: string | null,
    public population?: string | null,
    public libelleAffichage?: string | null,
    public mandat?: IMandat | null,
    public fonctions?: IFonctionEnCours[] | null
  ) {}
}

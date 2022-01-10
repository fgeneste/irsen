import { IFonctionEnCours } from '@/shared/model/fonction-en-cours.model';
import { IMandat } from '@/shared/model/mandat.model';

export interface IMandatEnCours {
  id?: number;
  idType?: number | null;
  idFonction?: number | null;
  code?: string | null;
  libelle?: string | null;
  libelleFonction?: string | null;
  circonscription?: string | null;
  depuis?: string | null;
  dateElection?: Date | null;
  population?: string | null;
  libelleAffichage?: string | null;
  fonctionEnCours?: IFonctionEnCours | null;
  mandatsEnCours?: IMandat[] | null;
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
    public dateElection?: Date | null,
    public population?: string | null,
    public libelleAffichage?: string | null,
    public fonctionEnCours?: IFonctionEnCours | null,
    public mandatsEnCours?: IMandat[] | null
  ) {}
}

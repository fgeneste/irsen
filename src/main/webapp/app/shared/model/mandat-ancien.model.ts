import { IMandat } from '@/shared/model/mandat.model';
import { IFonctionAncien } from '@/shared/model/fonction-ancien.model';

export interface IMandatAncien {
  id?: number;
  idType?: number | null;
  libelle?: string | null;
  dateDebut?: string | null;
  dateFin?: string | null;
  circonscription?: string | null;
  libelleAffichage?: string | null;
  mandat?: IMandat | null;
  fonctions?: IFonctionAncien[] | null;
}

export class MandatAncien implements IMandatAncien {
  constructor(
    public id?: number,
    public idType?: number | null,
    public libelle?: string | null,
    public dateDebut?: string | null,
    public dateFin?: string | null,
    public circonscription?: string | null,
    public libelleAffichage?: string | null,
    public mandat?: IMandat | null,
    public fonctions?: IFonctionAncien[] | null
  ) {}
}

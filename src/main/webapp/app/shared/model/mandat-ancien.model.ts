import { IFonctionAncien } from '@/shared/model/fonction-ancien.model';
import { IMandat } from '@/shared/model/mandat.model';

export interface IMandatAncien {
  id?: number;
  idType?: number | null;
  libelle?: string | null;
  dateDebut?: Date | null;
  dateFin?: Date | null;
  circonscription?: string | null;
  libelleAffichage?: string | null;
  fonctionAncien?: IFonctionAncien | null;
  anciensMandats?: IMandat[] | null;
}

export class MandatAncien implements IMandatAncien {
  constructor(
    public id?: number,
    public idType?: number | null,
    public libelle?: string | null,
    public dateDebut?: Date | null,
    public dateFin?: Date | null,
    public circonscription?: string | null,
    public libelleAffichage?: string | null,
    public fonctionAncien?: IFonctionAncien | null,
    public anciensMandats?: IMandat[] | null
  ) {}
}

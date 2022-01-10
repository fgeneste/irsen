import { IMandatAncien } from '@/shared/model/mandat-ancien.model';

export interface IFonctionAncien {
  id?: number;
  libelle?: string | null;
  dateDebut?: Date | null;
  dateFin?: Date | null;
  fonctions?: IMandatAncien[] | null;
}

export class FonctionAncien implements IFonctionAncien {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public dateDebut?: Date | null,
    public dateFin?: Date | null,
    public fonctions?: IMandatAncien[] | null
  ) {}
}

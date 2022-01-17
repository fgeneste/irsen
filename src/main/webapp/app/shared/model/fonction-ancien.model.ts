import { IMandatAncien } from '@/shared/model/mandat-ancien.model';

export interface IFonctionAncien {
  id?: number;
  libelle?: string | null;
  dateDebut?: string | null;
  dateFin?: string | null;
  mandatAncien?: IMandatAncien | null;
}

export class FonctionAncien implements IFonctionAncien {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public dateDebut?: string | null,
    public dateFin?: string | null,
    public mandatAncien?: IMandatAncien | null
  ) {}
}

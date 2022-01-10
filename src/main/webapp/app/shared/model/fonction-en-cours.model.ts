import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

export interface IFonctionEnCours {
  id?: number;
  libelle?: string | null;
  dateDebut?: Date | null;
  dateFin?: Date | null;
  fonctions?: IMandatEnCours[] | null;
}

export class FonctionEnCours implements IFonctionEnCours {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public dateDebut?: Date | null,
    public dateFin?: Date | null,
    public fonctions?: IMandatEnCours[] | null
  ) {}
}

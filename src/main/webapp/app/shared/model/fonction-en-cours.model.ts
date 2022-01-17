import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

export interface IFonctionEnCours {
  id?: number;
  libelle?: string | null;
  dateDebut?: string | null;
  dateFin?: string | null;
  mandatEnCours?: IMandatEnCours | null;
}

export class FonctionEnCours implements IFonctionEnCours {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public dateDebut?: string | null,
    public dateFin?: string | null,
    public mandatEnCours?: IMandatEnCours | null
  ) {}
}

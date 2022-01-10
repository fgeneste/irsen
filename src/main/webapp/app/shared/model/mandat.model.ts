import { IMandatAncien } from '@/shared/model/mandat-ancien.model';
import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';
import { ISenateur } from '@/shared/model/senateur.model';

export interface IMandat {
  id?: number;
  mandatAncien?: IMandatAncien | null;
  mandatEnCours?: IMandatEnCours | null;
  senateur?: ISenateur | null;
}

export class Mandat implements IMandat {
  constructor(
    public id?: number,
    public mandatAncien?: IMandatAncien | null,
    public mandatEnCours?: IMandatEnCours | null,
    public senateur?: ISenateur | null
  ) {}
}

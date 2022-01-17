import { ISenateur } from '@/shared/model/senateur.model';
import { IMandatAncien } from '@/shared/model/mandat-ancien.model';
import { IMandatEnCours } from '@/shared/model/mandat-en-cours.model';

export interface IMandat {
  id?: number;
  senateur?: ISenateur | null;
  anciensMandats?: IMandatAncien[] | null;
  mandatsEnCours?: IMandatEnCours[] | null;
}

export class Mandat implements IMandat {
  constructor(
    public id?: number,
    public senateur?: ISenateur | null,
    public anciensMandats?: IMandatAncien[] | null,
    public mandatsEnCours?: IMandatEnCours[] | null
  ) {}
}

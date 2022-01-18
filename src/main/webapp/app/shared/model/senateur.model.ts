import { IEtatCivil } from '@/shared/model/etat-civil.model';
import { IAdresses } from '@/shared/model/adresses.model';
import { IMandat } from '@/shared/model/mandat.model';
import { IDecoration } from '@/shared/model/decoration.model';

export interface ISenateur {
  id?: number;
  etatCivil?: IEtatCivil | null;
  adresses?: IAdresses | null;
  mandats?: IMandat | null;
  decorations?: IDecoration[] | null;
}

export class Senateur implements ISenateur {
  constructor(
    public id?: number,
    public etatCivil?: IEtatCivil | null,
    public adresses?: IAdresses | null,
    public mandats?: IMandat | null,
    public decorations?: IDecoration[] | null
  ) {}
}

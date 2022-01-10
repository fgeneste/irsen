import { IEtatCivil } from '@/shared/model/etat-civil.model';
import { IAdresses } from '@/shared/model/adresses.model';
import { IMandat } from '@/shared/model/mandat.model';

export interface ISenateur {
  id?: number;
  etatCivil?: IEtatCivil | null;
  adresses?: IAdresses | null;
  mandats?: IMandat | null;
}

export class Senateur implements ISenateur {
  constructor(
    public id?: number,
    public etatCivil?: IEtatCivil | null,
    public adresses?: IAdresses | null,
    public mandats?: IMandat | null
  ) {}
}

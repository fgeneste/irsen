import { IEtatCivil } from '@/shared/model/etat-civil.model';

export interface ITelephonePortable {
  id?: number;
  type?: string | null;
  numero?: string | null;
  etatCivil?: IEtatCivil | null;
}

export class TelephonePortable implements ITelephonePortable {
  constructor(public id?: number, public type?: string | null, public numero?: string | null, public etatCivil?: IEtatCivil | null) {}
}

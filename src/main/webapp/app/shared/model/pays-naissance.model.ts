import { IEtatCivil } from '@/shared/model/etat-civil.model';

export interface IPaysNaissance {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  avecConseilDepartemental?: boolean | null;
  article?: string | null;
  etatCivil?: IEtatCivil | null;
}

export class PaysNaissance implements IPaysNaissance {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public avecConseilDepartemental?: boolean | null,
    public article?: string | null,
    public etatCivil?: IEtatCivil | null
  ) {
    this.avecConseilDepartemental = this.avecConseilDepartemental ?? false;
  }
}

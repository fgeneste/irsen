import { IAdresses } from '@/shared/model/adresses.model';

export interface IAdressePostale2 {
  id?: number;
  label?: string | null;
  numero?: string | null;
  voie?: string | null;
  codePostal?: string | null;
  ville?: string | null;
  pays?: string | null;
  localisation?: string | null;
  modeManuel?: boolean | null;
  type?: string | null;
  adresses?: IAdresses | null;
}

export class AdressePostale2 implements IAdressePostale2 {
  constructor(
    public id?: number,
    public label?: string | null,
    public numero?: string | null,
    public voie?: string | null,
    public codePostal?: string | null,
    public ville?: string | null,
    public pays?: string | null,
    public localisation?: string | null,
    public modeManuel?: boolean | null,
    public type?: string | null,
    public adresses?: IAdresses | null
  ) {
    this.modeManuel = this.modeManuel ?? false;
  }
}

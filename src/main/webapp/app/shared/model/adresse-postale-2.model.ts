import { IAdresses } from '@/shared/model/adresses.model';

export interface IAdressePostale2 {
  id?: number;
  numero?: string | null;
  bister?: string | null;
  complement1?: string | null;
  complement2?: string | null;
  typeVoie?: string | null;
  voie?: string | null;
  codePostal?: string | null;
  ville?: string | null;
  pays?: string | null;
  affichageInternet?: boolean | null;
  affichageIntranet?: boolean | null;
  adresses?: IAdresses | null;
}

export class AdressePostale2 implements IAdressePostale2 {
  constructor(
    public id?: number,
    public numero?: string | null,
    public bister?: string | null,
    public complement1?: string | null,
    public complement2?: string | null,
    public typeVoie?: string | null,
    public voie?: string | null,
    public codePostal?: string | null,
    public ville?: string | null,
    public pays?: string | null,
    public affichageInternet?: boolean | null,
    public affichageIntranet?: boolean | null,
    public adresses?: IAdresses | null
  ) {
    this.affichageInternet = this.affichageInternet ?? false;
    this.affichageIntranet = this.affichageIntranet ?? false;
  }
}

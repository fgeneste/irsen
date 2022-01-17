import { IEtatCivil } from '@/shared/model/etat-civil.model';

export interface IDepartementNaissance {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  avecConseilDepartemental?: boolean | null;
  article?: string | null;
  codeSirpas?: string | null;
  codeComparaison?: string | null;
  libelleComplet?: string | null;
  libelleAvecArticle?: string | null;
  etatCivil?: IEtatCivil | null;
}

export class DepartementNaissance implements IDepartementNaissance {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public avecConseilDepartemental?: boolean | null,
    public article?: string | null,
    public codeSirpas?: string | null,
    public codeComparaison?: string | null,
    public libelleComplet?: string | null,
    public libelleAvecArticle?: string | null,
    public etatCivil?: IEtatCivil | null
  ) {
    this.avecConseilDepartemental = this.avecConseilDepartemental ?? false;
  }
}

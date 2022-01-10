import { IEtatCivil } from '@/shared/model/etat-civil.model';

export interface ICategorieSocioProf {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  libelleComplet?: string | null;
  categorieSocioProf?: IEtatCivil | null;
}

export class CategorieSocioProf implements ICategorieSocioProf {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public libelleComplet?: string | null,
    public categorieSocioProf?: IEtatCivil | null
  ) {}
}

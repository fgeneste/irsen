import { IDepartementNaissance } from '@/shared/model/departement-naissance.model';
import { IPaysNaissance } from '@/shared/model/pays-naissance.model';
import { ICategorieSocioProf } from '@/shared/model/categorie-socio-prof.model';
import { ISenateur } from '@/shared/model/senateur.model';

export interface IEtatCivil {
  id?: number;
  matricule?: string | null;
  civilite?: string | null;
  titre?: string | null;
  nomFamille?: string | null;
  nomMarital?: string | null;
  nomUsuel?: string | null;
  prenoms?: string | null;
  prenomUsuel?: string | null;
  dateNaissance?: Date | null;
  communeNaissance?: string | null;
  profession?: string | null;
  courriel?: string | null;
  courriel2?: string | null;
  telephonePortable?: string | null;
  telephonePortable2?: string | null;
  telephoneFixe?: string | null;
  departementNaissance?: IDepartementNaissance | null;
  paysNaissance?: IPaysNaissance | null;
  categorieSocioProf?: ICategorieSocioProf | null;
  senateur?: ISenateur | null;
}

export class EtatCivil implements IEtatCivil {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public civilite?: string | null,
    public titre?: string | null,
    public nomFamille?: string | null,
    public nomMarital?: string | null,
    public nomUsuel?: string | null,
    public prenoms?: string | null,
    public prenomUsuel?: string | null,
    public dateNaissance?: Date | null,
    public communeNaissance?: string | null,
    public profession?: string | null,
    public courriel?: string | null,
    public courriel2?: string | null,
    public telephonePortable?: string | null,
    public telephonePortable2?: string | null,
    public telephoneFixe?: string | null,
    public departementNaissance?: IDepartementNaissance | null,
    public paysNaissance?: IPaysNaissance | null,
    public categorieSocioProf?: ICategorieSocioProf | null,
    public senateur?: ISenateur | null
  ) {}
}

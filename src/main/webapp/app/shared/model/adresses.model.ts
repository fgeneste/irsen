import { IAdresseFiscale } from '@/shared/model/adresse-fiscale.model';
import { IAdressePostale } from '@/shared/model/adresse-postale.model';
import { IAdressePostale2 } from '@/shared/model/adresse-postale-2.model';
import { ISenateur } from '@/shared/model/senateur.model';

export interface IAdresses {
  id?: number;
  adresseFiscale?: IAdresseFiscale | null;
  adressePostale?: IAdressePostale | null;
  adressePostale2?: IAdressePostale2 | null;
  senateur?: ISenateur | null;
}

export class Adresses implements IAdresses {
  constructor(
    public id?: number,
    public adresseFiscale?: IAdresseFiscale | null,
    public adressePostale?: IAdressePostale | null,
    public adressePostale2?: IAdressePostale2 | null,
    public senateur?: ISenateur | null
  ) {}
}

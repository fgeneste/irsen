import { ISenateur } from '@/shared/model/senateur.model';

export interface IDecoration {
  id?: number;
  type?: string | null;
  grade?: string | null;
  senateur?: ISenateur | null;
}

export class Decoration implements IDecoration {
  constructor(public id?: number, public type?: string | null, public grade?: string | null, public senateur?: ISenateur | null) {}
}
